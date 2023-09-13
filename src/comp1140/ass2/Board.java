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
        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move(moveString.substring(i*10,i*10+10));
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
            if (getTile(tilePositions[i]) != null) {
                tiles[i].setHeight(getTile(tilePositions[i]).getHeight() + 1);
            }
            this.surfaceTiles[100+tilePositions[i].getX()][100+tilePositions[i].getY()] = tiles[i];
        }
    }

    private HexCoord[] findTilePosition (Move moveToMake) {
        HexCoord[] tilePosition = new HexCoord[3];
        HexCoord basePos = moveToMake.getPosition().getPos();
        tilePosition[0] = basePos;
        /*Offset to account for hexagonal grid, rotations work differently for odd columns*/
        int offset = 0;
        if (Math.abs(basePos.getX()) % 2 == 1) {
            offset = 1;
        }
        switch (moveToMake.getPosition().getRot()) {
            case DEG_0:
                tilePosition[1] = basePos.add(new HexCoord(0,1));
                tilePosition[2] = basePos.add(new HexCoord(1,0+offset));
                break;
            case DEG_60:
                tilePosition[1] = basePos.add(new HexCoord(1,0+offset));
                tilePosition[2] = basePos.add(new HexCoord(1,-1+offset));
                break;
            case DEG_120:
                tilePosition[1] = basePos.add(new HexCoord(1,-1+offset));
                tilePosition[2] = basePos.add(new HexCoord(0,-1));
                break;
            case DEG_180:
                tilePosition[1] = basePos.add(new HexCoord(0,-1));
                tilePosition[2] = basePos.add(new HexCoord(-1,-1+offset));
                break;
            case DEG_240:
                tilePosition[1] = basePos.add(new HexCoord(-1,-1+offset));
                tilePosition[2] = basePos.add(new HexCoord(-1,0+offset));
                break;
            case DEG_300:
                tilePosition[1] = basePos.add(new HexCoord(-1,0+offset));
                tilePosition[2] = basePos.add(new HexCoord(0,1));
                break;
        }
        return tilePosition;
    }

    /*Decides if piece can be placed legally
    * @Param Piece to be placed*/
    private Boolean isValidPlacement(Move moveToMake) {
        if (moveToMake == null) {
            return false;
        }
        if (moveToMake.getPiece() == null) {
            return false;
        }
        return true;
    }

    /*Decides if there is a tile at a given position
    *@Param HexCoord
     */


    /*Returns tile at given position
    * @Param HexCoord*/
    public Tile getTile (HexCoord position) {
        return surfaceTiles[100+ position.getX()][100+ position.getY()];
    }
}
