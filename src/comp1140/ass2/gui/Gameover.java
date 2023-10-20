package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * This class is responsible for ending the game, displaying the scores and allowing the user to exit.
 * Its primary purpose is to provide a display method that can be utilized in any other JavaFX executable class. This method
 * displays a Game Over! window allowing the user to see the winner and exit.
 * @author u7330006
 */


public class Gameover {
    private static Stage primaryStage;
    private static final int SETUP_WIDTH = 1200 * 3 / 4;
    private static final int SETUP_HEIGHT = 750 * 3 / 4;

    private static final StackPane root = new StackPane();

    private static Group controls;

    static Button exitButton;
    static HBox exitHBox;
    static Label gameOverLabel = new Label();
    static Label winnerLabel = new Label();

    /**
     * Displays the gameOver screen
     * @u7330006
     *
     * @param akropolis represents the state of the finished game*/
    public static void display(Akropolis akropolis) {
        primaryStage = new Stage();
        primaryStage.setTitle("Game Over");

        // Create a Scoreboard to display the scores
        Scoreboard scoreboard = new Scoreboard(akropolis);
        scoreboard.setTranslateY(10);

        // Create the Exit Button and Game Over Label
        makeControls(akropolis);

        // Organize everything into a vertical box with 30 pixel spacing
        VBox controlsVBox = new VBox(30);
        controlsVBox.setAlignment(Pos.CENTER);
        gameOverLabel.setAlignment(Pos.CENTER);
        winnerLabel.setAlignment(Pos.CENTER);
        exitHBox.setAlignment(Pos.CENTER);

        controlsVBox.getChildren().addAll(gameOverLabel, winnerLabel, scoreboard, exitHBox);

        controls = new Group(controlsVBox);
        root.getChildren().add(controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initialize and format control objects
     * @u7330006
     *
     * @param akropolis object representing the finished game*/
    public static void makeControls(Akropolis akropolis) {
        // Generate the "Game over" text
        gameOverLabel.setText("Game Over");
        gameOverLabel.setTextFill(Color.BLACK);
        gameOverLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");

        // Finding the winners
        List<Integer> winners = akropolis.getWinner();
        boolean multipleWinner = winners.size() > 1;
        String gameOverMessage;
        if (multipleWinner) {
            gameOverMessage = "The Winners Are:";
            for (int i = 0; i < winners.size(); i++) {
                gameOverMessage += " Player " + (winners.get(i));
                if (i != winners.size() - 1) {
                    gameOverMessage += ",";
                }
            }
        } else {
            gameOverMessage = "The Winner is Player " + (winners.get(0)) + "!";
        }

        // Set text for the winner label
        winnerLabel.setText(gameOverMessage);
        winnerLabel.setTextFill(Color.BLACK);
        winnerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");

        // Create an HBox for the "Exit" button
        exitButton = new Button("Exit");
        // #ff6666 is hexadecimal for light red
        exitButton.setStyle("-fx-background-color: #ff6666; -fx-text-fill: black; -fx-font-weight: bold;");
        exitButton.setMinWidth(120);
        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        exitHBox = new HBox(exitButton);
    }
}
