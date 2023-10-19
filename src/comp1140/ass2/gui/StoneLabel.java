package comp1140.ass2.gui;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Creates a label representing how many stones a player has
 * @author u7683699
 * */
public class StoneLabel extends Label {
    /**
     * Constructor
     * @author u7683699
     *
     * @param x x-coordinate of position on window
     * @param y-coordinate of position on window
     * @param stoneNumber amount of stones that it represents
     * @param playerIndex int corresponding to the player that own the stones*/
    StoneLabel(double x, double y, int stoneNumber, int playerIndex) {
        this.setText("Player " + (playerIndex + 1) + " Stones: " + stoneNumber);
        this.setFont(Font.font(25));
        this.setLayoutX(x);
        this.setLayoutY(y);
    }
}
