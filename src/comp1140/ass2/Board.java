package comp1140.ass2;

import java.util.HashSet;
import java.util.Set;
public class Board {

    /*100 100 is origin, Array of all the tiles appearing on the surface*/
    private Tile[][] surfaceTiles = new Tile[200][200];

    /*Player ID 0 to P-1 inclusive, P is number of players*/
    private int player;

    private int stonesInHold;



    /*Constructs board form player ID and tiles. Trusts that the tile arrangement is possible.*/
    public Board(int player, Tile[][] surfaceTiles) {
        this.player = player;
        this.surfaceTiles = surfaceTiles;
        stonesInHold = 0;
    }

    /*Constructs board from playerID and moves applied to it*/
    public Board(int player, String movesString) {
        this.player = player;
        this.stonesInHold = 0;
        /*Converts string of moves into Array of move objects*/
        Move[] move = movesFromString(movesString);
        /*Places the initial tiles*/
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true, 0);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false, 0);
        /*Makes all the moves listed in the moveString*/
        for (int i = 0; i < move.length; i++) {
            placePiece(move[i], true);
        }
    }

    /*Converts string representing several moves into an Array of move objects
    * @Param String moveString the part of the gameString describing all the moves the player has made
    * @Return Array move objects the moveString represents*/
    private Move[] movesFromString (String moveString) {
        Move[] moves = new Move[Math.floorDiv( moveString.length(),10)];
        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move(moveString.substring(i*10,i*10+10));
        }
        return moves;
    }
    /*Places piece on Board incorporating the piece tiles into surfaceTiles if possible
    * @Param move*/
    public void placePiece(Move moveToMake, boolean setup){
        if (! isValidPlacement(moveToMake)) {
            return;
        }
        HexCoord[] tilePositions = findTilePosition(moveToMake);
        Tile[] tiles = moveToMake.getPiece().getTiles();
        for (int i = 0; i < 3; i++) {
            /*Sets the tile's height as one below the tile above it*/
            if (getTile(tilePositions[i]) != null) {
                tiles[i].setHeight(getTile(tilePositions[i]).getHeight() + 1);
                if (getTile(tilePositions[i]).getDistrictType() == District.QUARRY && !setup) {
                    stonesInHold ++;
                }
            }
            this.surfaceTiles[100+tilePositions[i].getX()][100+tilePositions[i].getY()] = tiles[i];
        }
    }

    /*Returns the position of the individual tiles given the transformation of the piece.
    * The order of the positions is the same s the order of tiles
    * @Param Move moveToMake
    * @Return HexCoord[] array of HexCoords (object describing a position of the board)
    * of the position each tile will be.*/
    private HexCoord[] findTilePosition (Move moveToMake) {
        HexCoord[] tilePosition = new HexCoord[3];
        HexCoord basePos = moveToMake.getPosition().getPos();
        tilePosition[0] = basePos;
        /*Offset to account for hexagonal grid, odd and even columns are shifted from each other*/
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
        /*Check if move correctly represents a piece that could be placed of the board*/
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
            return false;
        }
        /*Given the case that the entire piece is on the ground return true if there is an adjacent tile*/
        if (nullCount == 3) {
            for (HexCoord i : positions) {
                for (HexCoord j : i.getSurroundings()) {
                    if (getTile(j) != null) {
                        return true;
                    }
                }
            }
            return false;
        }
        /*Checks if the whole piece is on the one level*/
        boolean heightEq = true;
        heightEq = heightEq && (tilesToBeCovered[0].getHeight() == tilesToBeCovered[1].getHeight());
        heightEq = heightEq && (tilesToBeCovered[2].getHeight() == tilesToBeCovered[1].getHeight());
        /*Given that the entire piece is level checks that it will not cover just one piece*/
        if (heightEq) {
            boolean samePiece = true;
            samePiece = samePiece && (tilesToBeCovered[0].getPiece() == tilesToBeCovered[1].getPiece());
            samePiece = samePiece && (tilesToBeCovered[2].getPiece() == tilesToBeCovered[1].getPiece());
            return !samePiece;
        }
        return false;
    }

    /*given a hexCoord will decide if it is a part of a lake*/
    public boolean isLake(HexCoord point) {
        if (getTile(point) != null) {
            return false;
        }
        /*This quick way of checking if it is not a lake, failing this does not mean that it cannot be a lake*/
        boolean hasReachedTileCardinally = false;
        hasReachedTileCardinally = hasReachedTileCardinally || !cardinalSearchForTile(new HexCoord(0,1),point);
        hasReachedTileCardinally = hasReachedTileCardinally || !cardinalSearchForTile(new HexCoord(0,-1),point);
        hasReachedTileCardinally = hasReachedTileCardinally || !cardinalSearchForTile(new HexCoord(1,0),point);
        hasReachedTileCardinally = hasReachedTileCardinally || !cardinalSearchForTile(new HexCoord(-1,0),point);
        if (hasReachedTileCardinally) {
            return false;
        }
        /*This tries to build the largest set of empty coordinates, if it reaches a sie of 20, it will stop adding
        * elements, in this case we assume it is not a lake*/
        Set<HexCoord> noTileGroup = new HashSet<>();
        groupOfNoTiles(point, noTileGroup);
        if (noTileGroup.size() > 20) {
            return false;
        }
        return true;
    }

    /*given a direction and a point will return true if tile is reached ,moving in given direction from point*/
    private boolean cardinalSearchForTile(HexCoord direction, HexCoord point) {
        if (point.getX() < -100 || point.getX() > 99 || point.getY() < -100 || point.getY() > 99) {
            return false;
        }
        if (getTile(point) != null) {
            return true;
        }
        return cardinalSearchForTile(direction, point.add(direction));
    }

    /*Given a point will build largest set of coordinates with no tile as possible up to 20 elements*/
    private void groupOfNoTiles(HexCoord point, Set<HexCoord> noTileGroup) {
        if (noTileGroup.size() > 20) {
            return;
        }
        if (getTile(point) != null) {
            return;
        }
        noTileGroup.add(point);
        HexCoord[] neighbours = point.getSurroundings();
        for (HexCoord neighbour : neighbours) {
            if (!noTileGroup.contains(neighbour)) {
                groupOfNoTiles(neighbour, noTileGroup);
            }
        }
    }

    /*Called by player to collect the stones generated due to move*/
    public int collectStones() {
        int stonesHldr = stonesInHold;
        stonesInHold = 0;
        return stonesHldr;
    }


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

    public Tile[][] getSurfaceTiles() {
        return surfaceTiles;
    }
}
