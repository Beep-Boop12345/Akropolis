package comp1140.ass2.gui;


import comp1140.ass2.Akropolis;
import javafx.geometry.Insets;
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

import java.util.Arrays;


/**
 * This class is responsible for initializing the game, allowing users to select the number of players and score
 * variants.
 * Its primary purpose is to provide a display method that can be utilized in any other JavaFX executable class.
 * This method
 * initializes the initial gameState string and creates instances of Akropolis objects using said string to commence
 * the game.
 * @author u7330006
 */

public class Setup {
    private static final Stage primaryStage = new Stage();
    private static final int SETUP_WIDTH = 1200*3/4;
    private static final int SETUP_HEIGHT = 750*3/4;
    private static int playerCount = 2;

    private static int aiCount = 0;

    private static final boolean[] scoreVariants = {false,false,false,false,false};

    //private static boolean aiVariant = false;
    private static final StackPane background = new StackPane();

    private static final Group controls = new Group();

    private static final StackPane root = new StackPane();

    private static Label playerCountDisplay = new Label("2");
    private static Label aiCountDisplay = new Label("0");

    static Button incrementPlayersButton;
    static Button decrementPlayersButton;

    static Button incrementAIButton;
    static Button decrementAIButton;
    static Button playButton;
    static CheckBox housesVariant;
    static CheckBox marketsVariant;
    static CheckBox barracksVariant;
    static CheckBox templesVariant;
    static CheckBox gardensVariant;

    static CheckBox playAI;

    /**
     * Initializes/formats the controls
     * @author u7330006
     **/
    public static void makeControls() {
        // Label for displaying "Number of Players" text
        Label playerCountLabel = new Label("Number of Players");
        playerCountLabel.setTextFill(Color.WHITE);
        playerCountLabel.setStyle("-fx-font-weight: bold;");


        // Create a label for displaying the player count
        playerCountDisplay.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of players
        incrementPlayersButton = createButton("+", "-fx-background-color: red; -fx-text-fill: black;");
        decrementPlayersButton = createButton("-", "-fx-background-color: yellow; -fx-text-fill: black;");

        // Label for displaying "Number of AIs" text
        Label aiCountLabel = new Label("Number of AIs");
        aiCountLabel.setTextFill(Color.WHITE);
        aiCountLabel.setStyle("-fx-font-weight: bold;");

        // Create a label for displaying the ai count
        aiCountDisplay.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of ai
        incrementAIButton = createButton("+", "-fx-background-color: red; -fx-text-fill: black;");
        decrementAIButton = createButton("-", "-fx-background-color: yellow; -fx-text-fill: black;");

        // Checkbox buttons for scoring variants
        housesVariant = createCheckBox("Houses Scoring Variant");
        marketsVariant = createCheckBox("Markets Scoring Variant");
        barracksVariant = createCheckBox("Barracks Scoring Variant");
        templesVariant = createCheckBox("Temples Scoring Variant");
        gardensVariant = createCheckBox("Gardens Scoring Variant");




        // Create an HBox for the increment and decrement buttons, along with the player count
        HBox playerCountHBox = new HBox(50);
        playerCountHBox.setAlignment(Pos.CENTER);
        playerCountHBox.getChildren().addAll(decrementPlayersButton, playerCountDisplay, incrementPlayersButton);

        // Create an HBox for the increment and decrement buttons, along with the ai count
        HBox aiCountHBox = new HBox(50);
        aiCountHBox.setAlignment(Pos.CENTER);
        aiCountHBox.getChildren().addAll(decrementAIButton, aiCountDisplay, incrementAIButton);

        // Create an HBox for the "Play" button
        playButton = createButton("Play", "-fx-background-color: yellow; -fx-text-fill: black;" +
                " -fx-font-weight: bold;");
        playButton.setMinWidth(80);
        HBox playButtonHBox = new HBox(playButton);
        playButtonHBox.setAlignment(Pos.CENTER);

        // Create a VBox to organize the controls vertically
        VBox controlVBox = new VBox(10);
        controlVBox.getChildren().addAll(playerCountLabel, playerCountHBox, aiCountLabel, aiCountHBox, housesVariant,
                marketsVariant, barracksVariant, templesVariant, gardensVariant, playButtonHBox);
        controlVBox.setStyle("-fx-background-color: " + Color.LIGHTBLUE.interpolate(Color.TRANSPARENT, 0.5).
                toString().replace("0x", "#") + ";");
        controlVBox.setAlignment(Pos.CENTER);
        controlVBox.setPadding(new Insets(10,10,10,10));

        // Event Handling
        setupEventHandlers();


        // Set the root to the StackPane
        controls.getChildren().add(controlVBox);
    }

