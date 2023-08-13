package comp1140.ass2;

import comp1140.ass2.testdata.DataLoader;
import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.ScoreDataLoader;
import comp1140.ass2.testdata.VariantDataLoader;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.List;

public abstract class CalculateScoresTest implements TestPlayerCount {
    private final DataLoader<String> gameLoader = isVariant() ? new VariantDataLoader() : new GameDataLoader();
    private final ScoreDataLoader scoreDataLoader = fetchScoreLoader();

    protected abstract ScoreDataLoader fetchScoreLoader();

    protected abstract int[] fetchScore(String state);

    protected abstract boolean isVariant();

    private void test(int[] expected, String state) {
        int[] solution = fetchScore(state);
        Assertions.assertArrayEquals(expected, solution, "failed on input: " + state + "\nExpected: " + Arrays.toString(expected) + "\nGot: " + Arrays.toString(solution));
    }

    private void testGame(List<String> game, List<List<Integer>> solutions) {
        for (int i = 0; i < solutions.size(); i++) {
            List<Integer> solution = solutions.get(i);

            int[] expected = new int[solution.size()];
            for (int j = 0; j < solution.size(); j++) {
                expected[j] = solution.get(j);
            }
            test(expected, game.get((isVariant() ? 1 : 2) * i));
        }
    }

    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameLoader.fetchForPlayers(playerCount);
        List<List<List<Integer>>> solutions = scoreDataLoader.fetchForPlayers(playerCount);
        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), solutions.get(game));
        }
    }
}
