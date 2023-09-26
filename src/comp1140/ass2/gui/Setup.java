package comp1140.ass2.gui;

import comp1140.ass2.Stack;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import static javafx.application.Application.launch;


public class Setup {
    public static int playerCount;
    private static final int SETUP_WIDTH = 1200;
    private static final int SETUP_HEIGHT = 700;

    public static int display(String title, String message) {
        Stage primaryStage = new Stage();
        primaryStage.setTitle(title);

        Label label = new Label();
        label.setText(message);

        StackPane root = new StackPane();

        // Create buttons
        Button twoPlayersButton = new Button("2 Players");
        Button threePlayersButton = new Button("3 Players");
        Button fourPlayersButton = new Button("4 Players");

        // Create background image
        Image backgroundImage = new Image(
                "https://cf.geekdo-images.com/OTb0hIJHE-U3eD0FmpBrzA__opengraph/img/gYkj6_qAS2W_vj-uNP92cWCbakc=/0x0:3060x1607/fit-in/1200x630/filters:strip_icc()/pic6705097.jpg",
                SETUP_WIDTH, SETUP_HEIGHT, false, false
        );

        BackgroundImage BI = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background BG = new Background(BI);

        // Set the background to the root pane
        root.setBackground(BG);


        // Add event handlers for button clicks
        twoPlayersButton.setOnAction(e -> {
            playerCount = 2;
            primaryStage.close();
        });
        threePlayersButton.setOnAction(e -> {
            playerCount = 2;
            primaryStage.close();
        });

        fourPlayersButton.setOnAction(e -> {
            playerCount = 2;
            primaryStage.close();
        });

        // Create an HBox for horizontal centering and spacing
        HBox hbox = new HBox(10); // 10 pixels spacing
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(twoPlayersButton, threePlayersButton, fourPlayersButton);
        root.getChildren().add(hbox);

        // Create a scene and add the layout
        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
        return playerCount;
    }
}
