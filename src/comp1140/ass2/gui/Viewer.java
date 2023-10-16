package comp1140.ass2.gui;

import comp1140.ass2.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

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
    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param akropolis representitive of the state as an object
     */
    void displayState(Akropolis akropolis) {

        //Reset The View
        root.getChildren().remove(currentView);
        Group newView  = new Group();

        int currentTurnId = akropolis.currentTurn;


        // Old Code to Display Stones
        int stones = akropolis.getCurrentPlayer().getStones();
        System.out.println(stones);

        StoneLabel stoneLabel = new StoneLabel(50, VIEWER_HEIGHT - 100, stones, currentTurnId);
        newView.getChildren().add(stoneLabel);



        /* New Code to Display Scoreboard including Stones and playerScores
        Scoreboard, scoreboard = new Scoreboard(state);
        newView.getChildren().add(scoreboard);
        */

        //Constructs A Visual Board Object by Creating the String Instanced Board Object
        VisualBoard currentBoard = new VisualBoard(akropolis.getCurrentPlayer().getBoard(), this);
        board = currentBoard;

        currentBoard.setLayoutX(VIEWER_WIDTH/2);
        currentBoard.setLayoutY(VIEWER_HEIGHT/2);

        newView.getChildren().add(currentBoard);

        //Constructs A Visual Construction Site Object by Instancing the Construction Site Class
        var sitePosX = VIEWER_WIDTH * 0.85;
        var sitePosY = VIEWER_HEIGHT*0.05;
        var subSite = akropolis.getConstructionSite();

        VisualConstructionSite currentConstructionSite = new VisualConstructionSite(sitePosX,
                sitePosY,
                subSite,
                this,
                akropolis);
        newView.getChildren().add(currentConstructionSite);
        site = currentConstructionSite;

        //Creates A Label to Display Current Turn
        Label playerLabel = new Label("Player " + (currentTurnId + 1));
        playerLabel.setTextAlignment(TextAlignment.CENTER);
        playerLabel.setFont(Font.font(40));
        playerLabel.setLayoutX((VIEWER_WIDTH/2) - 70);
        playerLabel.setLayoutY(10);
        newView.getChildren().add(playerLabel);

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
     * Create a basic text field for input and a refresh button.
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

    /**Updates the viewed state
     * @author u7646615*/
    public void updateView() {
        displayState(akropolis);
    }

    public VisualBoard getBoard() {
        return board;
    }

    public VisualConstructionSite getSite() {
        return site;
    }

    public static double getViewerWidth() {
        return VIEWER_WIDTH;
    }

    public static double getViewerHeight() {
        return VIEWER_HEIGHT;
    }
}
