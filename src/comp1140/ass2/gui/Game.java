package comp1140.ass2.gui;

import comp1140.ass2.Akropolis;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * Initiates the game
 * @author u7646615
 */
public class Game extends Application {

    private final Group root = new Group();
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;

    private static Akropolis akropolis;

    private static VisualBoard[] boards = new VisualBoard[4];

    private static VisualConstructionSite purchasablePieces;


    @Override
    public void start(Stage stage) throws Exception {
        Setup.display();
    }
}
