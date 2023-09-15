package comp1140.ass2;

public class HexCoord {

    // The horizontal and vertical positions as integers x and y.
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

    public HexCoord(String position) {
        int absX = Integer.parseInt(position.substring(4,6));
        int absY = Integer.parseInt(position.substring(1,3));
        if (position.charAt(0) == 'N') {
            this.y = absY;
        } else {
            this.y = -1*absY;
        }
        if (position.charAt(3) == 'E') {
            this.x = absX;
        } else {
            this.x = -1*absX;
        }
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
     * @return all adjacent HexCoords. Clockwise starting from North.
     */
    public HexCoord[] getSurroundings(){
        /*Offset to account for hexagonal grid, rotations work differently for odd columns*/
        int offset = 0;
        if (Math.abs(x) % 2 == 1) {
            offset = 1;
        }
        HexCoord[] neigbours = new HexCoord[6];
        neigbours[0] = new HexCoord(x,y+1);
        neigbours[1] = new HexCoord(x+1,y+offset);
        neigbours[2] = new HexCoord(x+1,y-1+offset);
        neigbours[3] = new HexCoord(x,y-1);
        neigbours[4] = new HexCoord(x-1,y-1+offset);
        neigbours[5] = new HexCoord(x-1,y+offset);
        return neigbours;
    }

    @Override
    public String toString() { return "Position: " + "(" + x +", " + y + ")"; }

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
