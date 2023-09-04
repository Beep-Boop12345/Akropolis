package comp1140.ass2;

public class Transform {
    private HexCoord pos;

    private Rotation rot;

    Transform(String transformString) {

    }

    /*Checks if it is being compared with the same Transform
    * @Param Object
    * @Return true if it is a transform with same position and rotation*/
    private Boolean isEqual(Object object) {

        return null;
    }

    /*Adds a transform to this transform (component-wise)
    * @Param transform to be added*/
    private void add(Transform transform) {

    }

    public HexCoord getPos() {
        return pos;
    }

    public Rotation getRot() {
        return rot;
    }
}
