package comp1140.ass2;

import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.TestPosition;
import comp1140.ass2.testdata.TestPositionsDataLoader;
import comp1140.ass2.testdata.TileAtDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Timeout(value = 8000, unit = TimeUnit.MILLISECONDS)
public class TileAtTest implements TestPlayerCount {
    private static final int STEP_SIZE = 70;
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final TestPositionsDataLoader testPositionsDataLoader = new TestPositionsDataLoader();
    private final TileAtDataLoader tileAtDataLoader = new TileAtDataLoader();

    private final VariantMutator mutator = new VariantMutator();

    private static void test(Character expected, String state, int playerId, String position) {
        if (expected == null)
            testNull(state, playerId, position);
        else
            testCharacter(expected, state, playerId, position);
    }

    private static void testNull(String state, int playerId, String position) {
        Character result = Akropolis.tileAt(state, playerId, position);
        Assertions.assertNull(result, "Tile for player " + playerId + " at position " + position
                + " should be empty (null) but got " + result + " for state " + state);
    }

    private static void testCharacter(Character expected, String state, int playerId, String position) {
        Character result = Akropolis.tileAt(state, playerId, position);
        Assertions.assertEquals(expected, result, "Tile for player " + playerId + " at position " + position
                + " should be " + expected + " but got " + result + " for state " + state);
    }

    private void testGame(List<String> game, List<TestPosition> testPositions, List<Character> solutions) {
        for (int i = 0; i < testPositions.size(); i += STEP_SIZE) {
            TestPosition testPosition = testPositions.get(i);
            String state = game.get(2 * testPosition.stateIndex());
            state = mutator.mutateVariant(state);
            test(solutions.get(i), state, testPosition.playerId(), testPosition.position());
            mutator.updateMutator();
        }
    }

    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);
        List<List<TestPosition>> testPositions = testPositionsDataLoader.fetchForPlayers(playerCount);
        List<List<Character>> solutions = tileAtDataLoader.fetchForPlayers(playerCount);
        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), testPositions.get(game), solutions.get(game));
        }
    }
}