    /** Helper method to create buttons (of all the same size)
     * @author u7330006
     *
     * @param text the text the button displays
     * @param style the String representing standard javaFX style parameters*/
    private static Button createButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        return button;
    }

    /**
     * Helper method to create checkboxes (of the same size)
     *
     * @author u7330006
     * @param text that the checkbox displays*/
    private static CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setStyle("-fx-font-weight: bold;");
        return checkBox;
    }

    // Method for event handling
    private static void setupEventHandlers() {
        incrementPlayersButton.setOnAction(event -> {
            if ((playerCount + aiCount) < 4) {
                playerCount++;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        decrementPlayersButton.setOnAction(event -> {
            if ((playerCount) > 1) {
                playerCount--;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        });

        incrementAIButton.setOnAction(event -> {
            if ((playerCount + aiCount) < 4) {
                aiCount++;
                aiCountDisplay.setText(Integer.toString(aiCount));
            }
        });

        decrementAIButton.setOnAction(event -> {
            if (aiCount > 0) {
                aiCount--;
                aiCountDisplay.setText(Integer.toString(aiCount));
            }
        });

        housesVariant.setOnAction(event -> {
            if (housesVariant.isSelected()) {
                scoreVariants[0] = true;
            } else {
                scoreVariants[0] = false;
            }
        });

        marketsVariant.setOnAction(event -> {
            if (marketsVariant.isSelected()) {
                scoreVariants[1] = true;
            } else {
                scoreVariants[1] = false;
            }
        });

        barracksVariant.setOnAction(event -> {
            if (barracksVariant.isSelected()) {
                scoreVariants[2] = true;
            } else {
                scoreVariants[2] = false;
            }
        });

        templesVariant.setOnAction(event -> {
            if (templesVariant.isSelected()) {
                scoreVariants[3] = true;
            } else {
                scoreVariants[3] = false;
            }
        });

        gardensVariant.setOnAction(event -> {
            if (gardensVariant.isSelected()) {
                scoreVariants[4] = true;
            } else {
                scoreVariants[4] = false;
            }
        });

        /* If the playButton is hit the game will start, the play button generates
           the first gameState string that will start the game                     */
        playButton.setOnAction(e -> {
            Akropolis initialGame = new Akropolis(playerCount, aiCount, scoreVariants);

            System.out.println("Vars: " + Arrays.toString(initialGame.scoreVariants));
            new Viewer(initialGame);
            primaryStage.close();
        });
    }

    // Method to make background
    public static void makeBackground() {
        // Create background image
        Image backgroundImage = new Image("https://cf.geekdo-images.com/OTb0hIJHE-U3eD0FmpBrzA__opengraph/img/gYkj6_qAS2W_vj-uNP92cWCbakc=/0x0:3060x1607/fit-in/1200x630/filters:strip_icc()/pic6705097.jpg",
                SETUP_WIDTH, SETUP_HEIGHT, false, false);
        BackgroundImage BI = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background BG = new Background(BI);
        background.setBackground(BG);
    }

    public static void display() {
        primaryStage.setTitle("Setup");
        makeBackground();
        makeControls();
        controls.setTranslateY(100);

        root.getChildren().addAll(background, controls);

        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}



