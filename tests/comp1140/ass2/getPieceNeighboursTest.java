package comp1140.ass2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class getPieceNeighboursTest {

    @RepeatedTest(10)
    @DisplayName("does not contain a position that is not contained in a hexcoords neighbour")
    public void testNoIncorrectNeighbours() {
        int x = (((int) Math.random()*10) - 5);
        int y = (((int) Math.random()*10) - 5);
        int rotInt = (((int) Math.random()*6) * 60);
        Rotation rot = Rotation.getAngle(rotInt);
        Move move = new Move(new Piece("01"),new Transform(new HexCoord(x,y),rot));
        HexCoord[] moveNeighbours = move.getPieceNeighbours();
        HexCoord[] tilePositions =  findTilePosition(move);
        HexCoord[] tileNeighbours = new HexCoord[18];
        boolean contradiction = false;
        for (HexCoord i : moveNeighbours) {
            if (!(has(i,tilePositions[0].getSurroundings()) ||
                    has(i,tilePositions[1].getSurroundings()) ||
                    has(i,tilePositions[2].getSurroundings()))) {
                contradiction = true;
            }
        }

        Assertions.assertEquals(contradiction,false,"should not contain any position not within " +
                "the neighbours of its tiles but did");
    }

    //Checks if a hexcoord is equal to something in an array
    private boolean has(HexCoord point, HexCoord[] arr) {
        for (HexCoord i : arr) {
            if (point.equals(i)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Copy of private method from board with the same name*/
    private HexCoord[] findTilePosition (Move moveToMake) {
        HexCoord[] tilePosition = new HexCoord[3];
        HexCoord basePos = moveToMake.getTransform().getPos();
        tilePosition[0] = basePos;
        /*Offset to account for hexagonal grid, odd and even columns are shifted from each other*/
        int offset = 0;
        if (Math.abs(basePos.getX()) % 2 == 1) {
            offset = 1;
        }
        switch (moveToMake.getTransform().getRot()) {
            case DEG_0:
                tilePosition[1] = basePos.add(new HexCoord(0,1));
                tilePosition[2] = basePos.add(new HexCoord(1,offset));
                break;
            case DEG_60:
                tilePosition[1] = basePos.add(new HexCoord(1,offset));
                tilePosition[2] = basePos.add(new HexCoord(1,-1+offset));
                break;
            case DEG_120:
                tilePosition[1] = basePos.add(new HexCoord(1,-1+offset));
                tilePosition[2] = basePos.add(new HexCoord(0,-1));
                break;
            case DEG_180:
                tilePosition[1] = basePos.add(new HexCoord(0,-1));
                tilePosition[2] = basePos.add(new HexCoord(-1,-1+offset));
                break;
            case DEG_240:
                tilePosition[1] = basePos.add(new HexCoord(-1,-1+offset));
                tilePosition[2] = basePos.add(new HexCoord(-1,offset));
                break;
            case DEG_300:
                tilePosition[1] = basePos.add(new HexCoord(-1,offset));
                tilePosition[2] = basePos.add(new HexCoord(0,1));
                break;
        }
        return tilePosition;
    }
}
