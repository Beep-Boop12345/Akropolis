package comp1140.ass2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexCoordTest {

    @Test
    void add() {
        assertEquals(new HexCoord(3,4), (new HexCoord(1,1)).add(new HexCoord(2,3)));
        assertEquals(new HexCoord(3,4), (new HexCoord(2,3)).add(new HexCoord(1,1)));
        assertEquals(new HexCoord(-5,10), (new HexCoord(1,1)).add(new HexCoord(-6,9)));
        assertEquals(new HexCoord(-5,10), (new HexCoord(-6,9)).add(new HexCoord(1,1)));
        assertEquals(new HexCoord(0,0), (new HexCoord(-10,5)).add(new HexCoord(10,-5)));
        assertEquals(new HexCoord(0,0), (new HexCoord(5,-10)).add(new HexCoord(-5,10)));
        assertEquals(new HexCoord(5,103), (new HexCoord(5,103)).add(new HexCoord(0,0)));
        assertEquals(new HexCoord(5,103), (new HexCoord(0,0)).add(new HexCoord(5,103)));
    }

    @Test
    void getSurroundings() {
    }
}