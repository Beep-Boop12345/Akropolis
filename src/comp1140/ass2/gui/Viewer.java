package comp1140.ass2.gui;

import comp1140.ass2.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 1200;
    private static final int VIEWER_HEIGHT = 700;

    private final Group root = new Group();
    private final Group controls = new Group();

    private Group currentView = new Group();
    private TextField gameTextField;


    /**
     * Draw a placement in the window, removing any previously drawn placements
     *
     * @param state the String representation of the current game state
     */
    void displayState(String state) {

        //Fails to show state when state cannot be shown
        if (!Akropolis.isStateStringWellFormed(state)) {

            Text failText = new Text();
            failText.setText("Please Enter A Valid State String!");
            failText.setLayoutX(VIEWER_WIDTH/2 - 100);
            failText.setLayoutY(VIEWER_HEIGHT/2 - 10);
            currentView.getChildren().add(failText);
            return;

        }

        //Reset The View
        root.getChildren().remove(currentView);
        Group newView  = new Group();

        //Breaks string to construct objects
        String[] components = state.split(";");
        String[] playerStrings = new String[components.length - 2];
        System.arraycopy(components, 2, playerStrings, 0, components.length - 2);

        int currentTurnId = Character.getNumericValue(components[1].charAt(0));

        String currentPlayerString = playerStrings[0];

        for (String s : playerStrings) {
            if (Character.getNumericValue(s.charAt(1)) == currentTurnId) {
                currentPlayerString = s;
                break;
            }
        }

        /* Old Code to Displays Stones
        int stones = Integer.parseInt(currentPlayerString.substring(2,4));
        System.out.println(stones);

        StoneLabel stoneLabel = new StoneLabel(50, VIEWER_HEIGHT - 100, stones, currentTurnId);
        newView.getChildren().add(stoneLabel);
         */


        /* New Code to Display Scoreboard including Stones and playerScores
        Scoreboard scoreboard = new Scoreboard(state);
        newView.getChildren().add(scoreboard);
        */

        //Isolates Move String
        String movesString = currentPlayerString.substring(4);

        //Constructs A Visual Board Object by Creating the String Instanced Board Object
        VisualBoard currentBoard = new VisualBoard(new Board(currentTurnId, movesString));

        currentBoard.setLayoutX(VIEWER_WIDTH/2);
        currentBoard.setLayoutY(VIEWER_HEIGHT/2);

        newView.getChildren().add(currentBoard);

        //Constructs A Visual Construction Site Object by Instancing the Construction Site Class
        var sitePosX = VIEWER_WIDTH * 0.85;
        var sitePosY = VIEWER_HEIGHT*0.05;
        var subSite = new ConstructionSite(state);

        VisualConstructionSite currentConstructionSite = new VisualConstructionSite(sitePosX, sitePosY, subSite);
        newView.getChildren().add(currentConstructionSite);

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
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                displayState(gameTextField.getText());
                controls.toFront();
            }
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

        // Invokes Setup class to get initial stateString
        Setup.display();



        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
