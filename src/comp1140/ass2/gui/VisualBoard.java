package comp1140.ass2.gui;

import comp1140.ass2.Board;
import javafx.scene.Group;

public class VisualBoard extends Group {

    Board board;



    /*Backend board that this visual board corresponds to*/
    VisualBoard(Board board) {
        this.board = board;
        this.getChildren().add(new VisualTile(200,200,100));
    }

    /*Places piece on board*/
    private void piecePlacement(MovablePiece piece) {

    }

}
