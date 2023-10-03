package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// FIXME Task 13  - Basic Playable Game
// FIXME Task 21  - Fully Working Game
// FIXME Task 23G - Variant Scoring

public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private static Akropolis akropolis;

    private static VisualBoard[] boards = new VisualBoard[4];

    private static VisualConstructionSite purchasablePieces;

    private static PieceStack pieceStack;

    @Override
    public void start(Stage stage) throws Exception {
        root.getChildren().add(new StoneLabel(100, 100, 3, 2));

        Scene scene = new Scene(this.root, WINDOW_WIDTH, WINDOW_HEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    /*Initializes the start of a newGame*/
    private void newGame() {

    }

    /*Checks if new game*/
    private boolean nextTurnCheck() {
        return true;
    }
}
