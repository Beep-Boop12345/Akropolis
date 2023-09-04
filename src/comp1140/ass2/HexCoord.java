package comp1140.ass2;

public class HexCoord {

    // The horizontal and vertical positions as integers x and y
    private final int x;
    private final int y;


    /**
     * Constructor for instances of HexCoordinates
     * @param x the horizontal displacement
     * @param y the vertical displacement
     */

    public HexCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Creates a new Hex Coordinate from the resulting addition of two hex coordinates.
     * @param other HexCoordinate added
     * @return a new HexCoord which is the resulting addition of the two.
     */
    public HexCoord add(HexCoord other) {
        int sumX = this.x + other.x;
        int sumY = this.y + other.y;
        return new HexCoord(sumX,sumY);
    }

    /**
     *
     * @return all adjacent HexCoords that are within the board
     */
    public HexCoord[] getSurroundings(){ return null; } // Todo

    @Override
    public String toString() { return "(" + x +", " + y; }

    /**
     * Method to ensure equals compares objects of HexCoord correctly
     * @param o the other object of comparison
     * @return true if the object is identical to the given HexCoord
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HexCoord position = (HexCoord) o;
        return x == position.x && y == position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
