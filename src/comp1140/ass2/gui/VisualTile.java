package comp1140.ass2.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static javafx.scene.paint.Color.LIGHTGREY;

public class VisualTile extends Polygon {


    VisualTile(double x, double y, double side, Color color) {

        Double[] pointArray = new Double[12];
        for (int i = 0; i < 6; i++) {
            Double xPos = Math.cos(Math.toRadians(i*60))*side;
            Double yPos = Math.sin(Math.toRadians(i*60))*side;
            pointArray[2*i] = xPos;
            pointArray[2*i + 1] = yPos;
        }
        this.getPoints().addAll(pointArray);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setFill(color);
    }
}
