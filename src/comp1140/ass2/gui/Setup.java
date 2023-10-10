package comp1140.ass2.gui;


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
import java.util.HashSet;
import java.util.Random;

/**
 * This class is responsible for initializing the game, allowing users to select the number of players and score variants.
 * Its primary purpose is to provide a display method that can be utilized in any other JavaFX executable class. This method
 * initializes the initial gameState string and creates instances of Akropolis objects using said string to commence the game.
 * @u7330006
 */

public class Setup {
    private static String gameState;
    private static Stage primaryStage = new Stage();
    private static final int SETUP_WIDTH = 1200*3/4;
    private static final int SETUP_HEIGHT = 750*3/4;
    private static int playerCount = 2;

    private static char[] scoreVariants = {'h', 'm', 'b', 't', 'g'};
    private static final StackPane background = new StackPane();

    private static final Group controls = new Group();

    private static final StackPane root = new StackPane();

    private static Label playerCountDisplay = new Label("2");

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


        // Create a label for displaying the player count
        playerCountDisplay.setStyle("-fx-font-weight: bold;");

        // Buttons to adjust the number of players
        incrementPlayersButton = createButton("+", "-fx-background-color: red; -fx-text-fill: black;");
        decrementPlayersButton = createButton("-", "-fx-background-color: yellow; -fx-text-fill: black;");

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

        // Create an HBox for the "Play" button
        playButton = createButton("Play", "-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        playButton.setMinWidth(80);
        HBox playButtonHBox = new HBox(playButton);
        playButtonHBox.setAlignment(Pos.CENTER);

        // Create a VBox to organize the controls vertically
        VBox controlVBox = new VBox(20);
        controlVBox.getChildren().addAll(playerCountLabel, playerCountHBox, housesVariant, marketsVariant, barracksVariant, templesVariant, gardensVariant, playButtonHBox);
        controlVBox.setStyle("-fx-background-color: " + Color.LIGHTBLUE.interpolate(Color.TRANSPARENT, 0.5).toString().replace("0x", "#") + ";");
        controlVBox.setAlignment(Pos.CENTER);

        // Event Handling
        setupEventHandlers();


        // Set the root to the StackPane
        controls.getChildren().add(controlVBox);
    }

    // Helper method to create buttons (of all the same size)
    private static Button createButton(String text, String style) {
        Button button = new Button(text);
        button.setStyle(style);
        button.setPrefWidth(25);
        button.setPrefHeight(25);
        return button;
    }

    // Helper method to create checkboxes (of the same size)
    private static CheckBox createCheckBox(String text) {
        CheckBox checkBox = new CheckBox(text);
        checkBox.setStyle("-fx-font-weight: bold;");
        return checkBox;
    }

    // Method for event handling
    private static void setupEventHandlers() {
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
                scoreVariants[2] = 'B';
            } else {
                scoreVariants[2] = 'b';
            }
        });

        templesVariant.setOnAction(event -> {
            if (templesVariant.isSelected()) {
                scoreVariants[3] = 'T';
            } else {
                scoreVariants[3] = 't';
            }
        });

        gardensVariant.setOnAction(event -> {
            if (gardensVariant.isSelected()) {
                scoreVariants[4] = 'G';
            } else {
                scoreVariants[4] = 'g';
            }
        });

        /* If the playButton is hit the game will start, the play button generates
           the first gameState string that will start the game                     */
        playButton.setOnAction(e -> {
            String settings = playerCount + new String(scoreVariants) + ";";
            // Initialize tileID for playerCount
            int maxTileID;
            switch (playerCount) {
                case 2: maxTileID = 37; break;
                case 3: maxTileID = 49; break;
                default: maxTileID = 61; break;
            }

            // Initialize shared to "0"
            StringBuilder shared = new StringBuilder("0");

            // Determine size of the construction site based on playerCount
            int constructionSiteSize;
            switch (playerCount) {
                case 2: constructionSiteSize = 4; break;
                case 3: constructionSiteSize = 5; break;
                default: constructionSiteSize = 6; break;
            }

            // Create the construction site
            java.util.Set<String> generatedTileIDs = new HashSet<>();
            while (generatedTileIDs.size() < constructionSiteSize) {
                Random random = new Random();
                int tileID = random.nextInt(maxTileID + 1);
                String tileIDString;

                if (tileID < 10) {
                    tileIDString = "0" + tileID;
                } else {
                    tileIDString = String.valueOf(tileID);
                }

                if (!generatedTileIDs.contains(tileIDString)) {
                    generatedTileIDs.add(tileIDString);
                }
            }

            // Append the construction site onto the shared string
            for (String tileID : generatedTileIDs) {
                shared.append(tileID);
            }
            shared.append(";");
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


            // Invoke a callback method with the gameState to return the gameState to start the game
            // todo Akropolis.onGameStart(gameState);

            System.out.println(gameState);
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



