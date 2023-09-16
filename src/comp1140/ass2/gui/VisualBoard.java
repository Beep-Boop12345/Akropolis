package comp1140.ass2.gui;

import comp1140.ass2.Board;
import comp1140.ass2.District;
import comp1140.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;

public class VisualBoard extends Group {

    Board board;

    ArrayList<VisualTile> tiles;



    /*Backend board that this visual board corresponds to*/
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

                    District tileDistrict = tiles[i][j].getDistrictType();
                    Color districtColor = districtToColour(tileDistrict);
                    int tileHeight = tiles[i][j].getHeight();

                    System.out.println(districtColor + ": " + districtColor.getBrightness() );



                    for (int k = 0; k < tileHeight; k++) {
                        districtColor = districtColor.darker();
                    }

                    double strokeLength = sideLength * 0.1 * (1 + tileHeight/2);


                    VisualTile newTile = new VisualTile(xPos, yPos,sideLength * 0.9, districtColor);
                    newTile.setStroke(Color.BLACK);
                    newTile.setStrokeWidth(strokeLength);
                    //newTile.setStrokeType(StrokeType.OUTSIDE);
                    this.tiles.add(newTile);

                }
            }
        }

        this.getChildren().addAll(this.tiles);

    }

    private Color districtToColour(District d) {
        switch (d) {
            case MARKETS:
                return Color.GOLD;
            case GARDENS:
                return Color.LIMEGREEN;
            case BARRACKS:
                return Color.ORANGERED;
            case TEMPLES:
                return Color.DARKORCHID;
            case QUARRY:
                return Color.LINEN;
            default:
                return Color.DODGERBLUE;
        }

    }

    /*Places piece on board*/
    private void piecePlacement(MovablePiece piece) {

    }

}
