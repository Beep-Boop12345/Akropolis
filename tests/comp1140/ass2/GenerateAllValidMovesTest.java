package comp1140.ass2;

import comp1140.ass2.testdata.AllValidMovesDataLoader;
import comp1140.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(value = 3000, unit = TimeUnit.MILLISECONDS)
public class GenerateAllValidMovesTest implements TestPlayerCount {
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final AllValidMovesDataLoader loader = new AllValidMovesDataLoader();
    private final VariantMutator mutator = new VariantMutator();

    private static void test(Set<String> expected, String state) {
        Set<String> actual = Akropolis.generateAllValidMoves(state);
        List<String> expectedList = new ArrayList<>(expected);
        List<String> actualList = new ArrayList<>(actual);
        if (!expected.equals(actual)) {
            Collections.sort(expectedList);
            Collections.sort(actualList);
        }
        Assertions.assertEquals(expected, actual, "Move sets are not equal for state: " + state + "\n"
                + "Expected (Sorted): " + expectedList + "\nGot (Sorted):      " + actualList);
    }

    private void testGame(List<String> game, List<Set<String>> solutions) {
        for (int i = 0; i < solutions.size(); i++) {
            String state = game.get(2 * i);
            state = mutator.mutateVariant(state);
            test(solutions.get(i), state);
            mutator.updateMutator();
        }
    }

    @Override
    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);
        List<List<Set<String>>> solutions = loader.fetchForPlayers(playerCount);
        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), solutions.get(game));
        }
    }
}
