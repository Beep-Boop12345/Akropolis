package comp1140.ass2;

public class Stack {

    private int pieceCount;

    private Piece[] currentPieces;

    /**
     * Constructor using internal representation
     * @author u7646615
     *
     * @param pieces the piece that the stack contains*/
    public Stack (Piece[] pieces){
        pieceCount = pieces.length;
        currentPieces = pieces;
    }

    /**
     * Copy constructor for the Stack
     * @author u7683699
     *
     * @param original Stack*/
    public Stack(Stack original) {
        pieceCount = original.pieceCount;
        currentPieces = new Piece[pieceCount];
        for (int i = 0; i < pieceCount; i++) {
            currentPieces[i] = new Piece(original.currentPieces[i]);
        }
    }


    /**
     * Constructor from string representation
     * @author u7646615
     *
     * @param gameState the string representing the gameState*/
    public Stack (String gameState) {
        int[] pieceIDsConstructionSite = constructionSitePieces(gameState);
        int[] pieceIDsActive = activePieces(gameState);
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
        int playerCount = Integer.parseInt(gameState.substring(0,1));
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

    /**
     * Produces array for all the piece IDs of pieces in the constructionSite from game string
     * @author u7646615
     *
     * @param gameState
     * @return int[] of the IDs of the pieces in the constructions site*/
    private static int[] constructionSitePieces (String gameState) {
        String shared = gameState.split(";")[1];
        int[] pieceIDs = new int[(shared.length() -1)/2];
        for (int i = 0; i < pieceIDs.length; i++) {
            pieceIDs[i] = Integer.parseInt(shared.substring((i*2)+1,(i*2)+3));
        }
        return pieceIDs;
    }

    /**Produces array for all the piece IDs of pieces in active play from game string
     * @author u7646615
     *
     * @param gameState
     * @return int[] of the IDs of the active pieces*/
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

    /**
     * Helper method, return true when an integer is in an integer array
     * @author u7646615
     *
     * @param arr array to be searched
     * @param key the integer that will be compared to values in array
     * @return true if piece is in the stack
     * */
    private static boolean in(int[] arr, int key) {
        for(int i:arr) {
            if (i == key) {
                return true;
            }
        }
        return false;
    }


    /**Chooses a random tile to give to constructionSite.
     * @author u7646615
     * <p>
     * Will remove the piece from the Stack
     **/
    public Piece choose() {
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

    public int getPieceCount() {
        return pieceCount;
    }

    public Piece[] getCurrentPieces() {
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
