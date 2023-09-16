package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import comp1140.ass2.Board;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import org.w3c.dom.css.Rect;

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
        if (!Akropolis.isStateStringWellFormed(state)) {
            FlowPane failPane = new FlowPane();
            failPane.setPrefWidth(VIEWER_WIDTH);
            failPane.setPrefHeight(VIEWER_HEIGHT);
            Text failText = new Text();
            failText.setText("Please Enter A Valid State String!");
            //failPane.getChildren().add(failText);
            failText.setLayoutX(VIEWER_WIDTH/2);
            failText.setLayoutY(VIEWER_HEIGHT/2);
            failText.setTextOrigin(VPos.CENTER);
            currentView.getChildren().add(failText);
            return;
        }
        root.getChildren().remove(currentView);
        Group newView  = new Group();

        //Rectangle testRect = new Rectangle(100, 100);
        //testRect.setLayoutY(100);
        //testRect.setLayoutX(100);
        //newView.getChildren().add(testRect);

        String[] components = state.split(";");
        String[] playerStrings = new String[components.length - 2];
        for (int i = 2; i < components.length; i++) {
            playerStrings[i-2] = components[i];
        }
        for (String s : playerStrings) {
            System.out.println(s);
        }

        int currentTurnId = Character.getNumericValue(components[1].charAt(0));
        System.out.println(currentTurnId);

        String currentPlayerString = playerStrings[0];

        for (String s : playerStrings) {
            if (Character.getNumericValue(s.charAt(1)) == currentTurnId) {
                currentPlayerString = s;
                break;
            }
        }

        System.out.println(currentPlayerString);

        int stones = Integer.parseInt(currentPlayerString.substring(2,4));
        System.out.println(stones);

        StoneLabel stoneLabel = new StoneLabel(stones, currentTurnId);
        newView.getChildren().add(stoneLabel);

        String movesString = currentPlayerString.substring(4);

        VisualBoard currentBoard = new VisualBoard(new Board(currentTurnId, movesString));

        currentBoard.setLayoutX(VIEWER_WIDTH/2);
        currentBoard.setLayoutY(VIEWER_HEIGHT/2);

        newView.getChildren().add(currentBoard);


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


        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
