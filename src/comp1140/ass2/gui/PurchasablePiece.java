package comp1140.ass2.gui;

import comp1140.ass2.Piece;
import comp1140.ass2.Tile;

public class PurchasablePiece {
    /*The back end piece this represents*/
    private Piece piece;

    private PurchasablePiece(Piece piece) {
        this.piece = piece;

        Tile[] tiles = piece.getTiles();

    }

    /*How the constructionSite representation responds to a click*/
    private void onClick() {

    }
}
