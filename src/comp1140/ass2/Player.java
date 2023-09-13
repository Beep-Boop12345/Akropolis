package comp1140.ass2;

public class Player {

    private int id;
    private Board board;
    private int stones = 0;

    Player(int id, Board board, int stones) {
        this.id = id;
        this.board = board;
        this.stones = stones;
    }

    // Takes in only a Player string representation and constructs a board based on the moves made.
    Player(String playerString) {
        char idChar = playerString.charAt(1);
        this.id = Character.getNumericValue(idChar);

        String stones = playerString.substring(2, 4);
        this.stones = Integer.parseInt(stones);

        String movesMade = playerString.substring(4);
        this.board = new Board(this.id, movesMade);
    }

    // To string method that displays player id and current stone count
    @Override
    public String toString() {
        return "Player " + id + " | Stones " + stones;
    }

    // Used to test Constructor
    public static void main(String[] args) {
        Player newPlayer1 = new Player("P00119S00E02R2");
        Player newPlayer2 = new Player("P10057S02E01R4");
        Player newPlayer3 = new Player("P20341S01E03R5");
        Player newPlayer4 = new Player("P304");
        System.out.println(newPlayer1);
        System.out.println(newPlayer2);
        System.out.println(newPlayer3);
        System.out.println(newPlayer4);

    }

    public Board getBoard() {
        return board;
    }
}
