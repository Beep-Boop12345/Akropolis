package comp1140.ass2;

import java.util.HashSet;
import java.util.Set;

public class Akropolis {
    public final static String TILE_POOL = "2:01hbt02Mbq03qhb04Bhq05Bqq06Bht07gqq08qbm09qtm10qmb11Gqh12qmh13Ghq14qtb15hgm16Bmh17Mhg18Hmb19qhh20hgb21Mth22Mqq23Tqq24Gqq25qmg26mqq27qbm28Hqq29Thq30tqq31Tqh32Hgq33Hqq34Thb35htm36qmt37Hqq3:38hmb39qth40qbg41qhh42qhm43Tqq44hqq45qmh46Htm47Ghb48Bqh49Mqq4:50bqq51Bqq52Hqm53Gmh54Mqt55qht56Thm57qgh58qhh59qbh60qhb61qhm";

    public int numberOfPlayers;

    public Player[] currentPlayers;

    public ConstructionSite constructionSite;

    public Stack stack;

    public boolean[] scoreVariants = new boolean[5];

    //public GameState gameStage;

    public int currentTurn;

    /**
     * Constructs an Akropolis Class from its string representation for testing
     * @author u7683699
     * @param gameState The string representing a game state
     */
    public Akropolis(String gameState) {

        if (isStateStringWellFormed(gameState)) {
            //Breaks string to construct objects
            String[] components = gameState.split(";");
            String[] playerStrings = new String[components.length - 2];
            System.arraycopy(components, 2, playerStrings, 0, components.length - 2);


            currentTurn = Character.getNumericValue(components[1].charAt(0));

            currentPlayers = new Player[playerStrings.length];

            for (int i = 0; i < playerStrings.length; i++) {
                currentPlayers[i] = new Player(playerStrings[i]);
            }

            numberOfPlayers = playerStrings.length;

            constructionSite = new ConstructionSite(gameState);

            stack = new Stack(gameState);

            // Find the score variants for h,m,b,t,g
            for (int i = 0; i < scoreVariants.length; i++) {
                scoreVariants[i] = Character.isUpperCase(gameState.charAt(i + 1));
            }
        }


        // Error handling if the gameState is invalid
        else {
            throw new IllegalArgumentException("Invalid gameState: " + gameState);
        }
    }
    public Akropolis(int numberOfPlayers, boolean[] scoringVariants) {
        this.numberOfPlayers = numberOfPlayers;
        this.scoreVariants = scoringVariants;
        this.currentPlayers = new Player[numberOfPlayers];
        this.currentTurn = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            currentPlayers[i] = new Player(i,new Board(i),0);
        }
        this.constructionSite = new ConstructionSite(numberOfPlayers, new Piece[numberOfPlayers + 2]);
        this.stack = new Stack(getInitialStack());
        resupplyConstructionSite();
    }

    /** Returns the pieces that will be in a newly initialized stack
     * @author u7646615*/
    private Piece[] getInitialStack() {
        int idCap = 0;
        switch (numberOfPlayers) {
            case 2:
                idCap = 37;
                break;
            case 3:
                idCap = 49;
                break;
            case 4:
                idCap = 61;
                break;
        }
        Piece[] heldPieces = new Piece[idCap];
        for (int i = 0; i < idCap; i++) {
            String pieceIDString = String.valueOf(i+1);
            if (pieceIDString.length() == 1) {
                pieceIDString = "0" + pieceIDString;
            }
            heldPieces[i] = new Piece(pieceIDString);
        }
        return heldPieces;
    }



    /**
     * Given a move string, checks whether it is well-formed according to the specified rules.
     * @author u7330006
     * @param move a move string.
     * @return true is the move string is well-formed, false otherwise.
     */
    public static boolean isMoveStringWellFormed(String move) {
        if (move.length() < 10) { return false; }
        // General pattern of the moveString
        String pattern = "\\d{2}[NS]\\d{2}[EW]\\d{2}R[0-5]";

        // Check if the moveString contains "N00" or "W00"
        boolean hasInvalidCharacters = move.contains("N00") || move.contains("W00");

        // Set maxPieceID based on numberOfPlayers
        int maxPieceID = 61;
//        switch (numberOfPlayers) {
//            case 2: maxPieceID = 37; break;
//            case 3: maxPieceID = 49; break;
//            default: maxPieceID = 61;
//        }

        // Get the pieceID
        int pieceID;
        try {
            pieceID = Integer.parseInt(move.substring(0, 2));
        } catch (NumberFormatException e) {
            return false;
        }

        boolean validPieceID = pieceID > 0 && pieceID <= maxPieceID;

        return move.matches(pattern) && !hasInvalidCharacters && validPieceID;
    }

    /**
     * Given a state string, checks whether it is well-formed according to the specified rules.
     * <p>
     * In addition to the specified formatting rules, there are two additional things you must check.
     * 1. No duplicate tiles are contained within the state string (based on the tile ids).
     * 2. The tiles in the string must be playable for the given player count (Hint: use the TILE_POOL).
     * @author u7330006
     * @param gameState a state string.
     * @return true if the state string is well-formed, false otherwise.
     */

    public static boolean isStateStringWellFormed(String gameState) {
        // Parse the gameState string into its components, eg: 3hmbtg;01432;P00141S01E02R3;P10118S01W03R1;P20310S00W02R3;
        String[] components = gameState.split(";");
        if (!gameState.endsWith(";")) {return false; }

        // Check settings string
        String settings = components[0];
        if (settings.isEmpty() || !Character.isDigit(settings.charAt(0))) {
            return false;
        }

        int noOfPlayers = Integer.parseInt(settings.substring(0, 1));
        if (components.length != noOfPlayers + 2) {
            return false;
        }

        String scoring = settings.substring(1);
        if (!scoring.equalsIgnoreCase("hmbtg")) {
            return false;
        }

        // Initialize maximumTileID based on player count & hashset of all seen tileIDs
        int maxTileID;
        switch (noOfPlayers) {
            case 2: maxTileID = 37; break;
            case 3: maxTileID = 49; break;
            case 4: maxTileID = 61; break;
            default: return false;
        }

        Set<String> seenTileIDs = new HashSet<>();

        // Check shared string
        String shared = components[1];
        if (shared.isEmpty()) { return false; }

        try {
            int turn = Integer.parseInt(shared.substring(0, 1));
            if (turn < 0 || turn > noOfPlayers - 1) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }


        String constructionSite = shared.substring(1);
        if (constructionSite.length() % 2 == 1 || constructionSite.length() > 13) {
            return false;
        }

        // Check the tileIDs in constructionSite
        for (int i = 0; i < constructionSite.length(); i += 2) {
            String tileID = constructionSite.substring(i, i + 2);
            if (seenTileIDs.contains(tileID)) {
                return false; // Duplicate tile found
            } else {
                seenTileIDs.add(tileID);
            }

            try {
                int numTileID = Integer.parseInt(constructionSite.substring(i, i + 2));
                if (numTileID < 1 || numTileID > maxTileID) { return false; }
            } catch (NumberFormatException e) {
                return false;
            }
        }

        // Check all player strings
        for (int i = 2; i < components.length; i++) {
            String playerString = components[i];
            if (!playerString.startsWith("P")) {
                return false;
            }

            // Check stones
            if (playerString.length() < 4) { return false; }
            try {
                for (int j = 1; j < 4; j++) {
                    Integer.parseInt(String.valueOf(playerString.charAt(j)));
                }
            } catch (NumberFormatException e) {
                return false;
            }

            int playerID = Integer.parseInt(playerString.substring(1,2));
            if (playerID < 0 || playerID > noOfPlayers-1) { return false; }

            // Check the moveStrings
            String moves = playerString.substring(4);
            if (moves.length() % 10 != 0) { return false; }
            for (int j = 0; j < moves.length(); j += 10) {
                String moveString = moves.substring(j, j + 10);

                String tileID = moveString.substring(0, 2);
                try {
                    int numTileID = Integer.parseInt(tileID);
                    if (numTileID < 1 || numTileID > maxTileID) { return false; }
                    if (seenTileIDs.contains(tileID)) {
                        return false; // Duplicate tile found
                    } else {
                        seenTileIDs.add(tileID);
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
                if (!isMoveStringWellFormed(moveString)) { return false; }
            }

        }

        // Passes all checks
        return true;
    }

    /**
     * @author u7683699
     * Given a state string, checks whether the game has ended.
     * <p>
     * The game ends when there is only one tile left in the Construction Site and all other tiles have been played.
     *
     * @param gameState a state string.
     * @return true if the game is over, false otherwise.
     */
    public static boolean isGameOver(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);

        return akropolis.isGameOver();
    }

    /**
     * @author u7683699
     * Checks if this akropolis game instance has ended
     * @return true if the game is over, false otherwise.
     */
    public boolean isGameOver() {
        var siteEmpty = constructionSite.getCurrentPieceCount() <= 1;
        var stackEmpty = stack.getPieceCount() <= 0;

        return (siteEmpty && stackEmpty);
    }

    /**
     * Given a state string, checks whether the Construction Site needs to be resupplied. If it does, resupplies the
     * Construction Site, otherwise does nothing.
     * @author u7646615
     * <p>
     * The Construction Site needs to be refilled if both:
     * 1. There is only one tile remaining in the Construction Site
     * 2. The game is not over
     * <p>
     * When resupplying the Construction Site, appropriate unused tiles are randomly drawn from the TILE_POOL for the
     * player count.
     * <p>
     * Do not advance the player turn.
     * <p>
     * Note: In testing, this function is called many times, so it can take some time to run.
     *
     * @param gameState a state string.
     * @return the state string with the Construction Site refilled if needed, otherwise the unmodified state string.
     */

    public static String resupplyConstructionSite(String gameState) {
        //Make Akropolis Instance
        Akropolis akropolis = new Akropolis(gameState);

        //Resupply Its Construction Site
        akropolis.constructionSite.resupply(akropolis.stack);

        /*gets the new string representation and incorporates it into the gameState string*/
        String[] gameStateAsArray = gameState.split(";");
        gameStateAsArray[1] = gameStateAsArray[1].substring(0,1) + akropolis.constructionSite.toString();
        return String.join(";", gameStateAsArray) + ";";
    }

    private void resupplyConstructionSite() {
        constructionSite.resupply(stack);
    }

    /**
     * Given a state string, a player, and a position, finds the height of the tile in that player's city at the
     * position.
     * @author u7646615
     * <p>
     * If there is no tile at the position, the height is 0.
     * <p>
     * Hint: remember that each player's starting tile is not included in the state string.
     *
     * @param gameState a state string.
     * @param playerId  the id of a player.
     * @param position  a position string.
     * @return the height of the tile for the player at the given position.
     */

    public static int heightAt(String gameState, int playerId, String position) {

        Akropolis akropolis = new Akropolis(gameState);

        Player player = akropolis.currentPlayers[playerId];
        HexCoord tilePosition = new HexCoord(position);

        Tile tile = player.getBoard().getTile(tilePosition);
        if (tile == null) {
            return 0;
        }

        return tile.getHeight() + 1;
    }

    /**
     * Given a state string, a player, and a position, finds the character representing the tile in that player's city
     * at the position.
     * @author u7646615
     * <p>
     * The characters for each tile is given in the specification.
     * <p>
     * If the tile is a plaza, the character is uppercase. A lowercase letter is used otherwise.
     * <p>
     * If there is no tile at the position, returns null.
     * <p>
     * Hint: remember that each player's starting tile is not included in the state string.
     *
     * @param gameState a state string.
     * @param playerId  the id of a player.
     * @param position  a position string.
     * @return The character representing the tile at the position (null if there is no tile).
     */

    public static Character tileAt(String gameState, int playerId, String position) {

        Akropolis akropolis = new Akropolis(gameState);

        Player player = akropolis.currentPlayers[playerId];
        HexCoord tilePosition = new HexCoord(position);

        Tile tile = player.getBoard().getTile(tilePosition);
        if (tile == null) {
            return null;
        }
        return tile.toStringRep();
    }

    /**
     * Given a state string and a move string, checks whether the move can be made.
     * <p>
     * A move is valid if the following conditions all hold:
     * 1. The tile is in the Construction Site.
     * 2. The player has enough stones to play the tile.
     * 3. The tile for the given rotation can be played at the given position on the player's board.
     * <p>
     * When playing a tile on a space with nothing beneath it, it must be adjacent to another tile.
     * <p>
     * When playing a tile on a higher level, all tiles directly below it must be at the same level and the tile must
     * be placed to cover at least two different pieces.
     * <p>
     * Hint: a piece cannot overlap another piece at the same level.
     *
     * @param gameState a state string.
     * @param move      a move string.
     * @return true if the move can be played, false otherwise.
     */

    public static boolean isMoveValid(String gameState, String move) {

        Akropolis akropolis = new Akropolis(gameState);

        /*Creates the objects needed for computation*/
        Move moveObject = new Move(move);

        return akropolis.isMoveValid(moveObject);
    }

    public boolean isMoveValid(Move move) {
        if (move == null) {
            return false;
        }
        int price = constructionSite.findPrice(move.getPiece());
        Player player = currentPlayers[currentTurn];

        if (price < 0 || price > player.getStones()) {
            return false;
        }

        return player.getBoard().isValidPlacement(move);
    }

    /**
     * Returns true if a given piece can be purchased
     * @author u7646615
     * @param piece piece that could be purchased
     * @return true if it can be purchased*/
    public boolean canPieceBePurchased(Piece piece) {
        int price = constructionSite.findPrice(piece);
        Player player = currentPlayers[currentTurn];

        return !(price < 0 || price > player.getStones());
    }

    /**
     * Given a state string and a move string, apply the move to the board.
     * @author u7646615
     * <p>
     * Ensure the Construction Site is resupplied at the end of the turn if needed.
     * Only advance the turn of the move does not end the game.
     * <p>
     * You may assume that the move is valid.
     *
     * @param gameState a state string.
     * @param move      a move string.
     * @return The state string after applying the move.
     */
    public static String applyMove(String gameState, String move) {

        Akropolis akropolis = new Akropolis(gameState);
        Move moveObject = new Move(move);

        var currentTurn = akropolis.currentTurn;

        akropolis.applyMove(moveObject);

        /*create new gameState string*/
        String[] gameStateArr = gameState.split(";");
        for (int i = 0; i < akropolis.numberOfPlayers; i++) {
            if (i == currentTurn) {
                /*Account for stone cost*/
                String stoneString = String.valueOf(akropolis.currentPlayers[currentTurn].getStones());
                if (stoneString.length() == 1) {
                    stoneString = "0" + stoneString;
                }
                String purchasedPlayer = gameStateArr[i + 2].substring(0,2) +
                        stoneString +
                        gameStateArr[i + 2].substring(4);
                gameStateArr[i + 2] = purchasedPlayer + move;
            }
        }

        gameStateArr[1] = String.valueOf(akropolis.currentTurn) + akropolis.constructionSite;
        StringBuilder newGameState = new StringBuilder();
        for (String gameStringComponent : gameStateArr) {
            newGameState.append(gameStringComponent).append(";");
        }
        return newGameState.toString();
    }

    public void applyMove(Move move) {

        /*not required but will be useful when game becomes object reliant*/
        if (!isMoveValid(move)) {
            return;
        }

        var player = currentPlayers[currentTurn];

        /*Compute changes to objects*/
        player.applyMove(constructionSite, move);
        constructionSite.resupply(stack);

        /*Update turn if needed*/
        if (!isGameOver()) {
            currentTurn = (currentTurn + 1) % numberOfPlayers;
        }
    }

    /**
     * Given a state string, returns a set of move strings containing all the moves that can be played.
     * @author u7646615
     *
     * @param gameState a state string.
     * @return A set containing all moves that can be played.
 */
    public static Set<String> generateAllValidMoves(String gameState) {

        Akropolis akropolis = new Akropolis(gameState);

        var moveSet = akropolis.generateAllValidMoves();

        Set<String> validMoves = new HashSet<>();

        for (var move : moveSet) {
            validMoves.add(move.toString());
        }

        return validMoves;
    }

    private Set<Move> generateAllValidMoves() {

        // Initializes set with possible legal moves
        Set<Move> validMoves = new HashSet<>();

        if (isGameOver()) {
            return validMoves;
        }

        Player player = currentPlayers[currentTurn];

        //Get values to limit search
        int boardRadiusX = player.getBoard().getBoardRadiusX();
        System.out.println(boardRadiusX);
        int boardRadiusY = player.getBoard().getBoardRadiusY();
        System.out.println(boardRadiusY);
        int stones = player.getStones();
        Piece[] pieces = constructionSite.getCurrentPieces();
        int avaliablePiece = constructionSite.getCurrentPieceCount();

        // Iterate through all finite moves
        for (int x = -(boardRadiusX + 3); x < boardRadiusX + 3; x++) {
            for (int y = -(boardRadiusY+3); y < boardRadiusY + 3; y++) {
                Transform transform = new Transform(new HexCoord(x,y) ,Rotation.DEG_0);
                Move move0 = new Move (pieces[0], transform);
                if(isMoveValid(move0)){
                    validMoves.add(move0);
                    //We need not check validity of purchasable pieces
                    for (int s = 0; s < Math.min(stones+1, avaliablePiece); s++) {
                        Piece piece = pieces[s];
                        Move move = new Move(piece, transform);
                        validMoves.add(move);
                        //We need not check validity of equivalent transforms
                        Move move1 = new Move(piece, new Transform(new HexCoord(x,y+1), Rotation.DEG_120));
                        Move move2 = new Move(piece, new Transform(new HexCoord(x+1,y+(Math.abs(x) % 2)), Rotation.DEG_240));
                        validMoves.add(move1);
                        validMoves.add(move2);
                    }
                }
                transform = new Transform(new HexCoord(x,y) ,Rotation.DEG_180);
                move0 = new Move (pieces[0], transform);
                if(isMoveValid(move0)){
                    validMoves.add(move0);
                    //We need not check validity of purchasable pieces
                    for (int s = 0; s < Math.min(stones+1, avaliablePiece); s++) {
                        Piece piece = pieces[s];
                        Move move = new Move(piece, transform);
                        System.out.println(move);
                        validMoves.add(move);
                        //We need not check validity of equivalent transforms
                        Move move1 = new Move(piece, new Transform(new HexCoord(x,y-1), Rotation.DEG_300));
                        System.out.println(move1);
                        Move move2 = new Move(piece, new Transform(new HexCoord(x-1,y-1+(Math.abs(x) % 2)), Rotation.DEG_60));
                        System.out.println(move2);
                        validMoves.add(move1);
                        validMoves.add(move2);
                        System.out.println("_________________________");
                    }
                }

            }
        }
        return validMoves;
    }

    /**
     * Returns just the valid moves for one piece
     * @author u7646615
     * <p>
     * It only returns valid moves that are geometrically unique.
     * @param piece the piece for which moves should be found
     * @return the set containing all the moves that it can make.
     * */
    public Set<Move> generateAllValidMovesOfPiece(Piece piece) {

        // Initializes set with possible legal moves
        Set<Move> validMoves = new HashSet<>();

        if (isGameOver()) {
            return validMoves;
        }

        Player player = currentPlayers[currentTurn];

        //Get values to limit search
        int boardRadiusX = player.getBoard().getBoardRadiusX();
        int boardRadiusY = player.getBoard().getBoardRadiusY();

        // Iterate through all finite moves
        for (int x = -(boardRadiusX + 3); x < boardRadiusX + 3; x++) {
            for (int y = -(boardRadiusY+3); y < boardRadiusY + 3; y++) {
                Transform transform = new Transform(new HexCoord(x,y) ,Rotation.DEG_0);
                Move move = new Move (piece, transform);
                if(isMoveValid(move)){
                    validMoves.add(move);
                }
                transform = new Transform(new HexCoord(x,y) ,Rotation.DEG_60);
                move = new Move (piece, transform);
                if(isMoveValid(move)){
                    validMoves.add(move);
                }

            }
        }
        return validMoves;
    }

    /**
     * Given a state string, calculates the "House" component of the score for each player.
     * <p>
     * Task 14 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state. string.
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * Recall that the number of points that can be earned for each House district is equal to the district's height.
     * <p>
     * Each House plaza is worth one star.
     * <p>
     * A player only earn points for House districts that are a part their largest group of adjacent houses.
     * If there are multiple largest groups, choose the one that scores the most.
     * <p>
     * Recall that the score is the product of the stars and the points.
     * <p>
     * For the variant, if the player's largest group of adjacent houses is 10 or more, the points for those houses are
     * doubled.
     *
     * @param gameState a state string.
     * @return An array containing the "House" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateHouseScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);

        return akropolis.calculateHouseScores();

    }

    public int[] calculateHouseScores() {


        int[] houseScoreArray = new int[numberOfPlayers];
        var variant = scoreVariants[0];

        for (int i = 0; i < numberOfPlayers; i++) {
            houseScoreArray[i] = currentPlayers[i].getBoard().calculateHouseScore(variant);
        }
        System.out.println("_______");
        return houseScoreArray;
    }

    /**
     * A simple index finder method to find the index of a HexCoord in a HexCoord array.
     * Returns -1 if the element is not in the array.
     * @param hexCoords the array you are searching through
     * @param hexCoord the element in the array you are serching for
     * @return the index of the element in the array
     */
    public static int indexOf(HexCoord[] hexCoords, HexCoord hexCoord) {
        for (int index = 0; index < hexCoords.length; index++) {
            if (hexCoords[index].equals(hexCoord)) {
                return index;
            }
        }
        return -1;
    }


    /**
     * There's no simple getHexCoord method in board since tiles can be duplicate to one another other
     * to find a HexCoord of an adjacent tile we have to reference the original tile's HexCoord and
     * determine it based on that and its position in the surroundingTileHexCoord array found
     * using the getSurroundings method in the HexCoord class
     * @param board the original board
     * @param tileToFind the surroundingTile HexCoord you're searching for
     * @param point the original point in which the surroundingTile is adjacent to
     * @return the HexCoord of the surroundingTile
     */
    public static HexCoord findSurroundingHexCoord(Board board, Tile tileToFind, HexCoord point) {
        HexCoord[] surroundingHexCoords = point.getSurroundings();

        for (HexCoord surroundings : surroundingHexCoords) {
            if (board.getTile(surroundings) == tileToFind) {
                int x = point.getX();
                int y = point.getY();
                int offset = Math.abs(x) % 2;
                int surroundingTileIndex = indexOf(surroundingHexCoords, surroundings);

                HexCoord surroundingHexCoord = switch (surroundingTileIndex) {
                    case 0 -> new HexCoord(x, y + 1);
                    case 1 -> new HexCoord(x + 1, y + offset);
                    case 2 -> new HexCoord(x + 1, y - 1 + offset);
                    case 3 -> new HexCoord(x, y - 1);
                    case 4 -> new HexCoord(x - 1, y - 1 + offset);
                    case 5 -> new HexCoord(x - 1, y + offset);
                    default -> null;
                };

                return surroundingHexCoord;
            }
        }

        return null; // Return null only if no matching tile is found in any surroundingHexCoords.
    }


    /**
     * Given a house district tile, the board it came from and its corresponding HexCoord
     * Return a set of house tiles that are directly adjacent to one another.
     * This function is a recursive function taking in a Set of tiles and will stop recursing once it can no longer
     * find adjacent house tiles within the board to add to the group of adjacent house tiles.
     * Base Case: Once ALL the surrounding tiles are no longer houses within the board.
     * @param board the board all the tiles originate from
     * @param tile the tile you are searching for adjacent house groups for
     *             (this will change during recursion)
     * @param point the HexCoord of the tile as we cannot determine HexCoords just based on tiles
     *                 (as there are duplicate tiles in board)
     * @param group a set of tiles that represent the largest group of adjacent houses corresponding to a tile
     *              (this will change during recursion)
     */

    public static void findHouseGroup(Board board, Tile tile, HexCoord point, Set<Tile> group) {
        group.add(tile);

        HexCoord[] surroundingHexCoords = point.getSurroundings();

        // Base Case: If all the surroundingTiles are no longer houses that are within the board don't recurse
        boolean notAllValidHouses = true;
        for (HexCoord surroundings : surroundingHexCoords) {
            Tile surroundingTile = board.getTile(surroundings);
            HexCoord surroundingTileHexCoord = findSurroundingHexCoord(board, tile, point);
            // If one of the surroundingTiles is valid stop searching for valid tiles and recurse
            if (tile != null && point != null && surroundingTileHexCoord != null) {
                if (board.inBounds(surroundingTileHexCoord) && (surroundingTile.getDistrictType() == District.HOUSES)) {
                    notAllValidHouses = false;
                    break;
                }
            }
        }

        // Only recurse if one of the surrounding tiles is a house and is within the board
        if (!notAllValidHouses) {
            for (HexCoord surroundings : surroundingHexCoords) {
                Tile surroundingTile = board.getTile(surroundings);
                HexCoord surroundingTileHexCoord = findSurroundingHexCoord(board, tile, point);

                // If the adjacent tile is a district house add it to the group
                if (board.inBounds(surroundingTileHexCoord) &&
                        surroundingTile != null &&
                        !group.contains(surroundingTile) &&
                        surroundingTile.getDistrictType() == District.HOUSES &&
                        !surroundingTile.getPlaza()) {
                    findHouseGroup(board, surroundingTile, surroundingTileHexCoord, group);
                }
            }
        }
    }



    /**
     * Given a state string, calculates the "Market" component of the score for each player.
     * <p>
     * Task 15 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * Recall that the number of points that can be earned for each Market district is equal to the district's height.
     * <p>
     * Each Market plaza is worth two stars.
     * <p>
     * A player only earn points for Market districts that are not adjacent to other Market districts.
     * <p>
     * Recall that the score is the product of the stars and the points.
     * <p>
     * For the variant, if a Market district is adjacent to a Market plaza, its points are doubled.
     * @author u7330006
     * @param gameState a state string.
     * @return An array containing the "Market" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateMarketScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] marketScores = new int[numberOfPlayers];
        boolean marketScoringVar = akropolis.scoreVariants[1];

        // Iterate through all the player strings to calculate each player's score
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = akropolis.currentPlayers[i];
            Board board = player.getBoard();
            Tile[][] playerTiles = board.getSurfaceTiles();

            // Initialize the markets and marketStars to be zero for each player
            int totalMarketStars = 0;
            int totalValidMarkets = 0;

            // Iterate through the playerTiles to find marketPlazas stars and market districts to calculate scores
            for (int m = 0; m < playerTiles.length; m++) {
                for (int n = 0; n < playerTiles[m].length; n++) {
                    Tile tile = playerTiles[m][n];
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
                        Tile surroundingTile = board.getTile(surroundingHexCoords[j]);
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
                            if (marketScoringVar && adjacentMarketPlazas >= 1) {
                                    totalValidMarkets += 2 * (tile.getHeight() + 1);
                            }
                            else {
                                totalValidMarkets += tile.getHeight()+1;
                            }
                        }
                    }
                }
            }
            int marketScore = totalMarketStars * totalValidMarkets;
            marketScores[i] = marketScore;
        }
        return marketScores;
    }

    /**
     * Given a state string, calculates the "Barracks" component of the score for each player.
     * <p>
     * Task 16 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * Recall that the number of points that can be earned for each Barracks district is equal to the district's height.
     * <p>
     * Each Barracks plaza is worth two stars.
     * <p>
     * A player only earn points for Barracks districts on the edge of their city (has at least one empty adjacent
     * space).
     * <p>
     * Recall that the score is the product of the stars and the points.
     * <p>
     * For the variant, if a Barracks district has 3 or 4 adjacent empty spaces, its points are doubled.
     * @author u7330006
     * @param gameState a state string.
     * @return An array containing the "Barracks" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateBarracksScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] barrackScores = new int[numberOfPlayers];
        boolean barrackScoringVar = akropolis.scoreVariants[2];

        // Iterate through all the player strings to calculate each player's score
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = akropolis.currentPlayers[i];
            Board board = player.getBoard();
            Tile[][] playerTiles = board.getSurfaceTiles();

            // Initialize the barracks and barrackStars to be zero for each player
            int totalBarrackStars = 0;
            int totalValidBarracks = 0;

            // Iterate through the playerTiles to find barrackPlazas stars and barrack districts to calculate scores
            for (int m = 0; m < playerTiles.length; m++) {
                for (int n = 0; n < playerTiles[m].length; n++) {
                    Tile tile = playerTiles[m][n];
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
                        if (board.getTile(surroundingHexCoords[j]) == null) {
                            emptySpaces ++;
                        }
                    }

                    // Increment the district count
                    if (tile.getDistrictType() == District.BARRACKS) {
                        if (emptySpaces >= 1) {
                            if (barrackScoringVar && (emptySpaces == 3 || emptySpaces == 4)) {
                                totalValidBarracks += 2 * (tile.getHeight() + 1);
                            }
                            else {
                                totalValidBarracks += tile.getHeight() + 1;
                            }
                        }
                    }
                }
            }
            int barrackScore = totalBarrackStars * totalValidBarracks;
            barrackScores[i] = barrackScore;
        }
        return barrackScores;
    }



    /**
     * Given a state string, calculates the "Temple" component of the score for each player.
     * <p>
     * Task 17 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * Recall that the number of points that can be earned for each Temple district is equal to the district's height.
     * <p>
     * Each Temple plaza is worth two stars.
     * <p>
     * A player only earn points for Temple districts that are completely surrounded (ignoring the heights of the
     * surrounding tiles).
     * <p>
     * Recall that the score is the product of the stars and the points.
     * <p>
     * For the variant, if a Temple is on a level higher than 1, its points are doubled.
     * @author u7330006
     * @param gameState a state string.
     * @return An array containing the "Temple" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateTempleScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] templeScores = new int[numberOfPlayers];
        boolean templeScoringVar = akropolis.scoreVariants[3];

        // Iterate through all the player strings to calculate each player's score
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = akropolis.currentPlayers[i];
            Board board = player.getBoard();
            Tile[][] playerTiles = board.getSurfaceTiles();

            // Initialize the temples and templeStars to be zero for each player
            int totalTempleStars = 0;
            int totalValidTemples = 0;

            // Iterate through the playerTiles to find templePlazas stars and temple districts to calculate scores
            for (int m = 0; m < playerTiles.length; m++) {
                for (int n = 0; n < playerTiles[m].length; n++) {
                    Tile tile = playerTiles[m][n];
                    HexCoord point = new HexCoord(m - 100, n - 100);

                    // If the tile is null, ignore the current iteration
                    if (tile == null) {
                        continue;
                    }

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
                        if (board.getTile(surroundingHexCoords[j]) == null) {
                            isSurrounded = false;
                            break;
                        }
                    }

                    // Increment district count
                    if (tile.getDistrictType() == District.TEMPLES) {
                        // If the temple is completely surrounded, the temple is valid for scoring
                        if (isSurrounded) {
                            if (templeScoringVar && tile.getHeight() >= 1) {
                                    totalValidTemples += 2 * (tile.getHeight() + 1);
                            } else {
                                totalValidTemples += tile.getHeight() + 1;
                            }
                        }
                    }
                }
            }
            int templeScore = totalTempleStars * totalValidTemples;
            templeScores[i] = templeScore;
        }
        return templeScores;
    }



    /**
     * Given a state string, calculates the "Garden" component of the score for each player.
     * <p>
     * Task 18 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.     q
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * Recall that the number of points that can be earned for each Garden district is equal to the district's height.
     * <p>
     * Each Garden plaza is worth three stars.
     * <p>
     * Garden districts always score points.
     * <p>
     * Recall that the score is the product of the stars and the points.
     * <p>
     * For the variant, if a Garden is adjacent to a "lake", its points are doubled.
     * <p>
     * A lake is an empty space that is completely surrounded by tiles.
     * @author u7330006
     * @param gameState a state string.
     * @return An array containing the "Garden" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateGardenScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        int numberOfPlayers = akropolis.numberOfPlayers;
        int[] gardenScores = new int[numberOfPlayers];
        boolean gardenScoringVar = akropolis.scoreVariants[4];

        // Iterate through all the player strings to calculate each player's score
        for (int i = 0; i < numberOfPlayers; i++) {
            Player player = akropolis.currentPlayers[i];
            Board board = player.getBoard();
            Tile[][] playerTiles = board.getSurfaceTiles();

            // Initialize the gardens and gardenStars to be zero for each player
            int totalGardenStars = 0;
            int totalValidGardens = 0;

            // Iterate through the playerTiles to find gardenPlazas stars and garden districts to calculate scores
            for (int m = 0; m < playerTiles.length; m++) {
                for (int n = 0; n < playerTiles[m].length; n++) {
                    Tile tile = playerTiles[m][n];
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
                        if (board.isLakeSingleTile(surroundingHexCoords[j])) {
                            adjacentToLake = true;
                            break;
                        }
                    }

                    // Increment the district count
                    if (tile.getDistrictType() == District.GARDENS) {
                        if (gardenScoringVar && adjacentToLake) {
                                totalValidGardens += 2 * (tile.getHeight()+1);
                        } else {
                            totalValidGardens += tile.getHeight()+1;
                        }
                    }
                }
            }
            int gardenScore = totalGardenStars*totalValidGardens;
            gardenScores[i] = gardenScore;
        }
        return gardenScores;
    }


    /**
     * Given a state string, calculate the score for each player.
     * @author u7646615
     * <p>
     * Task 19 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.
     * <p>
     * To get full marks for the variant, the users playing your game must be able to select it as an option in the GUI.
     * <p>
     * The score for a player is the sum of scores for each district type, plus the number of stones that player has.
     *
     * @param gameState a state string.
     * @return An array containing the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateCompleteScores(String gameState) {
        /*Finds how many players*/
        int playerCount = Integer.parseInt(gameState.substring(0,1));
        int[] completeScores = new int[playerCount];
        int[] houseScores = calculateHouseScores(gameState);
        int[] marketScores = calculateMarketScores(gameState);
        int[] barrackScores = calculateBarracksScores(gameState);
        int[] templeScores = calculateTempleScores(gameState);
        int[] gardenScores = calculateGardenScores(gameState);
        int[] stones = new int[playerCount];
        for (int i = 0; i < playerCount; i ++) {
            /*Will retrieve stone count from player object*/
            Player hldrPlayer = new Player(gameState.split(";")[2+i]);
            stones[i] = hldrPlayer.getStones();
        }
        /*Will calculate final scores*/
        for (int i = 0; i < playerCount; i++) {
            completeScores[i] = houseScores[i] +
                    marketScores[i] +
                    barrackScores[i] +
                    templeScores[i] +
                    gardenScores[i] +
                    stones[i];
        }
        return completeScores;
    }

    /**
     * Generate a move using your AI.
     * <p>
     * The tests for this task only test whether the move you return is valid. You must however create and advanced
     * AI. That is, incorporate some kind of lookahead and use heuristics to determine the best move.
     * <p>
     * You must also implement the AI in your GUI.
     *
     * @param gameState a state string.
     * @return An AI generated move.
     */
    public static String generateAIMove(String gameState) {
        return ""; // FIXME Task 22
    }


    /**
     * Given a state string, calculate the stone count for each player.
     * @return array of stone counts for each player in ascending order
     */
    public static int[] calculatePlayerStones(String gameState) {
      Akropolis akropolis = new Akropolis(gameState);
      int numberOfPlayers = akropolis.numberOfPlayers;
      int[] playerStones;
      Player[] currentPlayers = akropolis.currentPlayers;

      // Find the playerStones
      playerStones = new int[numberOfPlayers];
      for (int i = 0; i < numberOfPlayers; i++) {
            playerStones[i] = currentPlayers[i].getStones();
      }
        return playerStones;
    }

    public Stack getStack() {
        return stack;
    }

    public Player getCurrentPlayer() {
        return currentPlayers[currentTurn];
    }

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    //    public static Piece[] createPieceArray(int numberOfPlayers) {
//
//        String currentPool = TILE_POOL;
//        Piece[] pieceArray = new Piece[pieceCount];
//
//        for (int i = 1; i < numberOfPlayers + 1; i++) {
//            String splitPoint  = Integer.toString(i + 1) + ":";
//            String[] splitPool = currentPool.split(splitPoint);
//            currentPool = splitPool[0];
//            for (int j = 0; j < (currentPool.length()/5); j++) {
//                String subString = currentPool.substring((j*5), (j*5) + 5);
//                String pieceId = subString.substring(0,2);
//                int pieceIndex = Integer.parseInt(pieceId);
//
//                pieceArray[pieceIndex] = new Piece(subString);
//            }
//            System.out.println(currentPool);
//            currentPool = splitPool[1];
//            System.out.println(currentPool);
//        }
//
//        return new Piece[0];
//    }



}
