package comp1140.ass2;

public class Player {

    // The players ID
    private final int id;
    private final Board board;
    private int stones;

    public boolean isAI;


    /**
     * Constructor for player form internal representation
     * @author u7683699
     *
     * @param id the player's id
     * @param board the player's board
     * @param stones the amount of stone the player has*/
    Player(int id, Board board, int stones, boolean ai) {
        this.id = id;
        this.board = board;
        this.stones = stones;
        this.isAI = ai;
    }

    /**
     * Constructor for player using string representation
     * @author u7683699
     *
     * @param playerString region of the gameString referring to this player*/
    Player(String playerString) {
        char idChar = playerString.charAt(1);
        this.id = Character.getNumericValue(idChar);

        String stones = playerString.substring(2, 4);
        this.stones = Integer.parseInt(stones);

        String movesMade = playerString.substring(4);
        this.board = new Board(movesMade);
        this.isAI = false;
    }

    /**
     * Copy constructor for the player
     * @author u7683699
     *
     * @param original the player that is being copied*/
    Player(Player original) {
        this.id = original.id;
        this.stones = original.stones;
        this.board = new Board(original.board);
        this.isAI = original.isAI;
    }

    /**
     * Applies a move to a board and/pays collects stones
     * @author u746615
     *
     * @param constructionSite the site where piece are being taken from
     * @param move that is being applied*/
    public void applyMove(ConstructionSite constructionSite, Move move) {
        int price = constructionSite.findPrice(move.getPiece());
        constructionSite.removePiece(move.getPiece());
        board.placePiece(move, false);
        int profit = board.collectStones();
        stones = stones - price + profit;
    }

    @Override
    public String toString() {
        return "Player " + id + " | Stones " + stones;
    }

    public Board getBoard() {
        return board;
    }

    public int getStones() {
        return stones;
    }
}
