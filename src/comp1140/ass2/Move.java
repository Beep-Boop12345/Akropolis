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

    /**
     * Gets the neighbours of the piece.@u7646615
     *
     * @return HexCoord[] with the piece neighbours*/
    public HexCoord[] getPieceNeighbours(){
        int x = position.getPos().getX();
        int y = position.getPos().getY();
        HexCoord[] neighbours = new HexCoord[9];
        int offset = Math.abs(x) % 2;
        switch (position.getRot()) {
            case DEG_0:
                neighbours[0] = new HexCoord(x+1,y-1+offset);
                neighbours[1] = new HexCoord(x,y-1);
                neighbours[2] = new HexCoord(x-1,y-1+offset);
                neighbours[3] = new HexCoord(x-1,y+offset);
                neighbours[4] = new HexCoord(x-1,y+1+offset);
                neighbours[5] = new HexCoord(x,y+2);
                neighbours[6] = new HexCoord(x+1,y+1+offset);
                neighbours[7] = new HexCoord(x+2,y);
                neighbours[8] = new HexCoord(x+2,y+1);
                break;
            case DEG_60:
                neighbours[0] = new HexCoord(x,y+1);
                neighbours[1] = new HexCoord(x,y-1);
                neighbours[2] = new HexCoord(x-1,y-1+offset);
                neighbours[3] = new HexCoord(x-1,y+offset);
                neighbours[4] = new HexCoord(x+2,y);
                neighbours[5] = new HexCoord(x+2,y+1);
                neighbours[6] = new HexCoord(x+2,y-1);
                neighbours[7] = new HexCoord(x+1,y+1+offset);
                neighbours[8] = new HexCoord(x+1,y-2+offset);
                break;
            case DEG_120:
                neighbours[0] = new HexCoord(x+1,y-2+offset);
                neighbours[1] = new HexCoord(x,y-2);
                neighbours[2] = new HexCoord(x-1,y-2+offset);
                neighbours[3] = new HexCoord(x-1,y-1+offset);
                neighbours[4] = new HexCoord(x-1,y+offset);
                neighbours[5] = new HexCoord(x,y+1);
                neighbours[6] = new HexCoord(x+1,y+offset);
                neighbours[7] = new HexCoord(x+2,y-1);
                neighbours[8] = new HexCoord(x+2,y);
                break;
            case DEG_180:
                neighbours[0] = new HexCoord(x,y+1);
                neighbours[1] = new HexCoord(x+1,y+offset);
                neighbours[2] = new HexCoord(x+1,y-1+offset);
                neighbours[3] = new HexCoord(x-1,y+offset);
                neighbours[4] = new HexCoord(x+1,y-2+offset);
                neighbours[5] = new HexCoord(x,y-2);
                neighbours[6] = new HexCoord(x-1,y-2+offset);
                neighbours[7] = new HexCoord(x-2,y);
                neighbours[8] = new HexCoord(x-2,y-1);
                break;
            case DEG_240:
                neighbours[0] = new HexCoord(x,y+1);
                neighbours[1] = new HexCoord(x+1,y+offset);
                neighbours[2] = new HexCoord(x+1,y-1+offset);
                neighbours[3] = new HexCoord(x,y-1);
                neighbours[4] = new HexCoord(x-1,y+1+offset);
                neighbours[5] = new HexCoord(x-1,y-2+offset);
                neighbours[6] = new HexCoord(x-2,y);
                neighbours[7] = new HexCoord(x-2,y+1);
                neighbours[8] = new HexCoord(x-2,y-1);
                break;
            case DEG_300:
                neighbours[0] = new HexCoord(x+1,y+offset);
                neighbours[1] = new HexCoord(x+1,y-1+offset);
                neighbours[2] = new HexCoord(x,y-1);
                neighbours[3] = new HexCoord(x-1,y-1+offset);
                neighbours[4] = new HexCoord(x+1,y+1+offset);
                neighbours[5] = new HexCoord(x,y+2);
                neighbours[6] = new HexCoord(x-1,y+1+offset);
                neighbours[7] = new HexCoord(x-2,y);
                neighbours[8] = new HexCoord(x-2,y+1);
        }
        return neighbours;
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
