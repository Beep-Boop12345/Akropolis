package comp1140.ass2.gui;

import comp1140.ass2.*;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.io.File;

import static javafx.scene.paint.Color.*;

public class Viewer extends Application {

    private static final double VIEWER_WIDTH = 1200;
    private static final double VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();

    private Group currentView = new Group();
    private TextField gameTextField;

    //components for easy access and modification
    private Akropolis akropolis;
    private VisualBoard board;
    private VisualConstructionSite site;


    private Scene scene;
    /**
     * Draw a placement in the window, removing any previously drawn placements
     * @author u7683699
     *
     * @param akropolis game object representing the state
     */
    void displayState(Akropolis akropolis) {

        //Reset The View
        root.getChildren().remove(currentView);
        Group newView  = new Group();

        int currentTurnId = akropolis.currentTurn;
        Color fillColor = TRANSPARENT;
        switch (currentTurnId) {
            case 0:
                fillColor = PINK;
                break;
            case 1:
                fillColor = LIGHTBLUE;
                break;
            case 2:
                fillColor = LIGHTGREEN;
                break;
            case 3:
                fillColor = LEMONCHIFFON;
                break;
        }
        this.scene.setFill(fillColor);

        // Old Code to Display Stones
        int stones = akropolis.getCurrentPlayer().getStones();
        System.out.println(stones);

        StoneLabel stoneLabel = new StoneLabel(50, VIEWER_HEIGHT - 100, stones, currentTurnId);
        newView.getChildren().add(stoneLabel);



        // New Code to Display Scoreboard including Stones and playerScores
        Scoreboard scoreboard = new Scoreboard(akropolis);


        var scoreboardPosX = VIEWER_WIDTH * 0.1;
        var scoreboardPosY = 0.875*(VIEWER_HEIGHT - scoreboard.getScoreboardHeight()) / 2;
        scoreboard.setLayoutX(scoreboardPosX);
        scoreboard.setLayoutY(scoreboardPosY);
        newView.getChildren().add(scoreboard);

        //Constructs A Visual Board Object by Creating the String Instanced Board Object
        VisualBoard currentBoard = new VisualBoard(akropolis.getCurrentPlayer().getBoard(), this);
        var sitePosX = VIEWER_WIDTH * 0.85;
        var sitePosY = VIEWER_HEIGHT*0.05;


        board = currentBoard;

        currentBoard.setLayoutX(VIEWER_WIDTH/2);
        currentBoard.setLayoutY(VIEWER_HEIGHT/2);

        newView.getChildren().add(currentBoard);

        //Constructs A Visual Construction Site Object by Instancing the Construction Site Class
        var subSite = akropolis.getConstructionSite();

        VisualConstructionSite currentConstructionSite = new VisualConstructionSite(sitePosX,
                sitePosY,
                subSite,
                this,
                akropolis);
        newView.getChildren().add(currentConstructionSite);
        site = currentConstructionSite;

        //Creates A Label to Display Current Turn
        VBox playerTurnBox = new VBox();
        playerTurnBox.setAlignment(Pos.CENTER);
        playerTurnBox.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");
        Text playerLabel = new Text("Player " + (currentTurnId + 1));
        playerLabel.setFont(Font.font(40));
        playerTurnBox.getChildren().add(playerLabel);
        playerTurnBox.setTranslateY(50);
        playerTurnBox.setTranslateX(0.43*(VIEWER_WIDTH-playerTurnBox.getMinWidth()));
        newView.getChildren().add(playerTurnBox);

        //Creates A Label to Display Board Instructions
        Label instructionLabel = new Label("Click and Drag Map With Mouse");
        instructionLabel.setTextAlignment(TextAlignment.CENTER);
        instructionLabel.setFont(Font.font(20));
        instructionLabel.setLayoutX(50);
        instructionLabel.setLayoutY(20);
        newView.getChildren().add(instructionLabel);

        //Completes Task by Adding the New View to the Scene
        root.getChildren().add(newView);
        currentView = newView;
    }

    /**
     * Creates a text field for input and a refresh button.
     * @author u7646615 u7683699 u7330006
     */
    private void makeControls() {
        Label gameLabel = new Label("Game State:");
        gameTextField = new TextField();
        gameTextField.setPrefWidth(800);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            if (!Akropolis.isStateStringWellFormed(gameTextField.getText())) {

                Text failText = new Text();
                failText.setText("Please Enter A Valid State String!");
                failText.setLayoutX(VIEWER_WIDTH/2 - 100);
                failText.setLayoutY(VIEWER_HEIGHT/2 - 10);
                currentView.getChildren().add(failText);
                return;

            }
            this.akropolis = new Akropolis(gameTextField.getText());
            displayState(akropolis);
            controls.toFront();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(gameLabel, gameTextField, button);
        hb.setSpacing(10);
        hb.setLayoutX(50);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    /** Initializes and formats the controls for the game
     * @author u7646615 u7683699 u7330006
     * */
    private void makeGameControls() {
        Button officialRulesButton = new Button("Official rules");
        Button guideButton = new Button("UI guide");
        File gameRules = new File("assets/rules.pdf");
        File guide = new File("assets/UIGuideAkropolis.pdf");
        officialRulesButton.setLayoutX(50);
        officialRulesButton.setLayoutY(100);
        guideButton.setLayoutX(50);
        guideButton.setLayoutY(150);
        controls.getChildren().add(officialRulesButton);
        controls.getChildren().add(guideButton);
        officialRulesButton.setOnAction(event -> {
            HostServices hostServices = getHostServices();
            hostServices.showDocument(gameRules.getAbsolutePath());
        });
        guideButton.setOnAction(event -> {
            HostServices hostServices = getHostServices();
            hostServices.showDocument(guide.getAbsolutePath());
        });
    }

    /**
     * Main method for when run by intelliJ
     * @author u7683699 u7330006
     * <p>
     * Will display controls to enter gameString
     * */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Akropolis Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().add(controls);
        root.getChildren().add(currentView);


        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Constructor
     * @author u7646615
     * <p>
     * will not display controls
     * @param akropolis object that it represents
     * */
    public Viewer(Akropolis akropolis) {
        this.akropolis = akropolis;
        Stage primaryStage = new Stage();
        this.scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        displayState(akropolis);
        primaryStage.setScene(scene);
        makeGameControls();
        root.getChildren().add(controls);
        primaryStage.show();

    }

    /**Updates the viewed state
     * @author u7646615*/
    public void updateView() {
        if (akropolis.isGameOver()) {
            Gameover.display(akropolis);
            // Close the viewer window
            closeViewerWindow();
        } else {
            displayState(akropolis);
        }
    }

    /**
     * Closes the current viewer window used for Game over
     * @author u7330006 */
    private void closeViewerWindow() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
    }

    public VisualBoard getBoard() {
        return board;
    }

    public VisualConstructionSite getSite() {
        return site;
    }
}
