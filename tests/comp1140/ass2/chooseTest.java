package comp1140.ass2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class chooseTest {
    @RepeatedTest(10)
    @DisplayName("Returns null when Stack is empty")
    public void testNullWhenEmpty() {
        Piece[] state = {};
        Stack testStack = new Stack(state);
        Piece result = testStack.choose();
        Assertions.assertEquals(result,null,"should have returned null but returned " + result);
    }

    @Test
    @DisplayName("Ensures that size of Stack is decreases by one")
    public void testLengthDecreased() {
        int size = 2;
        while (size < 30) {
            Piece[] state = new Piece[size];
            for (int i = 10; i < 10 + size; i ++) {
                state[i - 10] = new Piece(String.valueOf(i));
            }
            Stack testStack = new Stack(state);
            int original = testStack.getPieceCount();
            Piece bin = testStack.choose();
            int result = testStack.getPieceCount();
            Assertions.assertEquals(original - 1, result,
                    "Length did not decrease by one after calling choose");
            size ++;
        }
    }

    @Test
    @DisplayName("Ensures selected Piece no longer in the Stack")
    public void testNoLongerInStack() {
        int size = 2;
        while (size < 30) {
            Piece[] state = new Piece[size];
            for (int i = 10; i < 10 + size; i ++) {
                state[i - 10] = new Piece(String.valueOf(i));
            }
            Stack testStack = new Stack(state);
            Piece chosen = testStack.choose();
            boolean result = in(testStack.getCurrentPieces(), chosen);
            Assertions.assertFalse(result,
                    "Length did not decrease by one after calling choose");
            size ++;
        }
    }


    private static boolean in(Piece[] arr, Piece key) {
        for(Piece i:arr) {
            if (i.equals(key)) {
                return true;
            }
        }
        return false;
    }
}
