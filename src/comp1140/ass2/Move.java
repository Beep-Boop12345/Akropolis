package comp1140.ass2;

public class Move {
    /*The Piece used*/
    private Piece piece;

    /*The player making the move (0 to p-1)*/
    //private int player;

    /*The position of the piece*/
    private Transform position;
    //private int cost;

    Move(Piece piece, Transform newTransform) {
        this.piece = piece;
        this.position = newTransform;
    }
    public Move(String moveString) {
        String pieceId = moveString.substring(0, 2);
        this.piece = new Piece (pieceId);
        String transformString = moveString.substring(2);
        this.position = new Transform(transformString);
    }

    public Transform getPosition () {
        return this.position;
    }

    public Piece getPiece() {
        return this.piece;
    }

    @Override
    public String toString() {
       return piece + position.toString();
    }
}
