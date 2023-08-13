package comp1140.ass2;

import org.junit.jupiter.api.Test;

public interface TestPlayerCount {
    void testPlayerCount(int playerCount);


    @Test
    default void testTwoPlayerGames() {
        testPlayerCount(2);
    }


    @Test
    default void testThreePlayerGames() {
        testPlayerCount(3);
    }


    @Test
    default void testFourPlayerGames() {
        testPlayerCount(4);
    }


}
