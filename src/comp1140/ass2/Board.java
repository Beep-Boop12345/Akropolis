package comp1140.ass2;

import java.util.*;


public class Board {

    /*100 100 is origin, Array of all the tiles appearing on the surface*/
    private Tile[][] surfaceTiles = new Tile[200][200];

    /*The stones the board collects when a move is applied*/
    private int stonesInHold;

    /*The maximum horizontal and vertical distance from the 100,100 cell of the array of the pieces*/
    private int boardRadiusX;
    private int boardRadiusY;

    /**Constructs board form internal objects.
     * @author u7646615
     *
     * */
    public Board() {
        //this.player = player;
        stonesInHold = 0;
        /*Places the initial tiles*/
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true, 0);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false, 0);
        //Sets the default radii
        boardRadiusX = 2;
        boardRadiusY = 2;
    }

    /**Constructs board from String.
     * @author u7646615
     *
     * @param movesString the string representing the moves applied to this board*/
    public Board(String movesString) {
        this.stonesInHold = 0;
        /*Converts string of moves into Array of move objects*/
        Move[] move = movesFromString(movesString);
        /*Places the initial tiles*/
        this.surfaceTiles[100][100] = new Tile(District.HOUSES, true, 0);
        this.surfaceTiles[101][99] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[100][101] = new Tile(District.QUARRY, false, 0);
        this.surfaceTiles[99][99] = new Tile(District.QUARRY, false, 0);
        //Sets the default radii
        this.boardRadiusX = 2;
        this.boardRadiusY = 2;
        /*Makes all the moves listed in the moveString*/
        for (int i = 0; i < move.length; i++) {
            placePiece(move[i], true);
        }
    }

    /**
     * Copy constructor for board
     * @author u7683699
     *
     * @param original the original board
     * */
    Board(Board original) {
        this.stonesInHold = original.stonesInHold;
        this.boardRadiusX = original.boardRadiusX;
        this.boardRadiusY = original.boardRadiusY;
        this.surfaceTiles = new Tile[200][200];

        for (int i = 0; i < original.surfaceTiles.length; i++) {
            for (int j = 0; j < surfaceTiles[i].length; j++) {
                if (original.surfaceTiles[i][j] != null) {
                    this.surfaceTiles[i][j] = new Tile(original.surfaceTiles[i][j]);
                }
            }
        }
    }

    /**Converts string representing several moves into an Array of move objects.
     * @author u7646615
     *
    * @param moveString the part of the gameString describing all the moves the player has made
    * @return Move[] Array containing the moves this string represents in the same order*/
    private Move[] movesFromString (String moveString) {
        //Calculate the size of the move array
        Move[] moves = new Move[Math.floorDiv( moveString.length(),10)];
        // Iterate through string and initialize moves
        for (int i = 0; i < moves.length; i++) {
            moves[i] = new Move(moveString.substring(i*10,i*10+10));
        }
        return moves;
    }
    /**Places piece on Board.
     * @author u7646615
     * <p>
     * Will place pieces that cannot be legally placed, must gaurd for this before calling.
     * Will keep track of any stones generated by placing piece over a quarry.
     * Ensures piece is placed at correct height
     *
    * @param moveToMake the move to be reflected on the board
     *@param setup true when the method is called by the constructor. Stones already generated should not be counted
     **/
    public void placePiece(Move moveToMake, boolean setup){
        HexCoord[] tilePositions = findTilePosition(moveToMake);
        Tile[] tiles = moveToMake.getPiece().getTiles();
        for (int i = 0; i < 3; i++) {
            /*Sets the tile's height as one above the tile it will cover*/
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
            //Replaces the piece below it
            this.surfaceTiles[100+tilePositions[i].getX()][100+tilePositions[i].getY()] = tiles[i];
        }
    }

    /**
     * Given a piece returns the position that each tile will be placed.
     * @author u7646615
     * <p>
    * The order of the positions is the same as the order of tiles
     *
    * @param moveToMake, the move for which all the tile positions will be returns
    * @return  an array containing the positions of each piece*/
    private HexCoord[] findTilePosition (Move moveToMake) {
        HexCoord[] tilePosition = new HexCoord[3];
        HexCoord basePos = moveToMake.getTransform().getPos();
        tilePosition[0] = basePos;
        /*Offset to account for hexagonal grid, odd and even columns are shifted from each other*/
        int offset = 0;
        if (Math.abs(basePos.getX()) % 2 == 1) {
            offset = 1;
        }
        switch (moveToMake.getTransform().getRot()) {
            case DEG_0:
                tilePosition[1] = basePos.add(new HexCoord(0,1));
                tilePosition[2] = basePos.add(new HexCoord(1,offset));
                break;
            case DEG_60:
                tilePosition[1] = basePos.add(new HexCoord(1,offset));
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
                tilePosition[2] = basePos.add(new HexCoord(-1,offset));
                break;
            case DEG_300:
                tilePosition[1] = basePos.add(new HexCoord(-1,offset));
                tilePosition[2] = basePos.add(new HexCoord(0,1));
                break;
        }
        return tilePosition;
    }

    /** Given a Move will decide if the move can be legally placed on the board.
     * @author u7646615
     * <p>
     * This method does not check if the entire move is legal.
     * It will only test if there position the piece will be placed on is acceptable in the rules
     *
     * @param moveToMake the move being considered
     * @return boolean, true if the piece can be legally placed on the board*/
    public Boolean isValidPlacement(Move moveToMake) {
        HexCoord[] positions = findTilePosition(moveToMake);
        /*Counting to see how many of the pieces hexagons will not cover another piece*/
        int height = placementHeights(positions);
        if (height == -1) {
            return false;
        }

        /*Given the case that the entire piece is on the ground return true if there is an adjacent tile*/
        if (height == 0) {
            for (HexCoord point : moveToMake.getPieceNeighbours()) {
                if (getTile(point) != null) {
                    return true;
                }
            }
            return false;
        }
        int piece0 = getTile(positions[0]).getPiece();
        boolean samePiece = true;
        samePiece = samePiece && (piece0 == getTile(positions[1]).getPiece());
        samePiece = samePiece && (getTile(positions[2]).getPiece() == piece0);
        return !samePiece;
    }

    /** returns height all the pieces wll be placed at.
     * @author u7646615
     * <p>
     * -1 if different height
     *
     * @param piecePositions positions pieces will be placed
     * @return the height at which they will be placed
     * */
    private int placementHeights(HexCoord[] piecePositions) {
        Tile tile0 = getTile(piecePositions[0]);
        int height0;
        if (tile0 != null) {
            height0 = tile0.getHeight() + 1;
        } else {
            height0 = 0;
        }
        Tile tile1 = getTile(piecePositions[1]);
        int height1;
        if (tile1 != null) {
            height1 = tile1.getHeight() + 1;
        } else {
            height1 = 0;
        }
        if (height0 != height1) {
            return -1;
        }

        Tile tile2 = getTile(piecePositions[2]);
        int height2;
        if (tile2 != null) {
            height2 = tile2.getHeight() + 1;
        } else {
            height2 = 0;
        }
        if (height0 != height2) {
            return -1;
        }
        return height0;
    }

    //Remove if we never use it
    /**
    * Given a hexCoord describing a point of the board will check if it is a part of a lake.
    * @author u7646615
    * <p>
    * A lake is a part of the board where no piece has been placed but is surrounded by tiles
    *
    * @param point the point on the board that will be checked if it is a tile
    * @return boolean, true if it is a lake
    **/
    /*
    public boolean isLake(HexCoord point) {
        if (getTile(point) != null) {
            return false;
        }
        *//*This quick way of checking if it is not a lake, failing this does not mean that it cannot be a lake*//*
        boolean hasReachedEdgeCardinally = false;
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(0,1),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(0,-1),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(1,0),point);
        hasReachedEdgeCardinally = hasReachedEdgeCardinally || !cardinalSearchForTile(new HexCoord(-1,0),point);
        if (hasReachedEdgeCardinally) {
            return false;
        }
        *//*This tries to build the largest set of empty coordinates, if it reaches a sie of 20, it will stop adding
        * elements, in this case we assume it is not a lake*//*
        Set<HexCoord> noTileGroup = new HashSet<>();
        groupOfNoTiles(point, noTileGroup);
        return noTileGroup.size() > 20;
    }*/

    /**Checks if a tile is a lake according to the definition of the test cases.
     * @author u7646615
     * <p>
     * The game rules consider empty tiles surrounding by pieces to be a lake.
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
            if (getTile(neighbour) == null) {
                return false;
            }
        }
        return true;
    }

//Remove if no isLake
//    /**
//     * searches from a point in a given direction checking if a piece or edge is reached.
//     * @author u76466615
//     *
//     * @param point HexCoord describing the position from where the search will start
//     * @param direction HexCoord now acting as a direction, the direction in which the search will move
//     *
//     * @return boolean, true if tile reached, false if edge reached
//     * */
//    private boolean cardinalSearchForTile(HexCoord direction, HexCoord point) {
//        if (point.getX() < -100 || point.getX() > 99 || point.getY() < -100 || point.getY() > 99) {
//            return false;
//        }
//        if (getTile(point) != null) {
//            return true;
//        }
//        return cardinalSearchForTile(direction, point.add(direction));
//    }

    //Remove if no lake
     /**
     * Given a point will construct the set of adjacent empty tile.
     * @author u7646615
     * <p>
     * Will terminate once the set has more than 20 elements.
     * Will not add to set when called with point where the tile is not empty.
     *
     * @param point the point from where the all adjacent tiles will be checked.
     * @param noTileGroup the set which collects points reffering to empty tiles.
     * */
     /*
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
*/
    //Remove if no isLake
