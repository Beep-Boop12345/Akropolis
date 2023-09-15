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
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true, 0);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false, 0 );
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false, 0);
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
    public Boolean isValidPlacement(Move moveToMake) {
        System.out.println("________________________");
        /*Check if move correctly represents a piece*/
        if (moveToMake == null) {
            return false;
        }
        if (moveToMake.getPiece() == null) {
            return false;
        }
        HexCoord[] positions = findTilePosition(moveToMake);
        /*The tiles that will be covered if the piece is placed*/
        Tile[] tilesToBeCovered = new Tile[3];
        /*Counting to see how many of the pieces hexagons will not cover another piece*/
        int nullCount = 0;
        for (int i = 0; i < 3; i++) {
            tilesToBeCovered[i] = getTile(positions[i]);
            if (tilesToBeCovered[i] == null) {
                nullCount ++;
            }
        }
        /*returns false if some pieces are on the ground but not all of them*/
        if (nullCount > 0 && nullCount < 3) {
            System.out.println("Failed because some parts but not all on ground");
            return false;
        }
        /*Given the case that the entire piece is on the ground return true if there is an adjacent tile*/
        if (nullCount == 3) {
            for (HexCoord i : positions) {
                for (HexCoord j : i.getSurroundings()) {
                    if (getTile(j) != null) {
                        System.out.println("Passed because all on ground and adjacent tile");
                        return true;
                    }
                }
            }
            System.out.println("Failed because all on ground but no adjacent");
            return false;
        }
        /*Checks if all piece is level*/
        boolean heightEq = true;
        heightEq = heightEq && (tilesToBeCovered[0].getHeight() == tilesToBeCovered[1].getHeight());
        heightEq = heightEq && (tilesToBeCovered[2].getHeight() == tilesToBeCovered[1].getHeight());
        System.out.println("Result is because all parts raised");
        for (Tile i : tilesToBeCovered) {
            System.out.println(i.getHeight()+",");
        }
        if (heightEq) {
            boolean samePiece = true;
            samePiece = samePiece && (tilesToBeCovered[0].getPiece() == tilesToBeCovered[1].getPiece());
            samePiece = samePiece && (tilesToBeCovered[2].getPiece() == tilesToBeCovered[1].getPiece());
            return !samePiece;
        }
        return heightEq;
    }

    /*Decides if there is a tile at a given position
    *@Param HexCoord
     */


    /*Returns tile at given position
    * @Param HexCoord*/
    public Tile getTile (HexCoord position) {
        int xPos = 100 + position.getX();
        int yPos = 100 + position.getY();
        if (xPos > 199 || xPos < 0) {
            return null;
        }
        if (yPos > 199|| yPos < 0) {
            return null;
        }
        return surfaceTiles[xPos][100+ position.getY()];
    }
}
