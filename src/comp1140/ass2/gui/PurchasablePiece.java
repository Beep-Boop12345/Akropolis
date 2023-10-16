package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import comp1140.ass2.Move;
import comp1140.ass2.Piece;
import comp1140.ass2.Rotation;
import comp1140.ass2.Tile;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class PurchasablePiece extends Group {
    /*The back end piece this represents*/
    private final Piece piece;

    private final ArrayList<VisualTile> vTiles;

    private final ArrayList<Line> connectors;

    private double rotation;

    private double size;

    private boolean reflected;

    private boolean playable;

    //Fields to track movement

    //difference in mouse position and original position
    private double mousePosDiffX;
    private double mousePosDiffY;
    //Mouse position
    private double mousePositionX;
    private double mousePositionY;

    //Fields to track events
    private boolean isPressed;

    /**    A visual piece that is used in the visual construction site.
     * @author u7683699, @author u7646615
     *
     */
    PurchasablePiece(double x, double y, Piece piece, double sideLength, Viewer viewer, Akropolis akropolis){
        this.piece = piece;
        this.vTiles = new ArrayList<>();
        this.connectors = new ArrayList<>();
        this.isPressed = false;
        this.rotation = 0;
        this.size = sideLength;
        this.reflected = false;
        this.playable = true;

        Tile[] tiles = piece.getTiles();

        //Generates the three tiles in correct positions
        for (int i = 0; i < tiles.length; i++) {
            var tile = tiles[i];
            VisualTile newTile;

            double yLength = sideLength * (Math.sin(Math.toRadians(60)));

            if (i == 0) newTile = new VisualTile(0,0,sideLength, tile);
            else if (i == 1) newTile = new VisualTile(0,-2 * yLength,sideLength, tile);
            else newTile = new VisualTile(1.5*sideLength, -yLength, sideLength, tile);

            //Connects the tiles to form a piece
            for (VisualTile vTile : vTiles) {
                Line connectorLine = new Line(newTile.getLayoutX(), newTile.getLayoutY(), vTile.getLayoutX(), vTile.getLayoutY());
                connectorLine.setStrokeWidth(sideLength);
                connectorLine.setStroke(Color.LIGHTSLATEGRAY);
                this.connectors.add(connectorLine);
                this.getChildren().add(0, connectorLine);
            }

            vTiles.add(newTile);
            this.getChildren().add(newTile);
        }
        System.out.println("There are: " + connectors.size() + "lines ");
        requestFocus();

        this.setLayoutX(x);
        this.setLayoutY(y);

        if (!akropolis.canPieceBePurchased(this.piece)) {
            this.setOpacity(0.2);
            this.playable = false;
        }

        /*Dragging functionality for Purchasable Piece*/
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!playable) {
                    return;
                }
                mousePositionX = event.getSceneX();
                mousePositionY = event.getSceneY();
                mousePosDiffX = mousePositionX - getLayoutX();
                mousePosDiffY = mousePositionY - getLayoutY();
                size = 30;
                updateShape();
                isPressed =true;
                reflected = !event.isPrimaryButtonDown();
                toFront();
            }
        });

        this.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                if (!playable) {
                    return;
                }
                double deltaY = event.getDeltaY();
                rotation += deltaY/40;
                updateShape();
            }
        });

        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!playable) {
                    return;
                }
                double moveX = event.getSceneX() - mousePositionX;
                double moveY = event.getSceneY() - mousePositionY;
                setLayoutX(moveX+mousePositionX-mousePosDiffX);
                setLayoutY(moveY+mousePositionY-mousePosDiffY);
                viewer.getBoard().activateClosestMove(akropolis.generateAllValidMovesOfPiece(piece),
                        moveX+mousePositionX-mousePosDiffX,
                        moveY+mousePositionY-mousePosDiffY,
                        reflected);
                toFront();
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!playable) {
                    return;
                }
                double moveX = event.getSceneX() - mousePositionX;
                double moveY = event.getSceneY() - mousePositionY;
                isPressed = false;
                Move moveSelected = viewer.getBoard().findClosestMove(akropolis.generateAllValidMovesOfPiece(piece),
                        moveX+mousePositionX-mousePosDiffX,
                        moveY+mousePositionY-mousePosDiffY,
                        reflected);
                if (moveSelected == null) {
                    setLayoutX(x);
                    setLayoutY(y);
                    size = 25;
                    updateShape();
                }
                akropolis.applyMove(moveSelected);
                viewer.updateView();
            }
        });
    }


    /** updates the connectors. @u7646615
     * */
    private void updateConnectors(){
        for (int i = 0; i < 3; i++) {
            // resize lines
            int j = (i+1) % 3;
            System.out.println("(j: " + j + ",i: " + i + ")");
            Line line = connectors.get(i);
            line.setStrokeWidth(size);
            line.setStartX(vTiles.get(i).getLayoutX());
            line.setStartY(vTiles.get(i).getLayoutY());
            line.setEndX(vTiles.get(j).getLayoutX());
            line.setEndY(vTiles.get(j).getLayoutY());
        }
    }


    /**Rotates the piece. @u7646615
     *
     */
    private void updateShape(){
        double[] positions = findRelativeWindowPosition();
        for (int i = 0; i < 3; i++) {
            vTiles.get(i).resizeTile(size);
            vTiles.get(i).setLayoutX(positions[2*i]);
            vTiles.get(i).setLayoutY(positions[2*i+1]);
        }
        updateConnectors();
    }

    /**Finds the relative position on the window of each piece.@u7646615
     *
     **/
    private double[] findRelativeWindowPosition(){
        double yLength = size * (Math.sin(Math.toRadians(60)));
        double[] positions = new double[6];
        System.out.println(60 * ((int) (this.rotation)));
        Rotation newRotation = Rotation.getAngle (60 * ((int) (this.rotation)));
        System.out.println(newRotation.value);
        System.out.println("__________________-");
        switch (newRotation) {
            case DEG_0:
                positions[0] = 0;
                positions[1] = 0;
                positions[2] = 0;
                positions[3] = -2 * yLength;
                positions[4] = 1.5*size;
                positions[5] = -yLength;
                break;
            case DEG_60:
                positions[0] = -Math.cos(Math.toRadians(60))*size;
                positions[1] = -yLength;
                positions[2] = size;
                positions[3] = -2*yLength;
                positions[4] = size;
                positions[5] = 0;
                break;
            case DEG_120:
                positions[0] = 0;
                positions[1] = -2 * yLength;
                positions[2] = 1.5*size;
                positions[3] = -yLength;
                positions[4] = 0;
                positions[5] = 0;
                break;
            case DEG_180:
                positions[0] = size;
                positions[1] = -2*yLength;
                positions[2] = size;
                positions[3] = 0;
                positions[4] = -Math.cos(Math.toRadians(60))*size;
                positions[5] = -yLength;
                break;
            case DEG_240:
                positions[0] = 1.5*size;
                positions[1] = -yLength;
                positions[2] = 0;
                positions[3] = 0;
                positions[4] = 0;
                positions[5] = -2*yLength;
                break;
            case DEG_300:
                positions[0] = size;
                positions[1] = 0;
                positions[2] = -Math.cos(Math.toRadians(60))*size;
                positions[3] = -yLength;
                positions[4] = size;
                positions[5] = -2*yLength;
                break;
        }
        return positions;
    }
}
