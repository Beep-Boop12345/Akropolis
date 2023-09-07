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


    // Need to know number of players & most recent moveString
    public Piece(String pieceID) {
        String globalTilePool = Akropolis.TILE_POOL;
        String[] tileSections = globalTilePool.split(":");

        int numberOfPlayers = Akropolis.numberOfPlayers;
        String playerSpecificTilePool = tileSections[numberOfPlayers - 2];

        int startIndex = playerSpecificTilePool.indexOf(pieceID);

        String tileInfo = playerSpecificTilePool.substring(startIndex + 2, startIndex + 5);
        char[] tileCharacters = tileInfo.toCharArray();

        for (int i = 0; i < 3; i++) {
            this.tiles[i] = new Tile(tileCharacters[i]);
        }

        this.position = null; // todo: Requires information from moveString
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
