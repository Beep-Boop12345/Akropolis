package comp1140.ass2;

public enum GameState {
    ENDED,
    PLAYER1MOVE,
    PLAYER2MOVE,
    PLAYER3MOVE,
    PLAYER4MOVE,
    SETUP;

    public GameState () {

    }
    /*updates GameState from setup or to ended
    * @Param bool has the game begun?
    * @Param bool has game ended?
    * @Return new GameState*/
    public GameState UpdateState (bool playing, bool finished) {

    }

    /*Cycles player Turn
    * @Param current GameState
    * @Return GameState after cycling move*/
    public GameState cycleMove (gameState) {

    }

    @Overide
    public String toString() {

    }
}
