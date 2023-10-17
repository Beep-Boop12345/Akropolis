package comp1140.ass2;

import comp1140.ass2.gittest.A;

import java.util.HashSet;
import java.util.Set;
import comp1140.ass2.gui.Viewer;

public class Akropolis {
    public final static String TILE_POOL = "2:01hbt02Mbq03qhb04Bhq05Bqq06Bht07gqq08qbm09qtm10qmb11Gqh12qmh13Ghq14qtb15hgm16Bmh17Mhg18Hmb19qhh20hgb21Mth22Mqq23Tqq24Gqq25qmg26mqq27qbm28Hqq29Thq30tqq31Tqh32Hgq33Hqq34Thb35htm36qmt37Hqq3:38hmb39qth40qbg41qhh42qhm43Tqq44hqq45qmh46Htm47Ghb48Bqh49Mqq4:50bqq51Bqq52Hqm53Gmh54Mqt55qht56Thm57qgh58qhh59qbh60qhb61qhm";


    private Viewer viewer;
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
            currentPlayers[i] = new Player(i,new Board(i),i+1);
        }
        this.constructionSite = new ConstructionSite(numberOfPlayers, new Piece[numberOfPlayers + 2]);
        this.stack = new Stack(getInitialStack());
        resupplyConstructionSite();
        this.viewer = new Viewer(this);
    }

    /** Returns the pieces that will be in a newly initialized stack
     * @author u7646615*/
    private Piece[] getInitialStack() {
        int idCap = switch (numberOfPlayers) {
            case 2 -> 37;
            case 3 -> 49;
            case 4 -> 61;
            default -> 0;
        };
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
        akropolis.resupplyConstructionSite();

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

    /**
     * Applies the move to the board and updates the turn
     * @param move The move to be applied
     * @author u7683699
     */
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
        // System.out.println(boardRadiusX);
        int boardRadiusY = player.getBoard().getBoardRadiusY();
        // System.out.println(boardRadiusY);
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
                        // System.out.println(move);
                        validMoves.add(move);
                        //We need not check validity of equivalent transforms
                        Move move1 = new Move(piece, new Transform(new HexCoord(x,y-1), Rotation.DEG_300));
                        // System.out.println(move1);
                        Move move2 = new Move(piece, new Transform(new HexCoord(x-1,y-1+(Math.abs(x) % 2)), Rotation.DEG_60));
                        // System.out.println(move2);
                        validMoves.add(move1);
                        validMoves.add(move2);
                        // System.out.println("_________________________");
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
                Move move0 = new Move (piece, transform);
                if(isMoveValid(move0)){
                    validMoves.add(move0);
                    //We need not check validity of equivalent transforms
                    Move move1 = new Move(piece, new Transform(new HexCoord(x,y+1), Rotation.DEG_120));
                    Move move2 = new Move(piece, new Transform(new HexCoord(x+1,y+(Math.abs(x) % 2)), Rotation.DEG_240));
                    validMoves.add(move1);
                    validMoves.add(move2);

                }
                transform = new Transform(new HexCoord(x,y) ,Rotation.DEG_180);
                move0 = new Move (piece, transform);
                if(isMoveValid(move0)){
                    validMoves.add(move0);
                    //We need not check validity of equivalent transforms
                    Move move1 = new Move(piece, new Transform(new HexCoord(x,y-1), Rotation.DEG_300));
                    Move move2 = new Move(piece, new Transform(new HexCoord(x-1,y-1+(Math.abs(x) % 2)), Rotation.DEG_60));
                    validMoves.add(move1);
                    validMoves.add(move2);
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
     * @author u7683699
     */
    public static int[] calculateHouseScores(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        return akropolis.calculateHouseScores();
    }

    /**
     * calculates the "House" component of the score for each player in this akropolis instance
     * @return The house scores for each player
     * @author u7683699
     */
    public int[] calculateHouseScores() {
        int[] houseScoreArray = new int[numberOfPlayers];
        boolean variant = scoreVariants[0];

        for (int i = 0; i < numberOfPlayers; i++) {
            houseScoreArray[i] = currentPlayers[i].getBoard().calculateHouseScore(variant);
        }
        return houseScoreArray;
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
        return akropolis.calculateMarketScores();
    }

    public int[] calculateMarketScores() {
        int[] marketScoreArray = new int[numberOfPlayers];
        boolean variant = scoreVariants[1];

        for (int i = 0; i < numberOfPlayers; i++) {
            marketScoreArray[i] = currentPlayers[i].getBoard().calculateMarketScore(variant);
        }
        return marketScoreArray;
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

        return akropolis.calculateBarracksScores();
    }

    /**
     * calculates the "Barracks" component of the score for each player in this akropolis instance
     * @return The barracks scores for each player
     * @author u7683699
     */
    public int[] calculateBarracksScores() {

        int[] barracksScoreArray = new int[numberOfPlayers];
        var variant = scoreVariants[2];

        for (int i = 0; i < numberOfPlayers; i++) {
            barracksScoreArray[i] = currentPlayers[i].getBoard().calculateBarracksScore(variant);
        }
        return barracksScoreArray;
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
        return akropolis.calculateTempleScores();
    }

    public int[] calculateTempleScores() {
        int[] templeScoreArray = new int[numberOfPlayers];
        boolean variant = scoreVariants[3];

        for (int i = 0; i < numberOfPlayers; i++) {
           templeScoreArray[i] = currentPlayers[i].getBoard().calculateTempleScore(variant);
        }
        return templeScoreArray;
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
        return akropolis.calculateGardenScores();
    }

    public int[] calculateGardenScores() {
        int[] gardenScoreArray = new int[numberOfPlayers];
        boolean variant = scoreVariants[4];

        for (int i = 0; i < numberOfPlayers; i++) {
            gardenScoreArray[i] = currentPlayers[i].getBoard().calculateGardenScore(variant);
        }
        return gardenScoreArray;
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
        Akropolis akropolis = new Akropolis(gameState);
        return akropolis.calculateCompleteScores();
    }

    public int[] calculateCompleteScores() {
        int[] completeScores = new int[numberOfPlayers];

        // Calculate scores for each district type
        int[] houseScores = calculateHouseScores();
        int[] marketScores = calculateMarketScores();
        int[] barrackScores = calculateBarracksScores();
        int[] templeScores = calculateTempleScores();
        int[] gardenScores = calculateGardenScores();
        int[] stones = calculatePlayerStones();

        for (int i = 0; i < numberOfPlayers; i++) {
            // Sum up scores for all district types and player stones
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
     * @author u7330006
     */

    public static String generateAIMove(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        return akropolis.generateAIMove(gameState, 2);
    }

    public String generateAIMove(String gameState, int depth) {
        int turn = new Akropolis(gameState).currentTurn;

        // Generate an array of validMoves to determine the best move
        Set<String> allValidMoves = generateAllValidMoves(gameState);
        String[] validMovesArray = allValidMoves.toArray(new String[0]);

        // Set default values for the moves in case it times out
        int bestScore = Integer.MIN_VALUE;
        String bestMove = null;

        // Apply a search through the moves to determine the best move
        for (String moveString : validMovesArray) {
            String gameAfterMove = applyMove(gameState, moveString);
            int eval = minimax(gameAfterMove, depth - 1, turn, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (eval > bestScore) {
                bestScore = eval;
                bestMove = moveString;
            }
        }

        return bestMove;
    }


    /**
     * Minimax algorithm on the current gameState using alpha-beta pruning for optimization.
     *
     * @param gameState The current game state to evaluate.
     * @param depth The search depth, indicating how many moves ahead to look.
     * @param player The current player's turn for which we want to find the best move.
     * @param alpha The best score for the maximizing player found so far in the current branch.
     * @param beta The best score for the minimizing player found so far in the current branch.
     * @param maximizingPlayer A boolean flag indicating whether the current player is maximizing
     *        (true for AI player) or minimizing (false for opponent).
     * @return The evaluation score for the uppermost move, considering the specified search depth
     *         and any pruning applied using alpha-beta.
     * @author u7330006
     */

    public static int minimax(String gameState, int depth, int player, int alpha, int beta, boolean maximizingPlayer) {
        if (depth == 0) {
            return calculateCompleteScores(gameState)[player];
        }

        Set<String> validMoves = generateAllValidMoves(gameState);
        for (String moveString : validMoves) {
            String gameAfterMove = applyMove(gameState, moveString);
            int eval = minimax(gameAfterMove, depth - 1, player, alpha, beta, !maximizingPlayer);

            if (maximizingPlayer) {
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            } else {
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
        }

        return maximizingPlayer ? alpha : beta;
    }



    /**
     * Given a state string, calculate the stone count for each player.
     * @author u7330006
     * @return array of stone counts for each player in ascending order
     */
    public static int[] calculatePlayerStones(String gameState) {
        Akropolis akropolis = new Akropolis(gameState);
        return akropolis.calculatePlayerStones();
    }

    public int[] calculatePlayerStones() {
      // Find the playerStones
      int[] playerStones = new int[numberOfPlayers];
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
