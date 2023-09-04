package comp1140.ass2;

public class Piece {

    /*Having adjusted what Move does is this worthwhile?*/
    private Transform position;

    private Tile[] tiles = new Tile[3];

    public Piece(String pieceID) {

    }

    /*move the piece by replacing position with you Transform

    is this likely to be used?
    * @Param the new Transform*/
    private void movePiece (Transform transform) {

    }

    public Tile[] getTiles() {
        return this.tiles;
    }
}
