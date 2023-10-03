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



public class Setup extends Application {
    private static final int SETUP_WIDTH = 1200;
    private static final int SETUP_HEIGHT = 750;
    private int playerCount = 2;

    private String scoreVariants = "hmbtg";
    private final StackPane view = new StackPane();

    private final Group controls = new Group();

    private final Group root = new Group();

    Label playerCountDisplay = new Label("2");

    Button incrementPlayersButton;
    Button decrementPlayersButton;
    Button playButton;
    CheckBox housesVariant;
    CheckBox marketsVariant;
    CheckBox barracksVariant;
    CheckBox templesVariant;
    CheckBox gardensVariant;


    public void makeControls() {
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
        HBox playerButtonsHBox = new HBox(50);
        playerButtonsHBox.setAlignment(Pos.CENTER);
        playerButtonsHBox.getChildren().addAll(decrementPlayersButton, playerCountDisplay, incrementPlayersButton);
        playerButtonsHBox.setLayoutX(300);
        playerButtonsHBox.setLayoutY(400);

        // Create a VBox for the checkboxes and adjust its position
        VBox checkboxVBox = new VBox(10);
        checkboxVBox.setAlignment(Pos.CENTER);
        checkboxVBox.getChildren().addAll(housesVariant, marketsVariant, barracksVariant, templesVariant, gardensVariant);
        checkboxVBox.setLayoutX(500);
        checkboxVBox.setLayoutY(200);

        // Create a VBox to organize the controls and set its background color to grey
        VBox greyBox = new VBox(0);
        greyBox.setAlignment(Pos.CENTER);
        greyBox.setStyle("-fx-background-color: grey;");
        greyBox.getChildren().addAll(playerCountLabel, playerButtonsHBox, checkboxVBox);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(greyBox);


        controls.getChildren().add(stackPane);
    }


    public void makeView() {

        // Create background image
        Image backgroundImage = new Image(
                "https://cf.geekdo-images.com/OTb0hIJHE-U3eD0FmpBrzA__opengraph/img/gYkj6_qAS2W_vj-uNP92cWCbakc=/0x0:3060x1607/fit-in/1200x630/filters:strip_icc()/pic6705097.jpg",
                SETUP_WIDTH, SETUP_HEIGHT, false, false);
        BackgroundImage BI = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background BG = new Background(BI);
        view.setBackground(BG);

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Setup");
        makeControls();
        makeView();
        root.getChildren().addAll(controls, view);

        // Create the scene and display everything
        Scene scene = new Scene(root, SETUP_WIDTH, SETUP_HEIGHT);


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    // Event handler for buttons
    public void handle(ActionEvent event) {
        if (event.getSource() == incrementPlayersButton) {
            if (playerCount < 4) {
                playerCount++;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        }
        if (event.getSource() == decrementPlayersButton) {
            if (playerCount > 2) {
                playerCount--;
                playerCountDisplay.setText(Integer.toString(playerCount));
            }
        }
        if (event.getSource() == playButton) {
            String gameState = Integer.toString(playerCount) + scoreVariants;
            System.out.println(gameState);
        }
    }
}





/* Example generated  gameStrings at the start:

2 Players:
2hmbtg;008050336;P001;P102;
2hmbtg;009222814;P001;P102;
2hmbtg;032161034;P001;P102;


3 Players:
3hmbtg;04222144006;P001;P102;P203;
3hmbtg;04110181432;P001;P102;P203;
3hmbtg;03810013203;P001;P102;P203;

4 Players:
4hmbtg;0194136574037;P001;P102;P203;P304;
4hmbtg;0274035542113;P001;P102;P203;P304;
4hmbtg;0011061471902;P001;P102;P203;P304;
*/