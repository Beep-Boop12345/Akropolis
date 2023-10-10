package comp1140.ass2;

import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;
public class Board {

    /*100 100 is origin, Array of all the tiles appearing on the surface*/
    private Tile[][] surfaceTiles = new Tile[200][200];

    /*Player ID 0 to P-1 inclusive, P is number of players*/
    private int player;

    private int stonesInHold;

    private int boardRadiusX;
    private int boardRadiusY;

    /**Constructs board form internal objects. @u7646615
     *
     * @param player the player id of the player that owns this board
     * @param surfaceTiles 2d array of tiles representing the birds eye layout of the board*/
    public Board(int player, Tile[][] surfaceTiles) {
        this.player = player;
        this.surfaceTiles = surfaceTiles;
        stonesInHold = 0;
        //Default to max now, will change in future
        boardRadiusX = 100;
        boardRadiusY = 100;
    }

    /**Constructs board from String. @u7646615
     *
     * @param player the player id of the player that owns this board
     * @param movesString the string representing the moves applied to this board*/
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
        this.boardRadiusX = 2;
        this.boardRadiusY = 2;
        /*Makes all the moves listed in the moveString*/
        for (int i = 0; i < move.length; i++) {
            placePiece(move[i], true);
        }
    }

    /**Converts string representing several moves into an Array of move objects. @u7646615
     *
    * @param moveString the part of the gameString describing all the moves the player has made
    * @return Move[] Array containing the moves this string represents in the same order*/
    private Move[] movesFromString (String moveString) {
        Move[] moves = new Move[Math.floorDiv( moveString.length(),10)];
        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move(moveString.substring(i*10,i*10+10));
        }
        return moves;
    }
    /**Places piece on Board. @u7646615
     * <p>
     * Will not place pieces that cannot be legally placed.
     * Will keep track of any stones generated by placing piece over a quarry.
     * Ensures piece is placed at correct height
     *
    * @param moveToMake the move to be reflected on the board
     *@param setup true when the method is called by the constructor. Stones already generated should not be counted
     **/
    public void placePiece(Move moveToMake, boolean setup){
        if (!isValidPlacement(moveToMake)) {
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
            //We update board active board radius as pieces are placed. This makes checks much faster.
            if (Math.abs(tilePositions[i].getY()) > boardRadiusY) {
                boardRadiusY = Math.abs(tilePositions[i].getY());
            }
            if (Math.abs(tilePositions[i].getX()) > boardRadiusX) {
                boardRadiusX = Math.abs(tilePositions[i].getX());
            }
            this.surfaceTiles[100+tilePositions[i].getX()][100+tilePositions[i].getY()] = tiles[i];
        }
    }

    /**
     * Given a piece returns the position that each tile will be placed. @u7646615
     * <p>
    * The order of the positions is the same as the order of tiles
     *
    * @param moveToMake, the move for which all the tile positions will be returns
    * @Return HexCoord[] an array containing the positions of each piece*/
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

    /** Given a Move will decide if the move can be legally placed on the board. @u7646615
     * <p>
     * This method does not check if the entire move is legal.
     * It will only test if the position the piece will be placed on is acceptable in the rules
     *
    * @param moveToMake the move being concidered
     * @return boolean, true if the piece can be legally placed on the board*/
    public Boolean isValidPlacement(Move moveToMake) {
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
        } //FIXME potential optimization

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

    /**
    * Given a hexCoord describing a point of the board will check if it is a part of a lake. @u7646615
     * <p>
     * A lake is a part of the board where no piece has been placed but is surronded by tiles
     *
     * @param point the point on the board that will be checked if it is a tile
     * @return boolean, true if it is a lake
     **/
    public boolean isLake(HexCoord point) {
        if (getTile(point) != null) {
            return false;
        }
        /*This quick way of checking if it is not a lake, failing this does not mean that it cannot be a lake*/
        boolean hasReachedEdgeCardinally = false;
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(0,1),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(0,-1),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(1,0),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(-1,0),point);
        if (hasReachedEdgeCardinally) {
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

    /**Checks if a tile is a lake according to the definition of the test cases. @u7646615
     * <p>
     * The game rules consider empty tiles surronding by pieces to be a lake.
     * The test case definition requires that a lake be only 1 tile large
     *
     * @param point the point to be tested if it is a lake
     * @return boolean, true if it is a lake
     * */
    public boolean isLakeSingleTile(HexCoord point) {
        // Confirms that the tile is empty
        if (getTile(point) != null) {
            return false;
        }
        // Checks that all neighbours are non-empty
        HexCoord[] neighbours = point.getSurroundings();
        for (HexCoord neighbour : neighbours) {
            if (getTile(neighbour) == null || !withinEdge(neighbour)) {
                return false;
            }
        }
        return true;
    }


    /**
     * searches from a point in a given direction checking if a piece or edge is reached. @u76466615
     *
     * @param point HexCoord describing the position from where the search will start
     * @param direction HexCoord now acting as a direction, the direction in which the search will move
     *
     * @return boolean, true if tile reached, false if edge reached
     * */
    private boolean cardinalSearchForTile(HexCoord direction, HexCoord point) {
        if (point.getX() < -100 || point.getX() > 99 || point.getY() < -100 || point.getY() > 99) {
            return false;
        }
        if (getTile(point) != null) {
            return true;
        }
        return cardinalSearchForTile(direction, point.add(direction));
    }

    /**
     * Given a point will construct the set of adjacent empty tile. @u7646615
     * <p>
     * Will terminate once the set has more than 20 elements.
     * Will not add to set when called with point where the tile is not empty.
     *
     * @param point the point from where the all adjecent tiles will be checked.
     * @param noTileGroup the set which collects points reffering to empty tiles.
     * */
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
            if (!containsIdentical(noTileGroup,neighbour)) {
                groupOfNoTiles(neighbour, noTileGroup);
            }
        }
    }

    /**Checks if set of HexCoord contains an identical element to another HexCoord. @u7646615
     * <p>
     * Does not check if set contains an element, but if it holds an element that is equal to another
     *
     * @param set the set to be checked for identical elements
     * @param element the element that may be equal to an element in the set
     * @return boolean true if the set has an element equal to the element given.
     **/
    private boolean containsIdentical(Set<HexCoord> set, HexCoord element) {
        Iterator<HexCoord> setIterator = set.iterator();
        while (setIterator.hasNext()) {
            if (element.equals(setIterator.next())) {
                return true;
            }
        }
        return false;
    }


    /**
     * returns the stones held by the board, and resets the count. @u7646615
     *
     * @return int the amount of stones held by the board
     * */
    public int collectStones() {
        int stonesHldr = stonesInHold;
        stonesInHold = 0;
        return stonesHldr;
    }


    /**Given a position will return the tile that is their, or null if no tile there. @u7646615
     *
    * @param position the position the tile returned is from
     * @return Tile the tile at the position*/
    public Tile getTile (HexCoord position) {
        int xPos = 100 + position.getX();
        int yPos = 100 + position.getY();
        if (xPos > 199 || xPos < 0) {
            return null;
        }
        if (yPos > 199|| yPos < 0) {
            return null;
        }
        return surfaceTiles[xPos][yPos];  // HEXCOORD is surfaceTile xIndex-100, yIndex-100
    }

    /** Given a position checks if it is in the bounds of the board. @u7646615
     *
     * @param point the point to be checked if it is in the bounds of the board
     * @return boolean true if it is in the bounds of the board
     * */
    private boolean inBounds(HexCoord point) {
        int xPos = 100 + point.getX();
        int yPos = 100 + point.getY();
        if (xPos > 99 + boardRadiusX + 3 || xPos < 100 - boardRadiusX - 3) {
            return false;
        }
        if (yPos > 99 + boardRadiusY + 3|| yPos < 100 - boardRadiusY - 3) {
            return false;
        }
        return true;
    }

    /**
     * Given a position decides if it is within the maximum possible bounds of the board ([1,199]x[1,199])
     *
     * @param point the point to be checked if it is in the maximum bounds of the board
     * @return boolean true if it is in the maximum bounds of the board
     * */
    private boolean withinEdge(HexCoord point) {
        int xPos = 100 + point.getX();
        int yPos = 100 + point.getY();
        if (xPos > 199 || xPos < 0) {
            return false;
        }
        if (yPos > 199 || yPos < 0) {
            return false;
        }
        return true;
    }


    public Tile[][] getSurfaceTiles() {
        return surfaceTiles;
    }

    public int getBoardRadiusX() {
        return boardRadiusX;
    }

    public int getBoardRadiusY() {
        return boardRadiusY;
    }
}
