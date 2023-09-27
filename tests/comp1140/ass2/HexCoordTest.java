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
        var testHex1 = new HexCoord(0,0);
        var testList = testHex1.getSurroundings();
        var actualList = new HexCoord[]{
                new HexCoord(0,1),
                new HexCoord(1,0),
                new HexCoord(1,-1),
                new HexCoord(0,-1),
                new HexCoord(-1,-1),
                new HexCoord(-1,0)
        };

        for (int i = 0; i < testList.length; i++) {
            assertEquals(testList[i], actualList[i]);
        }

        var testHex2 = new HexCoord(3,4);
        var testList2 = testHex2.getSurroundings();
        var actualList2 = new HexCoord[]{
                new HexCoord(3,5),
                new HexCoord(4,5),
                new HexCoord(4,4),
                new HexCoord(3,3),
                new HexCoord(2,4),
                new HexCoord(2,5)
        };

        for (int i = 0; i < testList2.length; i++) {
            assertEquals(testList2[i], actualList2[i]);
        }

        var testHex3 = new HexCoord(-2,-5);
        var testList3 = testHex3.getSurroundings();
        var actualList3 = new HexCoord[]{
                new HexCoord(-2,-4),
                new HexCoord(-1,-5),
                new HexCoord(-1,-6),
                new HexCoord(-2,-6),
                new HexCoord(-3,-6),
                new HexCoord(-3,-5)
        };

        for (int i = 0; i < testList3.length; i++) {
            assertEquals(testList3[i], actualList3[i]);
        }


    }
}