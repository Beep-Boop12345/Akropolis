package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * This class is responsible for creating a new type of Label object used to display the current scoreboard of the game
 * It includes the scores of each player and the number of stones each player has
 * @author u7330006
 */


 // Dependency on score calculation tasks
public class Scoreboard extends Label {
    Scoreboard(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        int[] scores = akropolis.playerScores;
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] playerStones = akropolis.playerStones;


        // Organise the scoreboard into a vertical box
        VBox scoreboardVBox = new VBox(10);
        scoreboardVBox.setAlignment(Pos.CENTER);

        // Apply stone texture to the VBox
        scoreboardVBox.setStyle("-fx-background-color: derive(stone, 20%);");

        for (int i = 0; i < numberOfPlayers; i++) {
            // For each player make a horizontal box to display their scores and stone count
            HBox playerHBox = new HBox(10);
            playerHBox.setAlignment(Pos.CENTER);


            // Create a label to display player number, score, and number of stones
            Label playerInfoLabel = new Label("Player " + (i + 1) + ": Score: " + scores[i] + " Stones: " + playerStones[i]);

            // Customize the label (font, style, etc.)
            playerInfoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            // Apply the specified style to the HBox
            playerHBox.setStyle("-fx-background-color: " + Color.LIGHTBLUE.interpolate(Color.TRANSPARENT, 0.5).toString().replace("0x", "#") + ";");

            // Add the player info label to the playerHBox
            playerHBox.getChildren().add(playerInfoLabel);

            // Add the playerHBox to the scoreboardVBox
            scoreboardVBox.getChildren().add(playerHBox);
        }

        // Set the VBox as the content of the Label
        this.setGraphic(scoreboardVBox);

    }
}