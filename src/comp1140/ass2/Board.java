package comp1140.ass2;

import static comp1140.ass2.District.HOUSES;

public class Board {

    /*100 100 is origin*/
    private Tile[][] surfaceTiles = new Tile[200][200];

    /*Player ID 0 to P-1 inclusive, P is number of players*/
    private int player;
    public Board(int player, Tile[][] surfaceTiles) {
        this.player = player;
        this.surfaceTiles = surfaceTiles;
    }

    public Board(int player, String gamestate) {
        this.player = player;
        String[] movesStringForm = isolateMoves(gamestate);
        Move[] moves = new Move[movesStringForm.length];
        for (int i = 0; i < movesStringForm.length; i++) {
            moves[i] = new Move(this.player,movesStringForm[i]);
        }
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false);
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false);
    }

    /*Isolates the part of the game string pertaining only to the moves made by corresponding player*/
    private String[] isolateMoves (String gamestate) {
        String playerstate = gamestate.split(";")[1+player];
        String[] moves = new String[Math.floorDiv( playerstate.length() - 4,10)];
        for (int i = 1; i < moves.length; i++) {
            moves[i] = playerstate.substring(i*10+4,14);
        }
        return moves;
    }
    /*Places piece on Board incorporating the piece tiles into surfaceTiles if possible
    * @Param move*/
    private void placePiece(Move moveToMake){
        if (! isValidPlacement(moveToMake)) {
            return;
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
