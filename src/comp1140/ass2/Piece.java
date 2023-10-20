package comp1140.ass2;

/**
 * class that represents the three tiles that make up one piece
 * @author u7683699
 * @author u7646615
 */
public class Piece {
    /* Tile array of the tiles associated to the piece from top left,
       bottom left and bottom right at standard rotation                               */
    private Tile[] tiles = new Tile[3];
    private int pieceID;


    /** Constructor for piece from string representation
     * @author u7330006 u7646615
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

    /**
     * Copy constructor for piece
     * @author u7683699
     *
     * @param original the piece it is copying*/
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


    public Tile[] getTiles() {
        return this.tiles;
    }

    public int getPieceID() {
        return pieceID;
    }


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

}
