package comp1140.ass2.gui;

import comp1140.ass2.District;
import comp1140.ass2.Tile;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static javafx.scene.paint.Color.LIGHTGREY;

public class VisualTile extends Group {

    Tile tile;
    Color currentColor;
    Hexagon currentHexagon;

    ArrayList<StarIcon> currentStars;
    VisualTile(double x, double y, double side, Tile tile) {

        this.tile = tile;

        District tileDistrict = this.tile.getDistrictType();
        Color districtColor = districtToColour(tileDistrict);

        currentHexagon = new Hexagon(side * 0.9, districtColor);
        this.getChildren().add(currentHexagon);

        Boolean isPlaza = this.tile.getPlaza();
        this.currentStars = new ArrayList<>();

        if (isPlaza) {
            int starCount = tileDistrict.getStars();
            double starSideLength = side * 0.33;
            switch (starCount) {
                case 1:
                    StarIcon newStar = new StarIcon(0,0,starSideLength, districtColor.darker());
                    this.getChildren().add(newStar);
                    this.currentStars.add(newStar);
                    break;
                case 2:
                    StarIcon newStar1 = new StarIcon(-starSideLength,0,starSideLength, districtColor.darker());
                    this.getChildren().add(newStar1);
                    this.currentStars.add(newStar1);

                    StarIcon newStar2 = new StarIcon(starSideLength,0,starSideLength, districtColor.darker());
                    newStar2.setRotate(36);
                    this.getChildren().add(newStar2);
                    this.currentStars.add(newStar2);
                    break;
                default:
                    break;
                case 3:

                    double yOffset1 = -Math.tan(Math.toRadians(30))*starSideLength;
                    double yOffset2 = starSideLength / Math.cos(Math.toRadians(30));

                    StarIcon newStar4 = new StarIcon(-starSideLength, yOffset1,starSideLength, districtColor.darker());
                    newStar4.setRotate(-12);
                    this.getChildren().add(newStar4);
                    this.currentStars.add(newStar4);

                    StarIcon newStar5 = new StarIcon(starSideLength, yOffset1,starSideLength, districtColor.darker());
                    newStar5.setRotate(36);
                    this.getChildren().add(newStar5);
                    this.currentStars.add(newStar5);

                    StarIcon newStar6 = new StarIcon(0, yOffset2,starSideLength, districtColor.darker());
                    newStar6.setRotate(30);
                    this.getChildren().add(newStar6);
                    this.currentStars.add(newStar6);
                    break;
            }

        }

        this.setLayoutX(x);
        this.setLayoutY(y);

        this.currentColor = districtColor;

    }

    public Tile getTile() {
        return tile;
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

    public void updateColor() {
        currentColor = currentColor.darker();
        currentHexagon.setFill(currentColor);
        for (StarIcon star : currentStars) {
            star.setFill(currentColor.darker());
        }

    }

    private class Hexagon extends Polygon {
        Hexagon(double side, Color color) {
            Double[] pointArray = new Double[12];
            for (int i = 0; i < 6; i++) {
                Double xPos = Math.cos(Math.toRadians(i*60))*side;
                Double yPos = Math.sin(Math.toRadians(i*60))*side;
                pointArray[2*i] = xPos;
                pointArray[2*i + 1] = yPos;
            }
            this.getPoints().addAll(pointArray);
            this.setFill(color);
            this.setStroke(Color.BLACK);
            this.setStrokeWidth(side * 0.1);
        }
    }
    private class StarIcon extends Polygon {
        StarIcon(double x, double y, double length, Color color) {

            ArrayList<Double> pointArray = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Double outerXPos = length * Math.cos(Math.toRadians(i*72 - 18));
                Double outerYPos = length * Math.sin(Math.toRadians(i*72 - 18));

                pointArray.add(outerXPos);
                pointArray.add(outerYPos);

                Double innerXPos = (length / 2) * Math.cos(Math.toRadians(i*72 + 18));
                Double innerYPos = (length / 2) * Math.sin(Math.toRadians(i*72 + 18));

                pointArray.add(innerXPos);
                pointArray.add(innerYPos);

            }
            this.getPoints().addAll(pointArray);
            this.setFill(color);

            this.setStroke(Color.BLACK);
            this.setStrokeWidth(length * 0.1);

            this.setLayoutX(x);
            this.setLayoutY(y);
        }
    }

}
