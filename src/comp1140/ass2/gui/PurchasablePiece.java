package comp1140.ass2.gui;

import comp1140.ass2.Piece;
import comp1140.ass2.Tile;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PurchasablePiece extends Group {
    /*The back end piece this represents*/
    private Piece piece;

    ArrayList<VisualTile> vTiles;

    //Fields to track movement
    //difference in mouse position and original position
    private double mousePosDiffX;
    private double mousePosDiffY;
    //Mouse position
    private double mousePositionX;
    private double mousePositionY;

    //A visual piece that is used in the visual construction site.@u7683699, @u7646615
    PurchasablePiece(double x, double y, Piece piece, double sideLength) {
        this.piece = piece;
        this.vTiles = new ArrayList<>();

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
                this.getChildren().add(0, connectorLine);
            }

            vTiles.add(newTile);
            this.getChildren().add(newTile);
        }

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
    }
}
