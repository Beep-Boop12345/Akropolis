package comp1140.ass2;

public class Piece {
    /* Tile array of the tiles associated to the piece from top left,
       bottom left and bottom right                                */
    private Tile[] tiles = new Tile[3];

    /* Missing position field
       Requires more than just the pieceID to match the position field transform
       as knowing the current position requires the most recently played
       moveString stored in the gameState which cannot be deduced just from the pieceID
       2 digit pieceID

       Maybe getPosition method?
     */

    /** Constructor for instance of piece class
     * @oaram Two-digit number pieceID
     */
    public Piece(String pieceID) {
        String globalTilePool = Akropolis.TILE_POOL;
        int index = globalTilePool.indexOf(pieceID);
        for (int i = 0; i < 3; i++) {
            this.tiles[i] = new Tile(globalTilePool.charAt(index+2+i));
        }
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
