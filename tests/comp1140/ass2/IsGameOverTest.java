package comp1140.ass2;

import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.IsGameOverDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
public class IsGameOverTest implements TestPlayerCount {
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final IsGameOverDataLoader isGameOverLoader = new IsGameOverDataLoader();

    private final VariantMutator mutator = new VariantMutator();

    private static void test(boolean expected, String input) {
        if (expected) {
            testTrue(input);
        } else {
            testFalse(input);
        }
    }

    private static void testTrue(String input) {
        boolean result = Akropolis.isGameOver(input);
        Assertions.assertTrue(result, "Game should be over for state: " + input);
    }

    private static void testFalse(String input) {
        boolean result = Akropolis.isGameOver(input);
        Assertions.assertFalse(result, "Game should not be over for state: " + input);
    }

    private void testGame(List<String> game, List<Boolean> solutions) {
        for (int i = 0; i < solutions.size(); i++) {
            String state = game.get(2 * i);
            state = mutator.mutateVariant(state);
            test(solutions.get(i), state);
            mutator.updateMutator();
        }
    }

    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);
        List<List<Boolean>> solutions = isGameOverLoader.fetchForPlayers(playerCount);
        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), solutions.get(game));
        }
    }
}
