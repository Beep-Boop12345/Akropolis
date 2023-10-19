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
    static Label gameOverLabel;

    // Create a method to display the game over screen
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
        exitHBox.setAlignment(Pos.CENTER);

        controlsVBox.getChildren().addAll(gameOverLabel, scoreboard, exitHBox);

        controls = new Group(controlsVBox);
        root.getChildren().add(controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void makeControls(Akropolis akropolis) {
        // Generate the "Game over" text
        //Set Game over label to display winner/s
        List<Integer> winners = akropolis.getWinner();
        boolean multipleWinner = winners.size() > 1;
        String gameOverMessage;
        if (multipleWinner) {
            gameOverMessage = "Game over \n The winners are:";
            for (int i = 0; i < winners.size(); i++) {
                gameOverMessage += " Player " + (winners.get(i) + 1);
                if (i != winners.size() -1 ) {
                    gameOverMessage += ",";
                }
            }
        } else {
            gameOverMessage = "Game over \n The winner is " + (winners.get(0) + 1);
        }

        // Label for displaying "Game Over" text
        gameOverLabel = new Label(gameOverMessage);
        gameOverLabel.setTextFill(Color.BLACK);
        gameOverLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");

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
