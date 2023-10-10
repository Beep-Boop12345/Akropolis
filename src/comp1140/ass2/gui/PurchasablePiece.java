package comp1140.ass2.gui;

import comp1140.ass2.Piece;
import comp1140.ass2.Rotation;
import comp1140.ass2.Tile;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PurchasablePiece extends Group {
    /*The back end piece this represents*/
    private Piece piece;

    private ArrayList<VisualTile> vTiles;

    private ArrayList<Line> connectors;

    private Rotation rotation;

    private double size;

    //Fields to track movement
    //difference in mouse position and original position
    private double mousePosDiffX;
    private double mousePosDiffY;
    //Mouse position
    private double mousePositionX;
    private double mousePositionY;

    //Fields to track events
    private boolean isPressed;

    //A visual piece that is used in the visual construction site.@u7683699, @u7646615
    PurchasablePiece(double x, double y, Piece piece, double sideLength) {
        this.piece = piece;
        this.vTiles = new ArrayList<>();
        this.connectors = new ArrayList<>();
        this.isPressed = false;
        this.rotation = Rotation.DEG_0;
        this.size = sideLength;

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

        this.setLayoutX(x);
        this.setLayoutY(y);

        /*Dragging functionality for Purchasable Piece*/
        this.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePositionX = event.getSceneX();
                mousePositionY = event.getSceneY();
                mousePosDiffX = mousePositionX - getLayoutX();
                mousePosDiffY = mousePositionY - getLayoutY();
                size = 30;
                resizePiece();
                isPressed =true;
                toFront();
            }
        });

        this.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double moveX = event.getSceneX() - mousePositionX;
                double moveY = event.getSceneY() - mousePositionY;
                setLayoutX(moveX+mousePositionX-mousePosDiffX);
                setLayoutY(moveY+mousePositionY-mousePosDiffY);
                toFront();
            }
        });

        this.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                isPressed = false;
            }
        });

        setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("anything!!!!");
                if (!isPressed) {
                    System.out.println("key pressed no click");
                    return;
                }
                System.out.println("key pressed while pressing");
                if (event.getCode() == KeyCode.A) {
                    rotation = rotation.add(Rotation.getAngle(-60));
                    rotatePiece();
                } else if (event.getCharacter() == "d") {
                    rotation = rotation.add(Rotation.getAngle(60));
                    rotatePiece();
                }
            }
        });
    }

    /**Resizes the Piece. @u7646615
     *
     * */
    private void resizePiece() {
        double yLength = size * (Math.sin(Math.toRadians(60)));
        for (int i = 0; i < 3; i++) {
            // resize tiles
            vTiles.get(i).resizeTile(size);
            if (i == 1) {
                vTiles.get(i).setLayoutY(-2 * yLength);
            } else if (i > 1) {
                vTiles.get(i).setLayoutX(1.5*size);
                vTiles.get(i).setLayoutY(-1 * yLength);
            }
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
            // FIXME Correct top right connector line
    }

    /**Rotates the piece. @u7646615
     *
     */
    private void rotatePiece(){
        double[] positions = findRelativeWindowPosition();
        for (int i = 0; i < 3; i++) {
            vTiles.get(i).setLayoutX(positions[2*i]);
            vTiles.get(i).setLayoutX(positions[2*i+1]);
        }
    }

    /**Finds the relative position on the window of each piece.@u7646615
     *
     **/
    private double[] findRelativeWindowPosition(){
        double yLength = size * (Math.sin(Math.toRadians(60)));
        double[] positions = new double[6];
        positions[0] = 0;
        positions[1] = 0;
        Rotation newRotation = this.rotation;
        switch (newRotation) {
            case DEG_0:
                positions[2] = 0;
                positions[3] = -2 * yLength;
                positions[4] = 1.5*size;
                positions[5] = -yLength;
                break;
            case DEG_60:
                positions[2] = 1.5*size;
                positions[3] = -yLength;
                positions[4] = 1.5*size;
                positions[5] = yLength;
                break;
            case DEG_120:
                positions[2] = 1.5*size;
                positions[3] = yLength;
                positions[4] = 0;
                positions[5] = 2*yLength;
                break;
            case DEG_180:
                positions[2] = 0;
                positions[3] = 2*yLength;
                positions[4] = -1.5*size;
                positions[5] = yLength;
                break;
            case DEG_240:
                positions[2] = -1.5*size;
                positions[3] = yLength;
                positions[4] = -1.5*size;
                positions[5] = -yLength;
                break;
            case DEG_300:
                positions[2] = -1.5*size;
                positions[3] = -yLength;
                positions[4] = 0;
                positions[5] = -2*yLength;
                break;
        }
        return positions;
    }
}
