package comp1140.ass2;

/**
 * This enum is responsible for representation degrees of rotation that is applied when I move is made.
 * It holds many methods that allow for addition of rotations and ensuring that rotations cycle 360 degrees.
 * @author u7330006
 */
public enum Rotation {
    DEG_0(0),
    DEG_60(60),
    DEG_120(120),
    DEG_180(180),
    DEG_240(240),
    DEG_300(300);

    // Value of the angle rotation as an integer
    public final int value;

    /**
     * Constructor: create a new instance of Rotation class
     * Rotation is clockwise
     * @param angle of rotation
     */
    Rotation(int angle){
        this.value = angle;
    }



    /**
     * Convert an integer value back into a Rotation
     *
     * @param angle in degrees
     * @return rotation
     */
    public static Rotation getAngle(int angle) {
        angle = angle % 360;
        for (Rotation direction : Rotation.values()) {
            if (direction.value == angle) {
                return direction;
            }
        }
        return null;
    }



    /**
     * Adds rotations together
     * @param other rotation
     * @return the total rotation once added together
     */
    public Rotation add (Rotation other) {
        return getAngle(this.value + other.value);
    }

    /**
     * Converts the rotation to radians
     * @return rotation value in radians
     */
    public double toRad() {
        return Math.toRadians(value);
    }

    /**
     * Checks if this Rotation is equal to another Rotation object.
     *
     * @param other the Rotation object to compare.
     * @return true if the angle values are equal, false otherwise.
     */
    public boolean isEqual(Rotation other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return String.valueOf(value / 60);
    }
}


