package comp1140.ass2;

public class Piece {
    /* Tile array of the tiles associated to the piece from top left,
       bottom left and bottom right                                */
    private Tile[] tiles = new Tile[3];
    private int pieceID;

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
        this.pieceID = Integer.parseInt(pieceID);
        for (int i = 0; i < 3; i++) {
            //System.out.println("IndexChar Good: " + globalTilePool.charAt(index+2+i));
            this.tiles[i] = new Tile(globalTilePool.charAt(index+2+i), this.pieceID);
        }
    }

    public Piece(Piece original) {
        this.pieceID = original.pieceID;
        //System.out.println("New Piece Id: " + pieceID);
        String globalTilePool = Akropolis.TILE_POOL;

        var stringID = "";

        if (this.pieceID < 10) {
            stringID = "0" + Integer.toString(this.pieceID);
        } else {
            stringID = Integer.toString(this.pieceID);
        }

        int index = globalTilePool.indexOf(stringID);
        tiles = new Tile[3];
        for (int i = 0; i < 3; i++) {
            //System.out.println("IndexChar: " + globalTilePool.charAt(index+2+i));
            this.tiles[i] = new Tile(globalTilePool.charAt(index+2+i), this.pieceID);
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

    public int getPieceID() {
        return pieceID;
    }

    /*Checks if this piece object is equal to another peice
    * @Param Piece compare piece to be compared to
    * @return true if both objects refer to the same piece*/
    public boolean equals(Piece compare) {
        if (compare == null) {
            return false;
        }
        if (compare.getPieceID() == pieceID) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String output = String.valueOf(pieceID);
        /*adds "0" to front if single digit int*/
        if (output.length() == 1) {
            output = "0" + output;
        }
        return output;
    }

    public static void main(String[] args) {
        var testPiece = new Piece("01");
        var testPiece2 = new Piece(testPiece);
    }
}
