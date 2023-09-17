package comp1140.ass2.gui;

import comp1140.ass2.Piece;
import comp1140.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PurchasablePiece extends Group {
    /*The back end piece this represents*/
    private Piece piece;

    ArrayList<VisualTile> vTiles;

    PurchasablePiece(double x, double y, Piece piece, double sideLength) {
        this.piece = piece;
        this.vTiles = new ArrayList<>();

        Tile[] tiles = piece.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            var tile = tiles[i];
            VisualTile newTile;

            double yLength = sideLength * (Math.sin(Math.toRadians(60)));

            if (i == 0) newTile = new VisualTile(0,0,sideLength, tile);
            else if (i == 1) newTile = new VisualTile(0,-2 * yLength,sideLength, tile);
            else newTile = new VisualTile(1.5*sideLength, -yLength, sideLength, tile);

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

    }

    /*How the constructionSite representation responds to a click*/
    private void onClick() {

    }
}
