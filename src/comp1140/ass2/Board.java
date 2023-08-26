package comp1140.ass2;

public class Board {

    private Tile[][] surfaceTiles = new Tile[200][200];

    public void Board() {

    }

    /*Places piece on Board incorporating the piece tiles into surfaceTiles if possible
    * @Param Piece*/
    private void placePiece(Piece pieceToPlace){

    }

    /*Decides if piece can be placed legally
    * @Param Piece to be placed*/
    private Boolean isValidPlacement(Piece pieceToPlace) {
        return true;
    }

    /*Decides if there is a tile at a given position
    *@Param HexCoord
     */
    private Boolean isTile(HexCoord position) {
        return true;
    }

    /*Returns tile at given position
    * @Param HexCoord*/
    private Tile getTile (HexCoord position) {
        return null;
    }
}
