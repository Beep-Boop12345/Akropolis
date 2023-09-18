package comp1140.ass2.gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class StoneLabel extends Label {
    StoneLabel(double x, double y, int stoneNumber, int playerIndex) {
        this.setText("Player " + (playerIndex + 1) + " Stones: " + stoneNumber);
        this.setFont(Font.font(25));
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