//    /**Checks if set of HexCoord contains an identical element to another HexCoord.
//     * @author u7646615
//     * <p>
//     * Does not check if set contains an element, but if it holds an element that is equal to another
//     *
//     * @param set the set to be checked for identical elements
//     * @param element the element that may be equal to an element in the set
//     * @return boolean true if the set has an element equal to the element given.
//     **/
//    private boolean containsIdentical(Set<HexCoord> set, HexCoord element) {
//        Iterator<HexCoord> setIterator = set.iterator();
//        while (setIterator.hasNext()) {
//            if (element.equals(setIterator.next())) {
//                return true;
//            }
//        }
//        return false;
//    }


    /**
     * returns the stones held by the board, and resets the count.
     * @author u7646615
     *
     * @return int the amount of stones held by the board
     * */
    public int collectStones() {
        int stonesHldr = stonesInHold;
        stonesInHold = 0;
        return stonesHldr;
    }


    /**Given a position will return the tile that is their, or null if no tile there.
     * @author u7646615
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
        return surfaceTiles[xPos][yPos];  // HexCoord is surfaceTile xIndex-100, yIndex-100
    }


    /**
     * Calculates a boards house scores
     * @author u7683699
     *
     * @param variant boolean that is true if house scoring variant is selected
     * @return the score associated with house districts*/
    public int calculateHouseScore(boolean variant) {
        ArrayList<HexCoord> listOfCoords = getTilesOfType(District.HOUSES);
        ArrayList<ArrayList<HexCoord>> foundGroups = new ArrayList<>();
        outerLoop:
        for (int i = 0; i < listOfCoords.size(); i++) {

            for (var group : foundGroups) {
                if (group.contains(listOfCoords.get(i))) {
                    continue outerLoop;
                }
            }
            ArrayList<HexCoord> newGroup = new ArrayList<>();
            newGroup.add(listOfCoords.get(i));
            foundGroups.add(newGroup);
            findSurroundingHouses(newGroup, listOfCoords.get(i));

        }

        ArrayList<ArrayList<HexCoord>> largestGroups = new ArrayList<>();
        if (foundGroups.size() < 1) { return 0; }
        largestGroups.add(foundGroups.get(0));
        for (var group : foundGroups) {
            if (group.size() > largestGroups.get(0).size()) {
                largestGroups = new ArrayList<>();
                largestGroups.add(group);
            } else if (group.size() == largestGroups.get(0).size()) {
                largestGroups.add(group);
            }
        }

        ArrayList<Integer> largestScores = new ArrayList<>();
        for (var largeGroup : largestGroups) {
            var score = 0;
            for (var coord : largeGroup) {
                var tile = getTile(coord);
                var tileHeight = tile.getHeight();
                if (!tile.getPlaza())  {
                    score += (tileHeight + 1);
                }
            }

            if (variant && score >= 10) {
                score *= 2;
            }
            largestScores.add(score);
        }

        var largestScore = largestScores.get(0);
        for (var score : largestScores) {
            if (score > largestScore) {
                largestScore = score;
            }
        }

        var stars = starCountOfType(District.HOUSES);
        return largestScore * stars;
    }

    /**Builds a list of the all adjacent houses
     * @author u7683699
     *
     * @param housesInGroup the set that is being built. Is modified by this function
     * @param currentCoord the coordinate describing the point which neighbours will be checked to add to
     *                     housesInGroup
     * */
    private void findSurroundingHouses(ArrayList<HexCoord> housesInGroup, HexCoord currentCoord) {

        var surrounds = currentCoord.getSurroundings();

        for (HexCoord surround : surrounds) {
            var surroundingTile = getTile(surround);

            var notNull = surroundingTile != null;

            if (notNull) {

                var notInGroup = !housesInGroup.contains(surround);
                var isHouse = surroundingTile.getDistrictType().equals(District.HOUSES);
                var notPlaza = !surroundingTile.getPlaza();

                if (notInGroup && isHouse && notPlaza) {
                    housesInGroup.add(surround);
                    findSurroundingHouses(housesInGroup, surround);
                }
            }
        }
    }

    private ArrayList<HexCoord> getTilesOfType(District district) {

        ArrayList<HexCoord> listOfCoords = new ArrayList<>();

        for (int i = 0; i < surfaceTiles.length; i++) {
            for (int j = 0; j < surfaceTiles[i].length; j++) {
                var tile = surfaceTiles[i][j];
                if (tile != null && tile.getDistrictType().equals(district) && !tile.getPlaza()) {
                    listOfCoords.add( new HexCoord(i-100, j-100));
                }
            }
        }
        return listOfCoords;
    }

    /**
     * Method to calculate the market score for a single board given if the market variant is enabled or not.
     * Note: This function does not use the helper function starCount to reduce complexity as that the use
     *       of that helper function here will increase the number of total iterations required to calculate the score.
     * @author u7330006
     * @param variant the market scoring variant
     * @return the market score for the board
     */
    public int calculateMarketScore(boolean variant) {

        // Initialize the markets and marketStars to be zero for each player
        int totalMarketStars = 0;
        int totalValidMarkets = 0;

        // Iterate through the playerTiles to find marketPlazas stars and market districts to calculate scores
        for (int m = 0; m < surfaceTiles.length; m++) {
            for (int n = 0; n < surfaceTiles[m].length; n++) {
                Tile tile = surfaceTiles[m][n];
                HexCoord point = new HexCoord(m - 100, n - 100);

                // If the tile is null, ignore the current iteration
                if (tile == null) {
                    continue;
                }

                // Increment the totalMarketStars if they are a plaza and don't count plazas for districts
                if (tile.getPlaza() && tile.getDistrictType() == District.MARKETS) {
                    totalMarketStars += tile.getStars(tile);
                    continue;
                }

                // Count the emptySpaces of the current tile
                HexCoord[] surroundingHexCoords = point.getSurroundings();
                int adjacentMarketDistricts = 0;
                int adjacentMarketPlazas = 0;
                // Count how many of the surrounding spaces are empty
                for (int j = 0; j < surroundingHexCoords.length; j++) {
                    Tile surroundingTile = getTile(surroundingHexCoords[j]);
                    if (surroundingTile == null) { continue; }
                    if (surroundingTile.getDistrictType() == District.MARKETS) {
                        if (surroundingTile.getPlaza()) {
                            adjacentMarketPlazas++;
                        }
                        else {
                            adjacentMarketDistricts++;
                        }
                    }
                }

                // Increment the district count
                if (tile.getDistrictType() == District.MARKETS) {
                    if (adjacentMarketDistricts == 0) {
                        if (variant && adjacentMarketPlazas >= 1) {
                            totalValidMarkets += 2 * (tile.getHeight() + 1);
                        }
                        else {
                            totalValidMarkets += tile.getHeight()+1;
                        }
                    }
                }
            }
        }
        return totalMarketStars * totalValidMarkets;
    }

    /**
     * Method to calculate the barack score for a single board given if the barrack variant is enabled or not.
     * <p>
     * This function does not use the helper function starCount to reduce complexity as that the use
     *       of that helper function here will increase the number of total iterations required to calculate the score.
     * @author u7330006
     * @param variant the barrack scoring variant
     * @return the barrack score for the board
     */
    public int calculateBarrackScore(boolean variant) {
        // Initialize the barracks and barrackStars to be zero for each player
        int totalBarrackStars = 0;
        int totalValidBarracks = 0;

        // Iterate through the playerTiles to find barrackPlazas stars and barrack districts to calculate scores
        for (int m = 0; m < surfaceTiles.length; m++) {
            for (int n = 0; n < surfaceTiles[m].length; n++) {
                Tile tile = surfaceTiles[m][n];
                HexCoord point = new HexCoord(m - 100, n - 100);

                // If the tile is null, ignore the current iteration
                if (tile == null) {
                    continue;
                }

                // Increment the totalBarrackStars if they are a plaza and don't count plazas for districts
                if (tile.getPlaza() && tile.getDistrictType() == District.BARRACKS) {
                    totalBarrackStars += tile.getStars(tile);
                    continue;
                }

                // Count the emptySpaces of the current tile
                HexCoord[] surroundingHexCoords = point.getSurroundings();
                int emptySpaces = 0;
                // Count how many of the surrounding spaces are empty
                for (int j = 0; j < surroundingHexCoords.length; j++) {
                    if (getTile(surroundingHexCoords[j]) == null) {
                        emptySpaces ++;
                    }
                }

                // Increment the district count
                if (tile.getDistrictType() == District.BARRACKS) {
                    if (emptySpaces >= 1) {
                        if (variant && (emptySpaces == 3 || emptySpaces == 4)) {
                            totalValidBarracks += 2 * (tile.getHeight() + 1);
                        }
                        else {
                            totalValidBarracks += tile.getHeight() + 1;
                        }
                    }
                }
            }
        }
        return totalBarrackStars * totalValidBarracks;
    }


    /**
     * Method to calculate the temple score for a single board given if the temple variant is enabled or not.
     * Note: This function does not use the helper function starCount to reduce complexity as that the use
     *       of that helper function here will increase the number of total iterations required to calculate the score.
     * @author u7330006
     * @param variant the temple scoring variant
     * @return the temple score for the board
     */
    public int calculateTempleScore(boolean variant) {
        // Initialize the temples and templeStars to be zero for each player
        int totalTempleStars = 0;
        int totalValidTemples = 0;

        // Iterate through the playerTiles to find templePlazas stars and temple districts to calculate scores
        for (int m = 0; m < surfaceTiles.length; m++) {
            for (int n = 0; n < surfaceTiles[m].length; n++) {
                Tile tile = surfaceTiles[m][n];
                HexCoord point = new HexCoord(m - 100, n - 100);

                // If the tile is null, ignore the current iteration
                if (tile == null) { continue; }

                // Increment the totalTempleStars if they are a plaza and don't count plazas for districts
                if (tile.getPlaza() && tile.getDistrictType() == District.TEMPLES) {
                    totalTempleStars += tile.getStars(tile);
                    continue;
                }

                // Check if the tile is surrounded
                boolean isSurrounded = true;
                HexCoord[] surroundingHexCoords = point.getSurroundings();
                // If any of the surrounding tiles are null, the temple isn't completely surrounded
                for (int j = 0; j < surroundingHexCoords.length; j++) {
                    if (getTile(surroundingHexCoords[j]) == null) {
                        isSurrounded = false;
                        break;
                    }
                }

                // Increment district count
                if (tile.getDistrictType() == District.TEMPLES) {
                    // If the temple is completely surrounded, the temple is valid for scoring
                    if (isSurrounded) {
                        if (variant && tile.getHeight() >= 1) {
                            totalValidTemples += 2 * (tile.getHeight() + 1);
                        } else {
                            totalValidTemples += tile.getHeight() + 1;
                        }
                    }
                }
            }
        }
        return totalTempleStars * totalValidTemples;
    }

    /**
     * Method to calculate the garden score for a single board given if the garden variant is enabled or not.
     * Note: This function does not use the helper function starCount to reduce complexity as that the use
     *       of that helper function here will increase the number of total iterations required to calculate the score.
     * @author u7330006
     * @param variant the garden scoring variant
     * @return the garden score for the board
     */
    public int calculateGardenScore(boolean variant) {
        // Initialize the gardens and gardenStars to be zero for each player
        int totalGardenStars = 0;
        int totalValidGardens = 0;

        // Iterate through the playerTiles to find gardenPlazas stars and garden districts to calculate scores
        for (int m = 0; m < surfaceTiles.length; m++) {
            for (int n = 0; n < surfaceTiles[m].length; n++) {
                Tile tile = surfaceTiles[m][n];
                HexCoord point = new HexCoord(m - 100, n - 100);

                // If the tile is null ignore the current iteration
                if (tile == null) { continue; }

                // Increment the totalTempleStars if they are a plaza and don't count plazas for districts
                if (tile.getPlaza() && tile.getDistrictType() == District.GARDENS) {
                    totalGardenStars += tile.getStars(tile);
                    continue;
                }

                // Check if the garden is adjacent to a lake
                HexCoord[] surroundingHexCoords = point.getSurroundings();
                boolean adjacentToLake = false;
                for (int j = 0; j < surroundingHexCoords.length; j++) {
                    if (isLakeSingleTile(surroundingHexCoords[j])) {
                        adjacentToLake = true;
                        break;
                    }
                }

                // Increment the district count
                if (tile.getDistrictType() == District.GARDENS) {
                    if (variant && adjacentToLake) {
                        totalValidGardens += 2 * (tile.getHeight()+1);
                    } else {
                        totalValidGardens += tile.getHeight()+1;
                    }
                }
            }
        }
        return totalGardenStars * totalValidGardens;
    }


    /**
     * Calculates the barracks score for this board's current state
     * @param variant Determines whether to use the variant calculation
     * @return A single number value representing this player's barracks score
     * @author u7683699
     */
    public int calculateBarracksScore(boolean variant) {

        int barracksStars = starCountOfType(District.BARRACKS);
        int totalBarracksScore = 0;

        var listOfCoords = getTilesOfType(District.BARRACKS);

        for (var coord : listOfCoords) {

            var adjacentTilePositions = coord.getSurroundings();
            var emptyAdjacentCount = 0;

            for (var adjacentTilePosition : adjacentTilePositions) {

                var adjacentTile = getTile(adjacentTilePosition);

                if (adjacentTile == null) {
                    emptyAdjacentCount += 1;
                }
            }

            if (emptyAdjacentCount >= 3 && variant) {
                totalBarracksScore += (getTile(coord).getHeight() + 1) * 2;
            } else if (emptyAdjacentCount >= 1) {
                totalBarracksScore += (getTile(coord).getHeight() + 1);
            }
        }
        return totalBarracksScore * barracksStars;
    }

    /**
     * Returns the star count of a given district plaza
     * @author u7683699
     *
     * @param district the type of district for which stars are being counted
     * @return star count*/
    private int starCountOfType(District district) {
        int stars = 0;
        for (int i = 0; i < surfaceTiles.length; i++) {
            for (int j = 0; j < surfaceTiles[i].length; j++) {
                if (surfaceTiles[i][j] != null && surfaceTiles[i][j].getDistrictType().equals(district)) {
                    if (surfaceTiles[i][j].getPlaza()) {
                        stars += surfaceTiles[i][j].getStars(surfaceTiles[i][j]);
                    }
                }
            }
        }
        return stars;
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
