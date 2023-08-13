package comp1140.ass2;

import comp1140.ass2.testdata.AllValidMovesDataLoader;
import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.InvalidMovesDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(value = 15000, unit = TimeUnit.MILLISECONDS)
public class IsMoveValidTest implements TestPlayerCount {
    private static final int MOVE_STEP_SIZE = 50;
    private static final int INVALID_MOVE_STEP_SIZE = 20;
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final AllValidMovesDataLoader validMovesDataLoader = new AllValidMovesDataLoader();
    private final InvalidMovesDataLoader invalidMovesDataLoader = new InvalidMovesDataLoader();

    private final VariantMutator mutator = new VariantMutator();

    private static void testTrue(String state, String move) {
        boolean result = Akropolis.isMoveValid(state, move);
        Assertions.assertTrue(result, "Move: " + move + " should be valid for state: " + state);
    }

    private static void testFalse(String state, String move) {
        boolean result = Akropolis.isMoveValid(state, move);
        Assertions.assertFalse(result, "Move: " + move + " should not be valid for state: " + state);
    }

    private void testValidMoves(List<String> game, List<Set<String>> valid) {
        for (int i = 0; i < valid.size(); i++) {
            List<String> moves = new ArrayList<>(valid.get(i));

            int start = (i % MOVE_STEP_SIZE);
            if (start > moves.size() && moves.size() > 0) {
                start = start % moves.size();
            }
            String state = game.get(2 * i);
            for (int j = start; j < moves.size(); j += MOVE_STEP_SIZE) {
                testTrue(mutator.mutateVariant(state), moves.get(j));
                mutator.updateMutator();
            }
        }
    }

    private void testInvalidMoves(List<String> game, List<Set<String>> invalid) {
        for (int i = 0; i < invalid.size(); i++) {
            List<String> moves = new ArrayList<>(invalid.get(i));

            int start = (i % INVALID_MOVE_STEP_SIZE);
            if (start > moves.size() && moves.size() > 0) {
                start = start % moves.size();
            }
            String state = game.get(2 * i);
            for (int j = start; j < moves.size(); j += INVALID_MOVE_STEP_SIZE) {
                testFalse(mutator.mutateVariant(state), moves.get(j));
                mutator.updateMutator();
            }
        }
    }

    private void testGame(List<String> game, List<Set<String>> valid, List<Set<String>> invalid) {
        testValidMoves(game, valid);
        testInvalidMoves(game, invalid);
    }

    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);
        List<List<Set<String>>> valid = validMovesDataLoader.fetchForPlayers(playerCount);
        List<List<Set<String>>> invalid = invalidMovesDataLoader.fetchForPlayers(playerCount);

        for (int game = 0; game < games.size(); game++) {
            testGame(games.get(game), valid.get(game), invalid.get(game));
        }
    }
}
