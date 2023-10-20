package comp1140.ass2;

/**
 * Holds a postition and rotation of a hexcoord to represent a hexcoord movement
 * @author u7646615
 * @author u7330006
 */
public class Transform {
    private HexCoord pos;

    private Rotation rot;


    public Transform(HexCoord translation, Rotation angle) {
        this.pos = translation;
        this.rot = angle;
    }
    /**
     * Translates a moveString into a transform object
     * ignores the pieceID (current tile transform is applied to)
     */
    public Transform(String transformString) {
        // Split the moveString into parts using "R" as the delimiter
        String[] parts = transformString.split("R");

        // Extract the position and rotation parts
        String positionPart = parts[0];
        String rotationPart = parts[1];
        this.pos = new HexCoord(positionPart);
        // Parse the rotation part
        int rotationIndex = Integer.parseInt(rotationPart);

        // Create the rotation
        this.rot = Rotation.values()[rotationIndex];
    }

    /*Checks if it is being compared with the same Transform
    * @Param Object
    * @Return true if it is a transform with same position and rotation*/
    private Boolean isEqual(Object object) {

        return null;
    }

    /*Adds a transform to this transform (component-wise)
    * @Param transform to be added*/
    public void add(Transform transform) {
        pos = pos.add(transform.getPos());
        rot = rot.add(transform.getRot());
    }

    public HexCoord getPos() {
        return pos;
    }

    public Rotation getRot() {
        return rot;
    }

    @Override
    public String toString() {
        return pos + "R" + rot; }

}
