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

    /**Constructor
     * @author u7330006
     *
     * @param akropolis game object that it reflect the scores of
     * */
    Scoreboard(Akropolis akropolis) {
        int[] scores = akropolis.calculateCompleteScores();
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] playerStones = akropolis.retrievePlayerStones();
        int turn = akropolis.currentTurn;


        // Organise the scoreboard into a vertical box
        scoreboardVBox.setAlignment(Pos.CENTER);

        // Apply transparency to the VBox background
        scoreboardVBox.setStyle(
                "-fx-background-color: " + Color.LIGHTGRAY.interpolate(Color.TRANSPARENT, 0.2).toString().replace("0x", "#") + ";" +
                        "-fx-padding: 10px;"  // Add padding around the VBox
        );


        // Create a label to display player number, score, and number of stones
        HBox playerHeaderHBox = new HBox(10);
        playerHeaderHBox.setAlignment(Pos.CENTER);
        Label playerInfoHeader  = new Label("Player " +  "Score " + "Stones");
        // Customize the label style
        playerInfoHeader.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
        playerHeaderHBox.getChildren().add(playerInfoHeader);
        scoreboardVBox.getChildren().add(playerHeaderHBox);


        for (int i = 0; i < numberOfPlayers; i++) {
            // For each player make a horizontal box to display their scores and stone count
            HBox playerHBox = new HBox(10);
            playerHBox.setAlignment(Pos.CENTER);


            // Create a label to display player number, score, and number of stones
            Label playerInfoScores = new Label
                    ( (i + 1) + "          " +  scores[i] + "          " + playerStones[i]);

            // Customize the label style
            playerInfoScores.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");

            // Highlight the score of the current player whose turn it is
            if (i == turn) {
                playerHBox.setOpacity(1);
            } else {
                playerHBox.setOpacity(0.3);
            }

            // Make scores all visible at the end
            if (akropolis.isGameOver()) {
                playerHBox.setOpacity(1);
            }





            // Add the player info label to the playerHBox
            playerHBox.getChildren().add(playerInfoScores);

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