package comp1140.ass2;

import static comp1140.ass2.District.HOUSES;

public class Board {

    /*100 100 is origin*/
    private Tile[][] surfaceTiles = new Tile[200][200];

    /*Player ID 0 to P-1 inclusive, P is number of players*/
    private int player;

    /*Constructs board form player ID and tiles. Trusts that the tile arrangement is possible.*/
    public Board(int player, Tile[][] surfaceTiles) {
        this.player = player;
        this.surfaceTiles = surfaceTiles;
    }

    /*Constructs board from playerID and moves applied to it*/
    public Board(int player, String movesString) {
        this.player = player;
        Move[] move = movesFromString(movesString);
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false);
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false);
        for (int i = 0; i < move.length; i++) {
            placePiece(move[i]);
        }
    }

    /*Isolates the part of the game string pertaining only to the moves made by corresponding player*/
    private Move[] movesFromString (String moveString) {
        Move[] moves = new Move[Math.floorDiv( moveString.length(),10)];
        for (int i = 1; i < moves.length; i++) {
            moves[i] = new Move(moveString.substring(i*10,10));
        }
        return moves;
    }
    /*Places piece on Board incorporating the piece tiles into surfaceTiles if possible
    * @Param move*/
    private void placePiece(Move moveToMake){
        if (! isValidPlacement(moveToMake)) {
            return;
        }
        HexCoord[] tilePositions = findTilePosition(moveToMake);
        Tile[] tiles = moveToMake.getPiece().getTiles();
        for (int i = 0; i < 3; i++) {
            if (isTile(tilePositions[i])) {
                tiles[i].setHeight(getTile(tilePositions[i]).getHeight() + 1);
            }
            this.surfaceTiles[100+tilePositions[i].getX()][100+tilePositions[i].getY()] = tiles[i];
        }
    }

    private HexCoord[] findTilePosition (Move moveToMake) {
        HexCoord[] tilePosition = new HexCoord[3];
        HexCoord basePos = moveToMake.getPosition().getPos();
        tilePosition[0] = basePos;
        switch (moveToMake.getPosition().getRot()) {
            case DEG_0:
                tilePosition[1] = basePos.add(new HexCoord(0,1));
                tilePosition[2] = basePos.add(new HexCoord(1,0));
                break;
            case DEG_60:
                tilePosition[1] = basePos.add(new HexCoord(1,0));
                tilePosition[2] = basePos.add(new HexCoord(1,-1));
                break;
            case DEG_120:
                tilePosition[1] = basePos.add(new HexCoord(1,-1));
                tilePosition[2] = basePos.add(new HexCoord(0,-1));
                break;
            case DEG_180:
                tilePosition[1] = basePos.add(new HexCoord(0,-1));
                tilePosition[2] = basePos.add(new HexCoord(-1,-1));
                break;
            case DEG_240:
                tilePosition[1] = basePos.add(new HexCoord(-1,-1));
                tilePosition[2] = basePos.add(new HexCoord(-1,0));
                break;
            case DEG_300:
                tilePosition[1] = basePos.add(new HexCoord(-1,0));
                tilePosition[2] = basePos.add(new HexCoord(0,1));
                break;
        }
        return tilePosition;
    }

    /*Decides if piece can be placed legally
    * @Param Piece to be placed*/
    private Boolean isValidPlacement(Move moveToMake) {
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
