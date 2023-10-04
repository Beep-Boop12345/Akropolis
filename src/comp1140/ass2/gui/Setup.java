package comp1140.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import java.util.Random;



public class Setup {
    private static String gameState;
    private static Stage primaryStage = new Stage();
    private static final int SETUP_WIDTH = 1200;
    private static final int SETUP_HEIGHT = 750;
    private static int playerCount = 2;

    private static char[] scoreVariants = {'h', 'm', 'b', 't', 'g'};
    private static final StackPane view = new StackPane();

    private static final Group controls = new Group();

    private static final Group root = new Group();

    static Label playerCountDisplay = new Label("2");

    static Button incrementPlayersButton;
    static Button decrementPlayersButton;
    static Button playButton;
    static CheckBox housesVariant;
    static CheckBox marketsVariant;
    static CheckBox barracksVariant;
    static CheckBox templesVariant;
    static CheckBox gardensVariant;

    public static void makeControls() {
        // Label for displaying "Number of Players" text
        Label playerCountLabel = new Label("Number of Players");
        playerCountLabel.setTextFill(Color.WHITE);
        playerCountLabel.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of players
        incrementPlayersButton = new Button("+");
        incrementPlayersButton.setStyle("-fx-background-color: red; -fx-text-fill: black;");
        incrementPlayersButton.setPrefWidth(25);
        incrementPlayersButton.setPrefHeight(25);

        decrementPlayersButton = new Button("-");
        decrementPlayersButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        decrementPlayersButton.setPrefWidth(25);
        decrementPlayersButton.setPrefHeight(25);

        // Create a label for displaying the player count
        Label playerCountDisplay = new Label(Integer.toString(playerCount));
        playerCountDisplay.setStyle("-fx-font-weight: bold;");

        // Checkbox buttons for scoring variants
        housesVariant = new CheckBox("Houses Scoring Variant");
        marketsVariant = new CheckBox("Markets Scoring Variant");
        barracksVariant = new CheckBox("Barracks Scoring Variant");
        templesVariant = new CheckBox("Temples Scoring Variant");
        gardensVariant = new CheckBox("Gardens Scoring Variant");

        // Apply bold style to checkbox labels
        housesVariant.setStyle("-fx-font-weight: bold;");
        marketsVariant.setStyle("-fx-font-weight: bold;");
        barracksVariant.setStyle("-fx-font-weight: bold;");
        templesVariant.setStyle("-fx-font-weight: bold;");
        gardensVariant.setStyle("-fx-font-weight: bold;");

        // Create an HBox for the increment and decrement buttons, along with the player count
        HBox playerCountHBox = new HBox(50);
        playerCountHBox.setAlignment(Pos.CENTER);
        playerCountHBox.getChildren().addAll(decrementPlayersButton, playerCountDisplay, incrementPlayersButton);
        playerCountHBox.setLayoutX(300);
        playerCountHBox.setLayoutY(100);

        // Create a VBox for the checkboxes and adjust its position
        VBox checkboxVBox = new VBox(10);
        checkboxVBox.setAlignment(Pos.CENTER);
        checkboxVBox.getChildren().addAll(housesVariant, marketsVariant, barracksVariant, templesVariant, gardensVariant);
        checkboxVBox.setLayoutX(500);
        checkboxVBox.setLayoutY(200);

        // Create a HBox for the playButton
        playButton = new Button("Play");
        playButton.setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
        playButton.setPrefWidth(80);
        playButton.setPrefHeight(30);

        // Create an HBox for the "Play" button and center it
        HBox playButtonHBox = new HBox(playButton);
        playButton.setTranslateX(500);
        playButton.setTranslateY(750/2);

        // Create a VBox to organize the controls and set its background color to grey
        VBox greyBox = new VBox(0);
        greyBox.setAlignment(Pos.CENTER);
        greyBox.setStyle("-fx-background-color: grey;");
        greyBox.getChildren().addAll(playerCountLabel, playButtonHBox, checkboxVBox);

        // Event handling for buttons
        incrementPlayersButton.setOnAction(event -> {
            if (playerCount < 4) {
                playerCount++;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        decrementPlayersButton.setOnAction(event -> {
            if (playerCount > 2) {
                playerCount--;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        playButton.setOnAction(event -> {
            String gameState = Integer.toString(playerCount) + scoreVariants;
            System.out.println(gameState);
        });

        housesVariant.setOnAction(event -> {
            if (housesVariant.isSelected()) {
                scoreVariants[0] = 'H';
            } else {
                scoreVariants[0] = 'h';
            }
        });

        marketsVariant.setOnAction(event -> {
            if (marketsVariant.isSelected()) {
                scoreVariants[1] = 'M';
            } else {
                scoreVariants[1] = 'm';
            }
        });

        barracksVariant.setOnAction(event -> {
            if (barracksVariant.isSelected()) {
                scoreVariants[1] = 'B';
            } else {
                scoreVariants[1] = 'b';
            }
        });

        templesVariant.setOnAction(event -> {
            if (templesVariant.isSelected()) {
                scoreVariants[1] = 'T';
            } else {
                scoreVariants[1] = 't';
            }
        });

        gardensVariant.setOnAction(event -> {
            if (gardensVariant.isSelected()) {
                scoreVariants[1] = 'G';
            } else {
                scoreVariants[1] = 'g';
            }
        });

        // Add grey box background for controls
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(greyBox,playerCountHBox);
        controls.getChildren().add(stackPane);
    }


    public static void makeView() {
        // Create background image
        Image backgroundImage = new Image(
                "https://cf.geekdo-images.com/OTb0hIJHE-U3eD0FmpBrzA__opengraph/img/gYkj6_qAS2W_vj-uNP92cWCbakc=/0x0:3060x1607/fit-in/1200x630/filters:strip_icc()/pic6705097.jpg",
                SETUP_WIDTH, SETUP_HEIGHT, false, false);
        BackgroundImage BI = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background BG = new Background(BI);
        view.setBackground(BG);
    }

    public static String display() {
        primaryStage.setTitle("Setup");
        makeControls();
        makeView();
        root.getChildren().addAll(controls, view);

        // Create the scene and display everything
        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();

        playButton.setOnAction(e -> {
            String settings = playerCount + new String(scoreVariants) + ";";
            // Initialize tileID for playerCount
            int maxTileID;
            switch (playerCount) {
                case 2:
                    maxTileID = 37;
                    break;
                case 3:
                    maxTileID = 49;
                    break;
                default:
                    maxTileID = 61;
                    break;
            }
            String shared = "0";
            for (int i = 0; i < 5; i++) {
                Random random = new Random();
                int tileID = random.nextInt(maxTileID + 1);
                if (tileID < 10) {
                    shared += "0" + tileID;
                } else {
                    shared += tileID;
                }
            }
            shared += ";";

            String playerStrings;
            switch (playerCount) {
                case 2:
                    playerStrings = "P001;P102;";
                    break;
                case 3:
                    playerStrings = "P001;P102;P203;";
                    break;
                default:
                    playerStrings = "P001;P102;P203;P304;";
                    break;
            }
            gameState = settings + shared + playerStrings;
            primaryStage.close();
        });

        // Print and return gameState string when button is clicked
        System.out.println(gameState);
        return gameState;
    }

}



