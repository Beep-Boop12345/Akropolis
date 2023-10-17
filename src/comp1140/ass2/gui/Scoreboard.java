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
    private final VBox scoreboardVBox = new VBox(10);
    Scoreboard(Akropolis akropolis) {
        int[] scores = akropolis.calculateCompleteScores();
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] playerStones = akropolis.calculatePlayerStones();
        int turn = akropolis.currentTurn;


        // Organise the scoreboard into a vertical box
        scoreboardVBox.setAlignment(Pos.CENTER);

        // Apply transparency to the VBox background
        scoreboardVBox.setStyle(
                "-fx-background-color: " + Color.LIGHTGRAY.interpolate(Color.TRANSPARENT, 0.2).toString().replace("0x", "#") + ";" +
                        "-fx-padding: 10px;"  // Add padding around the VBox
        );



        for (int i = 0; i < numberOfPlayers; i++) {
            // For each player make a horizontal box to display their scores and stone count
            HBox playerHBox = new HBox(10);
            playerHBox.setAlignment(Pos.CENTER);


            // Create a label to display player number, score, and number of stones
            Label playerInfoLabel = new Label("Player " + (i + 1) + ": Score: " + scores[i] + " Stones: " + playerStones[i]);

            // Customize the label style
            playerInfoLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            // Highlight the score of the current player whose turn it is
            if (i == turn) {
                playerHBox.setOpacity(1);
            } else {
                playerHBox.setOpacity(0.3);
            }



            // Add the player info label to the playerHBox
            playerHBox.getChildren().add(playerInfoLabel);

            // Add the playerHBox to the scoreboardVBox
            scoreboardVBox.getChildren().add(playerHBox);
        }

        // Set the VBox as the content of the Label
        this.setGraphic(scoreboardVBox);

    }

    // Method to get the height of the scoreboard
    public double getScoreboardHeight() {
        return scoreboardVBox.getBoundsInLocal().getHeight();
    }
}