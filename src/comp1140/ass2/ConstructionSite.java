package comp1140.ass2;

import java.lang.Math;

public class ConstructionSite {

    public static int size;

    public static Piece[] currentPieces;

    public ConstructionSite (int playercount, Piece[] initialPieces) {
        this.size = playercount + 2;
        this.currentPieces = new Piece[this.size];
        for (int i =0; i < initialPieces.length; i++) {
            this.currentPieces[i] = initialPieces[i];
        }
    }
    /*Constructor from string input*/
    public ConstructionSite (String gamestate) {
        Piece[] pieceIDs = isolateConstructionSite(gamestate);
        int playerCount = findPlayerCount(gamestate);
        this.size = playerCount + 2;
        this.currentPieces = new Piece[this.size];
        for (int i =0; i < pieceIDs.length; i++) {
            this.currentPieces[i] = pieceIDs[i];
        }
    }

    /*Finds the Pieces in the construction site from the gameState string
    * @Param String gamestate
    * @Return Piece[] the pieces in the constructions site*/
    private static Piece[] isolateConstructionSite (String gameState) {
        String shared = gameState.split(";")[1];
        Piece[] initialPieces = new Piece[(shared.length() - 1)/2];
        for (int i  = 0; i < initialPieces.length; i++) {
            initialPieces[i] = new Piece(shared.substring(i+1,i+3));
        }
        return initialPieces;
    }

    /*Extracts playerCount from gameState String
    * @Param String gameState
    * @Return int playerCount*/
    private static int findPlayerCount (String gameState) {
        return Integer.parseInt(gameState.substring(0,1));
    }

    public static void resupply () {
        if (!isEmpty()) {
            return;
        }
        order();
        for (int i = countPieces(); i < size; i++) {
            currentPieces[i] = addPiece();
        }
    }

    /*Evaluates whether refilling is needed
    * @Return True if only one piece in construction site*/
    private static boolean isEmpty() {
        if (countPieces() < 2) {
            return true;
        }
        return false;
    }

    private static int countPieces() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (currentPieces[i] != null) {
                count += 1;
            }
        }
        return count;
    }

    /*Order the pieces so that all null instances are at the highest indexes of the currentPiece array*/
    private static void order() {
        int index = 0;
        while (index < size) {
            if (currentPieces[index] == null) {
                int i = 0;
                while (index + i < size) {
                    if (currentPieces[index + i] != null) {
                        Piece holdr = currentPieces[index + i];
                        currentPieces[index + i] = null;
                        currentPieces[index] = holdr;
                        break;
                    }
                }
            }
            index ++;
        }
    }

    /*Removes an amount of Pieces from the construction site from the left
    * @Param nuber of pieces to be removed*/
    private static void removePieces(int numberOfPieces) {

    }

    /*Adds pieces to the construction Site
    * @Param pieces to be added*/
    private static Piece addPiece() {
        return Stack.choose();
    }
    @Override
    public String toString() {
        String output = "";
        for (Piece piece : currentPieces) {
            if (piece != null) {
                output += piece.toString();
            }
        }
        return output;
    }
}
