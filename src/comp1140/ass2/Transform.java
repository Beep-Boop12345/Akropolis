package comp1140.ass2;


public class Transform {
    private final HexCoord pos;

    private final Rotation rot;


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
        String positionPart = parts[0]; // Skip the ID part
        String rotationPart = parts[1];
        this.pos = new HexCoord(positionPart);
        // Parse the rotation part
        int rotationIndex = Integer.parseInt(rotationPart);

        // Create the rotation
        this.rot = Rotation.values()[rotationIndex];

        /*// Parse the positionPart to get the HexCoord
        int xOffset = 0;
        int yOffset = 0;

        if (positionPart.contains("N")) {
            int nIndex = positionPart.indexOf("N") + 1;
            yOffset = Integer.parseInt(positionPart.substring(nIndex, nIndex + 2));
        } else if (positionPart.contains("S")) {
            int sIndex = positionPart.indexOf("S") + 1;
            yOffset = -Integer.parseInt(positionPart.substring(sIndex, sIndex + 2));
        }

        if (positionPart.contains("E")) {
            int eIndex = positionPart.indexOf("E") + 1;
            xOffset = Integer.parseInt(positionPart.substring(eIndex, eIndex + 2));
        } else if (positionPart.contains("W")) {
            int wIndex = positionPart.indexOf("W") + 1;
            xOffset = -Integer.parseInt(positionPart.substring(wIndex, wIndex + 2));
        }

        // Create the HexCoord
        this.pos = new HexCoord(xOffset, yOffset);

        // Parse the rotation part
        int rotationIndex = Integer.parseInt(rotationPart);

        // Create the rotation
        this.rot = Rotation.values()[rotationIndex];*/
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

    @Override
    public String toString() { return pos.toString() + " " + rot.toString(); }


    // Main method for testing
    public static void main(String[] args) {
        // Test move strings
        String moveString1 = "02S01E03R5";
        String moveString2 = "01N02W01R2";

        // Create Transform objects and print them using toString
        Transform transform1 = new Transform(moveString1);
        Transform transform2 = new Transform(moveString2);

        System.out.println("Move String 1: " + moveString1);
        System.out.println(transform1);

        System.out.println("\nMove String 2: " + moveString2);
        System.out.println(transform2);
    }

}
