package comp1140.ass2;

import java.util.Set;
public class HexCoord {

    // The horizontal and vertical positions as integers x and y.
    private final int x;
    private final int y;

    /**
     * Constructor for instances of HexCoordinates
     * @param x the horizontal displacement
     * @param y the vertical displacement
     * @author u7330006
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
     * @u7330006
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
        int offset = Math.abs(x) % 2;
        HexCoord[] neigbours = new HexCoord[6];
        neigbours[0] = new HexCoord(x,y+1);
        neigbours[1] = new HexCoord(x+1,y+offset);
        neigbours[2] = new HexCoord(x+1,y-1+offset);
        neigbours[3] = new HexCoord(x,y-1);
        neigbours[4] = new HexCoord(x-1,y-1+offset);
        neigbours[5] = new HexCoord(x-1,y+offset);
        return neigbours;
    }

    public Set<HexCoord> getSurroundingsSet(){
        HexCoord[] neighbours = getSurroundings();
        Set<HexCoord> output = null;
        for (HexCoord pos : neighbours) {
            output.add(pos);
        }
        return output;
    }

    @Override
    public String toString() {
        String yString = String.valueOf(Math.abs(y));
        /*adds "0" to front if single digit int*/
        if (yString.length() == 1) {
            yString = "0" + yString;
        }
        String xString = String.valueOf(Math.abs(x));
        /*adds "0" to front if single digit int*/
        if (xString.length() == 1) {
            xString = "0" + xString;
        }
        String dirY;
        String dirX;
        if (y > 0) {
            dirY = "N";
        } else {
            dirY = "S";
        }
        if (x < 0) {
            dirX = "W";
        } else {
            dirX = "E";
        }
        return dirY + yString + dirX + xString;}

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
