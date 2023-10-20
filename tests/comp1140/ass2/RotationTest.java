package comp1140.ass2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RotationTest {

    @Test
    @DisplayName("Returns correct rotation for angles less than 360")
    public void testGetAngleValidAngles() {
        assertEquals(Rotation.DEG_0, Rotation.getAngle(0));
        assertEquals(Rotation.DEG_60, Rotation.getAngle(60));
        assertEquals(Rotation.DEG_120, Rotation.getAngle(120));
        assertEquals(Rotation.DEG_180, Rotation.getAngle(180));
        assertEquals(Rotation.DEG_240, Rotation.getAngle(240));
        assertEquals(Rotation.DEG_300, Rotation.getAngle(300));
    }

    @Test
    @DisplayName("Returns correct rotation for angles larger than 360")
    public void testGetAngleLargeAngles() {
        assertEquals(Rotation.DEG_60, Rotation.getAngle(420));
        assertEquals(Rotation.DEG_180, Rotation.getAngle(540));
        assertEquals(Rotation.DEG_0, Rotation.getAngle(720));
        assertEquals(Rotation.DEG_60, Rotation.getAngle(780));
        assertEquals(Rotation.DEG_120, Rotation.getAngle(840));
        assertEquals(Rotation.DEG_180, Rotation.getAngle(900));
        assertEquals(Rotation.DEG_240, Rotation.getAngle(960));
        assertEquals(Rotation.DEG_300, Rotation.getAngle(1020));
    }



    @Test
    @DisplayName("Add method correct works with basic angles")
    public void testAddValidRotations() {
        assertEquals(Rotation.DEG_120, Rotation.DEG_60.add(Rotation.DEG_60));
        assertEquals(Rotation.DEG_300, Rotation.DEG_60.add(Rotation.DEG_240));
        assertEquals(Rotation.DEG_0, Rotation.DEG_180.add(Rotation.DEG_180));
        assertEquals(Rotation.DEG_240, Rotation.DEG_60.add(Rotation.DEG_180));
    }

    @Test
    @DisplayName("Add method correctly returns angle when added with 0 angle")
    public void testAddWithZeroRotation() {
        assertEquals(Rotation.DEG_60, Rotation.DEG_60.add(Rotation.DEG_0));
        assertEquals(Rotation.DEG_180, Rotation.DEG_180.add(Rotation.DEG_0));
        assertEquals(Rotation.DEG_240, Rotation.DEG_240.add(Rotation.DEG_0));
    }

    @Test
    @DisplayName("Add method works correctly when angle added are equal")
    public void testAddWithEqualRotations() {
        assertEquals(Rotation.DEG_120, Rotation.DEG_60.add(Rotation.DEG_60));
        assertEquals(Rotation.DEG_0, Rotation.DEG_180.add(Rotation.DEG_180));
        assertEquals(Rotation.DEG_240, Rotation.DEG_300.add(Rotation.DEG_300));
    }


    /* Tests for toRad method using delta of 1e-10 to account for IEE-754 float point precision */
    @Test
    @DisplayName("Tests the toRad method with valid angles")
    public void testToRadValidAngles() {
        assertEquals(Math.toRadians(0), Rotation.DEG_0.toRad(), 1e-10);
        assertEquals(Math.toRadians(60), Rotation.DEG_60.toRad(), 1e-10);
        assertEquals(Math.toRadians(120), Rotation.DEG_120.toRad(), 1e-10);
        assertEquals(Math.toRadians(180), Rotation.DEG_180.toRad(), 1e-10);
        assertEquals(Math.toRadians(240), Rotation.DEG_240.toRad(), 1e-10);
        assertEquals(Math.toRadians(300), Rotation.DEG_300.toRad(), 1e-10);
    }

    @Test
    @DisplayName("Tests the toRad method with large angles")
    public void testToRadLargeAngles() {
        assertEquals(Math.toRadians(60), Rotation.DEG_60.add(Rotation.getAngle(360)).toRad(), 1e-10);
        assertEquals(Math.toRadians(180), Rotation.DEG_180.add(Rotation.getAngle(360)).toRad(), 1e-10);
    }

    @Test
    @DisplayName("Tests the toRad method with zero rotation")
    public void testToRadZeroRotation() {
        assertEquals(Math.toRadians(0), Rotation.DEG_0.toRad(), 1e-10);
        assertEquals(Math.toRadians(0), Rotation.getAngle(360).toRad(), 1e-10);
    }

    /* Tests for toString method */
    @Test
    @DisplayName("Tests the toString method with valid angles")
    public void testToStringValidAngles() {
        assertEquals("0", Rotation.DEG_0.toString());
        assertEquals("1", Rotation.DEG_60.toString());
        assertEquals("3", Rotation.DEG_180.toString());
    }
}
