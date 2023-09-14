package comp1140.ass2;

import java.util.Arrays;
import java.util.Random;

public class Stack {

    private static int pieceCount;

    private static Piece[] currentPieces;

    public Stack (Piece[] pieces){
        pieceCount = pieces.length;
        currentPieces = pieces;
    }

    public Stack (String gamestate) {
        int[] pieceIDsConstructionSite = constructionSitePieces(gamestate);
        int[] pieceIDsActive = activePieces(gamestate);
        /*Combine the two arrays into 1*/
        int[] allPieceIDs = new int[pieceIDsActive.length + pieceIDsConstructionSite.length];
        if (pieceIDsConstructionSite.length > 0) {
            for (int i = 0; i < pieceIDsConstructionSite.length; i++) {
                allPieceIDs[i] = pieceIDsConstructionSite[i];
            }
        }
        if (pieceIDsActive.length > 0) {
            for (int i = 0; i < pieceIDsActive.length; i++) {
                allPieceIDs[i + pieceIDsConstructionSite.length] = pieceIDsActive[i];
            }
        }
        /*Find what pieces can be in the game*/
        int playerCount = Integer.parseInt(gamestate.substring(0,1));
        int idCap = 0;
        switch (playerCount) {
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
        pieceCount = idCap - allPieceIDs.length;
        currentPieces = new Piece[pieceCount];
        int index = 0;
        for (int i = 0; i < idCap; i++) {
            if (!in(allPieceIDs,i+1)) {
                String pieceIDString = String.valueOf(i+1);
                if (pieceIDString.length() == 1) {
                    pieceIDString = "0" + pieceIDString;
                }
                currentPieces[index] = new Piece(pieceIDString);
                index ++;
            }
        }
    }

    /*Produces int array for all the piece IDs of pieces in the constructionSite
     * @Param String gamestate
     * @Return int[] pieceIDs the IDs of the pieces in the constructions site*/
    private static int[] constructionSitePieces (String gamestate) {
        String shared = gamestate.split(";")[1];
        int[] pieceIDs = new int[(shared.length() -1)/2];
        for (int i = 0; i < pieceIDs.length; i++) {
            pieceIDs[i] = Integer.parseInt(shared.substring((i*2)+1,(i*2)+3));
        }
        return pieceIDs;
    }

    /*Finds the pieceID of all placed pieces from a gameState
    * @Param String gameState
    * @Return int[] for all the activePieceIDs*/
    private static int[] activePieces (String gameState) {
        int activePieceCount = 0;
        String[] gameStateAsArray = gameState.split(";");
        String[] playerStrings = new String[gameStateAsArray.length - 2];
        for (int i = 0; i < playerStrings.length; i ++) {
            playerStrings[i] = gameStateAsArray[i+2];
        }
        /*Sums works through all player strings to count how many active pieces there are*/
        for (String i: playerStrings) {
            activePieceCount += (i.length() - 4)/10;
        }
        int[] activePieceIDs = new int[activePieceCount];
        int index = 0;
        for (String i : playerStrings) {
            for (int j = 0; j < (i.length() - 4)/10; j++) {
                activePieceIDs[index] = Integer.parseInt(i.substring((j*10)+4,(j*10)+6));
                index ++;
            }
        }
        return activePieceIDs;
    }

    private static boolean in(int[] arr, int key) {
        for(int i:arr) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }


    /*Chooses a random tile to give to constructionSite*/
    public static Piece choose() {
        if (pieceCount == 0) {
            return null;
        }
        int pieceIndex = (int) Math.floor(Math.random()*(pieceCount));
        Piece output = currentPieces[pieceIndex];
        pieceCount = pieceCount - 1;
        Piece[] holdr = new Piece[pieceCount];
        for (int i = 0; i < pieceCount; i++) {
            if (i > pieceIndex - 1) {
                holdr[i] = currentPieces[i+1];
            } else {
                holdr[i] = currentPieces[i];
            }
        }
        currentPieces = holdr;
        return output;
    }

    public static int getPieceCount() {
        return pieceCount;
    }

    public static Piece[] getCurrentPieces() {
        return currentPieces;
    }

    @Override
    public String toString() {
        String output = "";
        for (Piece i: currentPieces) {
            output = output + ", " + i;
        }
        return  output;
    }
}
