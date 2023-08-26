package comp1140.ass2;

public enum GameState {
    ENDED,
    PLAYER1MOVE,
    PLAYER2MOVE,
    PLAYER3MOVE,
    PLAYER4MOVE,
    SETUP;

    GameState() {

    }
    /*updates GameState from setup or to ended
    * @Param bool has the game begun?
    * @Param bool has game ended?
    * @Return new GameState*/
    public GameState UpdateState (Boolean playing, Boolean finished) {

        return null;
    }

    /*Cycles player Turn
    * @Param current GameState
    * @Return GameState after cycling move*/
    public GameState cycleMove (GameState currentState) {

        return currentState;
    }

    public String toString() {
        return "Null";
    }
}
