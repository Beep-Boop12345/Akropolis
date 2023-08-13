package comp1140.ass2;

import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.HeightAtDataLoader;
import comp1140.ass2.testdata.TestPosition;
import comp1140.ass2.testdata.TestPositionsDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
public class HeightAtTest implements TestPlayerCount {
    private static final int STEP_SIZE = 70;
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final TestPositionsDataLoader testPositionsDataLoader = new TestPositionsDataLoader();
    private final HeightAtDataLoader heightAtDataLoader = new HeightAtDataLoader();

    private final VariantMutator mutator = new VariantMutator();

    private static void test(int expected, String state, int playerId, String position) {
        int result = Akropolis.heightAt(state, playerId, position);
        Assertions.assertEquals(expected, result, "Height for player " + playerId + " at position " + position
                + " should be " + expected + " but got " + result + " for state " + state);
    }


    private void testGame(List<String> game, List<TestPosition> testPositions, List<Integer> solutions) {
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
        List<List<Integer>> solutions = heightAtDataLoader.fetchForPlayers(playerCount);
        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), testPositions.get(game), solutions.get(game));
        }
    }
}
