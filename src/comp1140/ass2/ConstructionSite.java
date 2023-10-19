package comp1140.ass2;


public class ConstructionSite {

    public int size;

    public Piece[] currentPieces;

    /**
     * Constructor for construction site using internal representations
     * @author u7646615
     *
     * @param playerCount how many players are in the game
     * @param initialPieces the pieces in the construction site
     * */
    public ConstructionSite (int playerCount, Piece[] initialPieces) {
        this.size = playerCount + 2;
        this.currentPieces = new Piece[this.size];
        for (int i =0; i < initialPieces.length; i++) {
            this.currentPieces[i] = initialPieces[i];
        }
    }
    /**Constructor from string input
     * @author u7646615
     *
     * @param gameState string representing the gameState*/
    public ConstructionSite (String gameState) {
        Piece[] pieces = isolateConstructionSite(gameState);
        int playerCount = findPlayerCount(gameState);
        this.size = playerCount + 2;
        this.currentPieces = new Piece[this.size];
        for (int i =0; i < pieces.length; i++) {
            this.currentPieces[i] = pieces[i];
        }
    }
    /**
     * Copy constructor for construction site
     * @author u7683688
     * @param original the original construction site*/
    public ConstructionSite(ConstructionSite original) {
        this.size = original.size;
        this.currentPieces = new Piece[this.size];
        for (int i =0; i < original.currentPieces.length; i++) {
            if (original.currentPieces[i] != null) {
                this.currentPieces[i] = new Piece(original.currentPieces[i]);
            }
        }
    }

    /**
     * Finds the Pieces in the construction site from the gameState string
     * @author u7646615
     *
    * @param gameState string representation of the current gameState
    * @return Piece[] the pieces in the constructions site
     * */
    private static Piece[] isolateConstructionSite (String gameState) {
        String shared = gameState.split(";")[1];
        Piece[] initialPieces = new Piece[(shared.length() - 1)/2];
        for (int i  = 0; i < initialPieces.length; i ++) {
            initialPieces[i] = new Piece(shared.substring((i*2)+1,(i*2)+3));
        }
        return initialPieces;
    }

    /**
     * Extracts playerCount from gameState String
     * @author u7646615
     * @param gameState the state of the game in string form
     * @return int playerCount*/
    private static int findPlayerCount (String gameState) {
        return Integer.parseInt(gameState.substring(0,1));
    }

    /**
     * Resupplies the construction site
     * @author u7646615
     * <p>
     * random pieces are chosen from the stack, and removed from the stack
     * @param  stack the stack that pieces are taken from
     * */
    public void resupply (Stack stack) {
        if (!isEmpty()) {
            return;
        }
        /*order();*/
        for (int i = getCurrentPieceCount(); i < size; i++) {
            currentPieces[i] = addPiece(stack);
        }
    }

    /**
     * Evaluates whether refilling is needed
     * @author u7646615
     * @return true if only one or no piece in construction site*/
    private boolean isEmpty() {
        return getCurrentPieceCount() < 2;
    }

    /**
     * Returns the price of a piece if it is to be purchased
     * @author u7646615
     * @param purchase piece to be purchased
     * @return price of selection, -1 if not purchasable
     * */
    public int findPrice(Piece purchase) {
        //Last piece may not be purchased
        if (getCurrentPieceCount() < 2) {
            return -1;
        }
        for (int i = 0; i < size; i++) {
            if (purchase.equals(currentPieces[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes piece from the construction site
     * @author u7646615
     * <p>
     * preserves order of pieces and ensures all null instances held are at the end of the array
     *
     * @param piece to be removed*/
    public void removePiece(Piece piece) {
        /*this round about way of removing is to make sure all nulls remain at the end*/
        boolean hasReachedPieceToBeRemoved = false;
        Piece[] currentPiecesHldr = new Piece[size];
        System.arraycopy(currentPieces, 0, currentPiecesHldr, 0, size);
        for (int i = 0; i < size; i ++) {
            if (hasReachedPieceToBeRemoved) {
                currentPieces[i-1] = currentPiecesHldr[i];
            }
            if (currentPiecesHldr[i] == null) {
                currentPieces[i] = currentPiecesHldr[i];
            } else {
                if (currentPiecesHldr[i].equals(piece)) {
                    hasReachedPieceToBeRemoved = true;
                } else {
                    currentPieces[i] = currentPiecesHldr[i];
                }
            }
        }
        if (hasReachedPieceToBeRemoved) {
            currentPieces[size-1] = null;
        }
    }

    /**
     * returns piece from the stack
     *
     * @param stack the stack from which the pieces are taken
     * @return the piece selected from the stack*/
    private static Piece addPiece(Stack stack) {
        Piece output = stack.choose();
        return output;
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

    public Piece[] getCurrentPieces() {
        return currentPieces;
    }

    public int getCurrentPieceCount() {
        var currentPieceCount = 0;
        for (var piece : currentPieces) {
            if (piece != null) {
                currentPieceCount++;
            }
        }
        return currentPieceCount;
    }
}
