package comp1140.ass2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameState {
    ENDED(0),
    PLAYER1MOVE(1),
    PLAYER2MOVE(2),
    PLAYER3MOVE(3),
    PLAYER4MOVE(4),
    SETUP(-1);

    public final int turn;

    // There are 2 to 4 inclusive possible players
    public final int numPlayers;

    GameState(int turn) {
        this.turn = turn;
        this.numPlayers = 2;
    }

    /**
     * Constructor for new instance of GameState that parses the stateString
     * @param stateString
     * @return current gameState
     */
    GameState(String stateString) {
        String[] statements = stateString.split(";");

        // Parse the shared string to find whose turn it is
        String shared = statements[1];
        int turn = Integer.parseInt(shared.substring(0, 1));
        this.turn = turn+1;

        // Parse the player string to find the number of players
        String player = statements[2];
        this.numPlayers = Integer.parseInt(player.substring(0, 1));

    }


    // Method to update the game state
    public GameState updateState(boolean playing, boolean finished) {
        if (finished) {
            return ENDED;
        } else if (playing) {
            return cycleMove();
        } else {
            return SETUP;
        }
    }



    // Method to cycle player turn
    public GameState cycleMove() {
        int nextPlayer = (turn % numPlayers) + 1;

        switch (nextPlayer) {
            case 1:
                return PLAYER1MOVE;
            case 2:
                return PLAYER2MOVE;
            case 3:
                return PLAYER3MOVE;
            case 4:
                return PLAYER4MOVE;
            default:
                return this;
        }
    }


    @Override
    public String toString() {
        return name(); // Returns the enum name as a string
    }
}

