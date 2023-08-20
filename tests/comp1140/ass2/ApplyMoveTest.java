package comp1140.ass2;

import comp1140.ass2.testdata.AvailableTilesDataLoader;
import comp1140.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
public class ApplyMoveTest implements TestPlayerCount {
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final AvailableTilesDataLoader availableTilesDataLoader = new AvailableTilesDataLoader();
    private final VariantMutator mutator = new VariantMutator();

    private int availableTilesIndex = 0;

    public static boolean testSharedString(String expected, String actual, Set<String> availableTiles, String previousState, String move) {
        // Returns true if a resupply occurred, false otherwise

        Set<String> availableTilesCopy = new HashSet<>(availableTiles);

        // Checks whether two shared strings are identical taking into account when randomness is introduced
        String[] expectedParts = expected.split(";");
        String expectedShared = expectedParts[1];

        String[] actualParts = actual.split(";");
        String actualShared = actualParts[1];

        Assertions.assertEquals(expectedShared.length(), actualShared.length(), "Shared string should be of " +
                "identical length, but are not.\nExpected: " + expectedShared + "\nGot: " + actualShared + "\nInput state: " + previousState + "\nInput Move" + move);
        Assertions.assertEquals(expectedShared.charAt(0), actualShared.charAt(0), "Expected shared string player" +
                " turn to be identical.\nExpected: " + expectedShared + "\nGot: " + actualShared + "\nInput state: " + previousState + "\nInput Move" + move);

        int playerCount = Integer.parseInt(expectedParts[0].substring(0, 1));

        // Check if the resupplying was triggered
        if (expectedShared.length() - 1 == (playerCount + 2) * 2) {
            // The Construction Site is of maximum size - resupplying was triggered.

            // The first tile in the Construction Site should always be identical still
            Assertions.assertEquals(expectedShared.substring(1, 3), actualShared.substring(1, 3), "Expected first" +
                    " tile in Construction Site of shared string to be identical.\nExpected: " + expectedShared +
                    "\nGot: " + actualShared + "\nInput state: " + previousState + "\nInput Move" + move);

            for (int i = 3; i < actualShared.length(); i += 2) {
                String tile = actualShared.substring(i, i + 2);
                // Remove returns true if the element was in the set. If this is ever false, it means that either
                // there were two identical tiles in the string, or an otherwise invalid tile has been resupplied
                Assertions.assertTrue(availableTilesCopy.remove(tile), "Returned String contains an unexpected" +
                        "tile " + tile + " at position " + i + " in the shared string. Is this a duplicate tile, or is" +
                        "it otherwise invalid?\nFull Result: " + actual + "\nInput state: " + previousState + "\nInput Move" + move);
            }
            return true;
        }
        // The Construction Site is not of maximum size, and has thus not been refilled. Check for exact equality.
        Assertions.assertEquals(expectedShared, actualShared, "Shared String must be identical when the " +
                "Construction Site was not refilled.\nExpected: " + expected + "\nGot: " + actual + "\nInput state: " + previousState + "\nInput Move" + move);
        return false;
    }

    private void testGameState(String expected, String actual, List<Set<String>> availableTiles, String previousState, String move) {
        String[] expectedParts = expected.split(";");
        String[] actualParts = actual.split(";");

        // Test length
        Assertions.assertEquals(expectedParts.length, actualParts.length, "Game states have a differing number" +
                "of semicolons.\nExpected: " + expected + "\nGot: " + actual + "\nInput state: " + previousState + "\nInput Move" + move);

        // Test settings
        Assertions.assertEquals(expectedParts[0], actualParts[0], "Settings strings are different." +
                "\nExpected: " + expected + "\nGot: " + actual + "\nInput state: " + previousState + "\nInput Move" + move);

        // Test shared
        Set<String> available = new HashSet<>();
        if (availableTilesIndex < 11) { // Resupplying only happens 11 times for the 11 stacks of tiles (0-10)
            available = availableTiles.get(availableTilesIndex);
        }
        boolean resupplied = testSharedString(expected, actual, available, previousState, move);
        if (resupplied) {
            availableTilesIndex++;
        }
        // Test players
        for (int i = 2; i < expectedParts.length; i++) {
            Assertions.assertEquals(expectedParts[i], actualParts[i], "Player " + (i - 2) + " is not as expected" +
                    "\nExpected: " + expected + "\nGot: " + actual + "\nInput state: " + previousState + "\nInput Move" + move);
        }
    }


    private void testMove(String previousState, String move, String newState, List<Set<String>> availableTiles) {
        previousState = mutator.mutateVariant(previousState);
        newState = mutator.mutateVariant(newState);

        String result = Akropolis.applyMove(previousState, move);
        Assertions.assertNotNull(result, "Got null for state: " + result);
        testGameState(newState, result, availableTiles, previousState, move);
        mutator.updateMutator();
    }

    private void testGame(List<String> game, List<Set<String>> availableTiles) {
        availableTilesIndex = 0;
        for (int i = 2; i < game.size(); i += 2) {
            testMove(game.get(i - 2), game.get(i - 1), game.get(i), availableTiles);
        }
    }

    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);
        List<List<Set<String>>> availableTiles = availableTilesDataLoader.fetchForPlayers(playerCount);

        for (int i = 0; i < games.size(); i++) {
            testGame(games.get(i), availableTiles.get(i));
        }
    }
}
