package comp1140.ass2;

public class Move {
    /*The Piece used*/
    private Piece play;

    /*The player making the move (0 to p-1)*/
    private int player;

    /*The position of the piece*/
    private Transform position;
    private int cost;

    public Move(int player, String moveString) {

    }

    public Transform getPosition () {
        return this.position;
    }

}
