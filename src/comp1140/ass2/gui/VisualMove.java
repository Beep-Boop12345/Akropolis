package comp1140.ass2.gui;

import javafx.scene.Group;
import comp1140.ass2.*;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import static javafx.scene.paint.Color.GOLD;

public class VisualMove extends Group {
    private Move move;
    private VisualBoard board;
    private double[] position;

    VisualMove(Move move, double[] position, VisualBoard board) {
        this.move = move;
        this.board = board;
        setLayoutX(position[0]);
        setLayoutY(position[1]);
        Polygon hex1;
        Polygon hex2;
        Polygon hex3;
        switch (move.getPosition().getRot()) {
            case DEG_0:
                hex1 = makeHexagon();
                hex1.setLayoutX(0);
                hex1.setLayoutY(0);
                this.getChildren().add(hex1);
                hex2 = makeHexagon();
                hex2.setLayoutX(0);
                hex2.setLayoutY(-2* 30 * (Math.sin(Math.toRadians(60))));
                this.getChildren().add(hex2);
                hex3 = makeHexagon();
                hex3.setLayoutX(1.5*30);
                hex3.setLayoutY(-30 * (Math.sin(Math.toRadians(60))));
                this.getChildren().add(hex3);
                break;
            case DEG_60:
                hex1 = makeHexagon();
                hex1.setLayoutX(0);
                hex1.setLayoutY(0);
                this.getChildren().add(hex1);
                hex2 = makeHexagon();
                hex2.setLayoutX(1.5*30);
                hex2.setLayoutY(30 * (Math.sin(Math.toRadians(60))));
                this.getChildren().add(hex2);
                hex3 = makeHexagon();
                hex3.setLayoutX(1.5 * 30);
                hex3.setLayoutY(-30 * (Math.sin(Math.toRadians(60))));
                this.getChildren().add(hex3);
                break;
        }
    }

    public void activate() {
        toFront();
        for (Node hex : this.getChildren()) {
            hex.setOpacity(0.5);
        }
    }

    public void deactivate() {
        for (Node hex : this.getChildren()) {
            hex.setOpacity(0);
        }
    }

    private Polygon makeHexagon() {
        Polygon hexagon = new Polygon();
        Double[] pointArray = new Double[12];
        for (int i = 0; i < 6; i++) {
            Double xPos = Math.cos(Math.toRadians(i*60))*30;
            Double yPos = Math.sin(Math.toRadians(i*60))*30;
            pointArray[2*i] = xPos;
            pointArray[2*i + 1] = yPos;
        }
        hexagon.getPoints().addAll(pointArray);
        hexagon.setFill(GOLD);
        hexagon.setOpacity(1);
        return hexagon;
    }
}
