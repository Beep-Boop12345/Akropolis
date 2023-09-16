package comp1140.ass2.gui;

import comp1140.ass2.Board;
import comp1140.ass2.District;
import comp1140.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.Vector;

public class VisualBoard extends Group {

    /*Backend board that this visual board corresponds to*/
    Board board;

    ArrayList<VisualTile> tiles;

    int boardHeight = 0;

    VisualBoard(Board board) {
        this.board = board;
        this.tiles = new ArrayList<>();

        Tile[][] tiles = board.getSurfaceTiles();

        int sideLength = 30;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != null) {

                    int xCoord = i - 100;
                    int yCoord = j - 100;

                    double xOffset = Math.abs(xCoord % 2);
                    double yLength = sideLength * (Math.sin(Math.toRadians(60)));

                    double xPos = 1.5 * xCoord * sideLength;
                    double yPos = -2 * yCoord * yLength - xOffset * yLength;

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

    }

    private void heightDarken() {
        for (VisualTile vTile : tiles) {
            int metaTileHeight = vTile.getTile().getHeight();
            for (int i = 0; i < boardHeight - metaTileHeight; i++) {
                vTile.updateColor();
            }
        }
    }

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



    /*Places piece on board*/
    private void piecePlacement(MovablePiece piece) {

    }

}
