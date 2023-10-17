package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * This class is responsible for ending the game, displaying the scores and allowing the user to restart a new game.
 * Its primary purpose is to provide a display method that can be utilized in any other JavaFX executable class. This method
 * displays a Game Over! window allowing the user to see the winner and restart a new game.
 * @author u7330006
 */


public class Gameover {
    private static Stage primaryStage = new Stage();
    private static final int SETUP_WIDTH = 1200*3/4;
    private static final int SETUP_HEIGHT = 750*3/4;

    private static final StackPane root = new StackPane();

    private static Group controls;

    static Button playAgainButton;


    // Create a method to display the game over screen
    public static void display(Akropolis akropolis) {
        primaryStage = new Stage();
        primaryStage.setTitle("Game Over");

        // Create a Scoreboard to display the scores
        Scoreboard scoreboard = new Scoreboard(akropolis);
        scoreboard.setTranslateY(100);

        makeControls();

        root.getChildren().addAll(scoreboard, controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void makeControls() {
        // Label for displaying "Game Over" text
        Label gameOverLabel = new Label("Game Over!");
        gameOverLabel.setTextFill(Color.BLACK);
        gameOverLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 36;");

        // Create an HBox for the "Play Again" button
        Button playAgainButton = new Button("Play Again");
        playAgainButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        playAgainButton.setMinWidth(120);
        playAgainButton.setOnAction(e -> {
            primaryStage.close();
            Setup.display(); // Assuming this method starts a new game
        });

        HBox controlsHBox = new HBox(playAgainButton);
        controlsHBox.setAlignment(Pos.CENTER);

        controls = new Group(controlsHBox);
        controls.setTranslateY(200);
    }


}
