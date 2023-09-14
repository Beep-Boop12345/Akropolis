package comp1140.ass2;

import java.util.Set;

public class Akropolis {
    public final static String TILE_POOL = "2:01hbt02Mbq03qhb04Bhq05Bqq06Bht07gqq08qbm09qtm10qmb11Gqh12qmh13Ghq14qtb15hgm16Bmh17Mhg18Hmb19qhh20hgb21Mth22Mqq23Tqq24Gqq25qmg26mqq27qbm28Hqq29Thq30tqq31Tqh32Hgq33Hqq34Thb35htm36qmt37Hqq3:38hmb39qth40qbg41qhh42qhm43Tqq44hqq45qmh46Htm47Ghb48Bqh49Mqq4:50bqq51Bqq52Hqm53Gmh54Mqt55qht56Thm57qgh58qhh59qbh60qhb61qhm";

    public static int numberOfPlayers; // Code to find numberOfPlayers given stateString is commented out in GameState

    public static Player[] currentPlayers;

    public static int pieceCount;

    public static Piece[] pieces;

    public static ConstructionSite constructionSite;

    public static Stack stack;

    public static Boolean[] scoreVariants = new Boolean[5];

    public static GameState gameStage;

    public static int curentTurn;

    /*Cycles turn*/
    public static void nextTurn() {

    }

    /**
     * Given a move string, checks whether it is well-formed according to the specified rules.
     *
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
        int maxPieceID;
        switch (numberOfPlayers) {
            case 2:
                maxPieceID = 37;
                break;
            case 3:
                maxPieceID = 49;
                break;
            default:
                maxPieceID = 61;
        }

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
     *
     * @param gameState a state string.
     * @return true if the state string is well-formed, false otherwise.
     */
    // Dependency on isSettingsStringWellFormed, isSharedStringWellFormed, and isPlayerStringWellFormed
    public static boolean isStateStringWellFormed(String gameState) {
        // Parse the gameState string into its components
        String[] components = gameState.split(";");
        if (components.length < 4) { return false; }

        // Check settings string
        String settings = components[0];
        if (!isSettingsStringWellFormed(settings)) { return false; }

        // Check shared string
        String shared = components[1];
        if (!isSharedStringWellFormed(shared)) { return false; }

        // Check all player strings
        for (int i = 2; i < components.length; i++) {
            String playerString = components[i];
            if (!isPlayerStringWellFormed(playerString)) {
                return false;
            }
        }

        // todo isSettingStringWellFormed, isSharedStringWellFormed,
        //  isPlayerStringWellFormed, duplicateTiles, playableTilesForPlayerCount

        return true;
    }

    // Helper functions for Task 5
    public static boolean isSettingsStringWellFormed(String settingsString) { return false; } // todo
    public static boolean isSharedStringWellFormed(String sharedString) { return false; } // todo

    public static boolean isPlayerStringWellFormed(String playerString) { return false; } //todo


    /**
     * Given a state string, checks whether the game has ended.
     * <p>
     * The game ends when there is only one tile left in the Construction Site and all other tiles have been played.
     *
     * @param gameState a state string.
     * @return true if the game is over, false otherwise.
     */
    public static boolean isGameOver(String gameState) {
        return false; // FIXME Task 6
    }

    /**
     * Given a state string, checks whether the Construction Site needs to be resupplied. If it does, resupplies the
     * Construction Site, otherwise does nothing.
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
        ConstructionSite constructionSite = new ConstructionSite(gameState);
        constructionSite.resupply();
        String[] gameStateAsArray = gameState.split(";");
        gameStateAsArray[1] = gameStateAsArray[1].substring(0,1) + constructionSite.toString();
        return String.join(";", gameStateAsArray); // FIXME Task 7
    }

    /**
     * Given a state string, a player, and a position, finds the height of the tile in that player's city at the
     * position.
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
        String playerString;
        playerString = gameState.split(";")[playerId + 2];
        Player player = new Player(playerString);
        Tile tile = player.getBoard().getTile(new HexCoord(position));
        if (tile == null) {
            return 0;
        }
        return tile.getHeight() + 1;
    }

    /**
     * Given a state string, a player, and a position, finds the character representing the tile in that player's city
     * at the position.
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
        String playerString;
        playerString = gameState.split(";")[playerId + 2];
        Player player = new Player(playerString);
        Tile tile = player.getBoard().getTile(new HexCoord(position));
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
        return false; // FIXME Task 11
    }

    /**
     * Given a state string and a move string, apply the move to the board.
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
        return ""; // FIXME Task 12
    }

    /**
     * Given a state string, returns a set of move strings containing all the moves that can be played.
     *
     * @param gameState a state string.
     * @return A set containing all moves that can be played.
     */
    public static Set<String> generateAllValidMoves(String gameState) {
        return null; // FIXME Task 14
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
        return new int[0]; // FIXME Task 15 & 23A
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
     *
     * @param gameState a state string.
     * @return An array containing the "Market" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateMarketScores(String gameState) {
        return new int[0]; // FIXME Task 16 & 23B
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
     *
     * @param gameState a state string.
     * @return An array containing the "Barracks" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateBarracksScores(String gameState) {
        return new int[0]; // FIXME Task 17 & 23C
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
     *
     * @param gameState a state string.
     * @return An array containing the "Temple" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateTempleScores(String gameState) {
        return new int[0]; // FIXME Task 18 & 23D
    }

    /**
     * Given a state string, calculates the "Garden" component of the score for each player.
     * <p>
     * Task 18 only considers the standard scoring rules, Task 23 considers the variant scoring.
     * Hint: Check the settings component of the state.
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
     *
     * @param gameState a state string.
     * @return An array containing the "Garden" component of the score for each player (ordered by ascending player ID).
     */
    public static int[] calculateGardenScores(String gameState) {
        return new int[0]; // FIXME Task 19 & 23E
    }


    /**
     * Given a state string, calculate the score for each player.
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
        return new int[0]; // FIXME Task 20 & 23F
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

    public static Stack getStack() {
        return stack;
    }

    public static Piece[] createPieceArray(int numberOfPlayers) {

        String currentPool = TILE_POOL;
        Piece[] pieceArray = new Piece[pieceCount];

        for (int i = 1; i < numberOfPlayers + 1; i++) {
            String splitPoint  = Integer.toString(i + 1) + ":";
            String[] splitPool = currentPool.split(splitPoint);
            currentPool = splitPool[0];
            for (int j = 0; j < (currentPool.length()/5); j++) {
                String subString = currentPool.substring((j*5), (j*5) + 5);
                String pieceId = subString.substring(0,2);
                int pieceIndex = Integer.parseInt(pieceId);

                pieceArray[pieceIndex] = new Piece(subString);
            }
            System.out.println(currentPool);
            currentPool = splitPool[1];
            System.out.println(currentPool);
        }

        return new Piece[0];
    }

    public static void main(String[] args) {
        createPieceArray(3);
    }
    public static Piece getPieceFromId(int pieceId) {
        return null;
    }

}
