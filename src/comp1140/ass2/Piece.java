package comp1140.ass2;

public class Piece {

    /*Having adjusted what Move does is this worthwhile?*/
    private Transform position;

    // Tile array of the tiles associated to the piece from top left,
    // bottom left and bottom right
    private Tile[] tiles = new Tile[3];


    /** Constructor for instance of piece class
     * @oaram Two-digit number pieceID
     */

    // From the pieceID match the ID to the ID FROM the moveString
    // and the pool string to deduce information about the piece


    // Need to know number of players
    public Piece(String pieceID) {
        String TILE_POOL = Akropolis.TILE_POOL;
        int startIndex = TILE_POOL.indexOf(pieceID);

        String tileInfo = TILE_POOL.substring(startIndex + 2, startIndex + 5);
        char[] tileChars = tileInfo.toCharArray();

        for (int i = 0; i < 3; i++) {
            this.tiles[i] = new Tile(tileChars[i]);
        }

        this.position = null; // todo

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
