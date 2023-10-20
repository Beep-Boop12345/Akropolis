package comp1140.ass2.gui;

import comp1140.ass2.*;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Set;

public class VisualBoard extends Group {

    /*Backend board that this visual board corresponds to*/
    Board board;

    private final Viewer viewer;

    // Keeps a store of all currently placed tiles
    ArrayList<VisualTile> tiles;

    // Stores the closest move
    VisualMove closestMove;

    // The max height of all tiles
    int boardHeight = 0;

    // Tracks mouse movement
    private Double mouseX;
    private Double mouseY;

    /**
     * Constructor
     * @author u7683699
     *
     * @param board the board that it represents
     * @param viewer that it is in*/
    VisualBoard(Board board, Viewer viewer) {
        this.board = board;
        this.viewer = viewer;
        this.tiles = new ArrayList<>();
        this.closestMove = null;

        Tile[][] tiles = board.getSurfaceTiles();

        int sideLength = 30;

        //Generates and places all tiles from the backend board as visual tiles
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != null) {

                    int xCoordinate = i - 100;
                    int yCoordinate = j - 100;

                    double xOffset = Math.abs(xCoordinate % 2);
                    double yLength = sideLength * (Math.sin(Math.toRadians(60)));

                    double xPos = 1.5 * xCoordinate * sideLength;
                    double yPos = -2 * yCoordinate * yLength - xOffset * yLength;

                    VisualTile newTile = new VisualTile(xPos, yPos, sideLength, tiles[i][j]);

                    this.tiles.add(newTile);

                    int tileHeight = tiles[i][j].getHeight();

                    if (tileHeight > boardHeight) {
                        boardHeight = tileHeight;
                    }
                }
            }
        }

        this.getChildren().addAll(this.tiles);

        heightDarken();
        connectPieces(sideLength);

        //Drag Functionality
        this.setOnMousePressed(event -> {
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
            this.toBack();
        });
        this.setOnMouseDragged(event -> {
            double newX = event.getSceneX() - mouseX;
            double newY = event.getSceneY() - mouseY;
            this.setLayoutX(this.getLayoutX() + newX);
            this.setLayoutY(this.getLayoutY() + newY);
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
            this.toBack();
        });
        this.setOnMouseReleased(event -> {
            double newX = event.getSceneX() - mouseX;
            double newY = event.getSceneY() - mouseY;
            this.setLayoutX(this.getLayoutX() + newX);
            this.setLayoutY(this.getLayoutY() + newY);
            this.mouseX = event.getSceneX();
            this.mouseY = event.getSceneY();
            this.toBack();
        });

    }

   /**
    * darkens tiles that are on lower heights
    * @author u7683699*/
    private void heightDarken() {
        for (VisualTile vTile : tiles) {
            int metaTileHeight = vTile.getTile().getHeight();
            for (int i = 0; i < boardHeight - metaTileHeight; i++) {
                vTile.updateColor();
            }
        }
    }

    /**
     * Draws lines between tiles from the same piece
     * @author u7683699
     *
     * @param sideLength the length of a side of a hexagon*/
    private void connectPieces(int sideLength) {
        for (VisualTile vTile : tiles) {

            int metaTilePieceId = vTile.getTile().getPiece();

            for (VisualTile otherVTile : tiles) {
                if (otherVTile.equals(vTile)) continue;

                int otherMetaTilePieceId = otherVTile.getTile().getPiece();

                double startPosX = vTile.getLayoutX();
                double startPosY = vTile.getLayoutY();

                double endPosX = otherVTile.getLayoutX();
                double endPosY = otherVTile.getLayoutY();

                double xDiff = endPosX - startPosX;
                double yDiff = endPosY - startPosY;

                double distance = Math.sqrt(xDiff * xDiff + yDiff * yDiff);

                if (metaTilePieceId == otherMetaTilePieceId && distance < 2 * sideLength) {
                    Line connectorLine = new Line(startPosX, startPosY, endPosX, endPosY);
                    connectorLine.setStrokeWidth(sideLength);
                    connectorLine.setStroke(Color.LIGHTSLATEGRAY);
                    this.getChildren().add(0, connectorLine);
                }
            }
        }
    }

    /**
     * Evaluates the coordinates of a move if it were on the board
     * @author u7646615
     *
     * @param move the move for which its position will be evaluated
     * @return double array containing the x and y coordinate
     * */
    private double[] windowPositionOfMove(Move move) {
        int xCoordinate = move.getTransform().getPos().getX();
        int yCoordinate = move.getTransform().getPos().getY();
        Rotation rotation = move.getTransform().getRot();



        int sideLength = 30;

        double yLength = sideLength * (Math.sin(Math.toRadians(60)));

        switch (rotation) {
            case DEG_120:
                xCoordinate += 0;
                yCoordinate += - 1;
                break;
            case DEG_240:
                xCoordinate += -1;
                yCoordinate += 0 - Math.abs(xCoordinate % 2);
                break;
            case DEG_180:
                xCoordinate += -1;
                yCoordinate += -1*Math.abs(xCoordinate % 2);
                break;
            case DEG_300:
                yCoordinate += Math.abs(xCoordinate % 2);
                xCoordinate += -1;
        }
        double xOffset = Math.abs(xCoordinate % 2);
        double xPos = 1.5 * xCoordinate * sideLength;
        double yPos = -2 * yCoordinate * yLength - xOffset * yLength;

        double[] position = new double[2];
        position[0] = xPos;
        position[1] = yPos;
        return position;
    }
    /**
     * Evaluates the distance between a move and a point
     * @author u7646615
     *
     * @param move the move
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return the distance as a double*/
    private double moveDistance(Move move, double x, double y) {
        if (move == null) {
            return Double.POSITIVE_INFINITY;
        }
        double[] movePosition = windowPositionOfMove(move);
        //Must convert between the pieces offset
        double trueXPos = x + viewer.getSite().getLayoutX();
        double trueYPos = y + viewer.getSite().getLayoutY();
        double relativeXPos = trueXPos - getLayoutX();
        double relativeYPos = trueYPos - getLayoutY();
        return Math.sqrt((relativeXPos - movePosition[0])*(relativeXPos - movePosition[0]) +
                (relativeYPos - movePosition[1])*(relativeYPos - movePosition[1]));
    }
    /**
     * Given a set of moves and a point will return the closest move with the same rotation.
     * @author u7646615
     *
     * @param moves all the moves being evaluated
     * @param x x-coordinate of point
     * @param y y-coordinate of point
     * @param rotation the pieces rotation only moves with the same rotation will be returned
     **/
    public Move findClosestMove(Set<Move> moves, double x, double y, Rotation rotation) {
        Move closest = null;
        for (Move move : moves) {
            if (move.getTransform().getRot() == rotation) {
                if (moveDistance(move,x,y) < moveDistance(closest,x,y)) {
                    closest = move;
                }
            }
        }
        if (moveDistance(closest, x,y) > 40) {
            return null;
        }
        return closest;
    }

    /**
     * Given a set of moves and a point will present a preview of where the closest move is.
     * @author u7646615
     *
     * @param moves all the moves being evaluated
     * @param x x-coordinate of point
     * @param y y-coordinate of point
     * @param rotation the pieces rotation only moves with the same rotation will be activated
     **/
    public void activateClosestMove(Set<Move> moves, double x, double y, Rotation rotation) {
        if (closestMove != null) {
            closestMove.deactivate();
        }
        Move closestMoveRep = findClosestMove(moves, x, y, rotation);
        if (closestMoveRep == null || moveDistance(closestMoveRep, x, y) > 40) {
            return;
        }
        closestMove = new VisualMove(closestMoveRep, windowPositionOfMove(closestMoveRep));
        this.getChildren().add(closestMove);
        System.out.println(findClosestMove(moves, x, y, rotation));
        closestMove.activate();
        System.out.println("I activated a move");
    }

    /**Removes the preview effect
     * @author u7646615*/

    public void deactivateClosestMove() {
        closestMove.deactivate();
        closestMove = null;
    }

    public VisualMove getClosestMove() {
        return closestMove;
    }
}
