package comp1140.ass2;

import comp1140.ass2.testdata.AvailableTilesDataLoader;
import comp1140.ass2.testdata.GameDataLoader;
import comp1140.ass2.testdata.PreResupplyDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Timeout;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Timeout(value = 120000, unit = TimeUnit.MILLISECONDS)
public class ResupplyConstructionSiteTest implements TestPlayerCount {
    private final static int SIMULATIONS = 1000;
    private final GameDataLoader gameDataLoader = new GameDataLoader();
    private final PreResupplyDataLoader preResupplyDataLoader = new PreResupplyDataLoader();
    private final AvailableTilesDataLoader availableTilesDataLoader = new AvailableTilesDataLoader();

    private final VariantMutator mutator = new VariantMutator();

    private void testNoChange(String game) {
        String result = Akropolis.resupplyConstructionSite(game);
        Assertions.assertEquals(game, result, "Expected the Construction Site to not be resupplied, though the " +
                "string has changed.\nInput: " + game + "\nResult: " + result);
    }

    private void testGame(List<String> game) {
        for (int i = 0; i < game.size(); i += 2) {
            String state = game.get(i);
            state = mutator.mutateVariant(state);
            testNoChange(state);
            mutator.updateMutator();
        }
    }

    private static void testConstantInformation(String originalState, String resuppliedState) {
        String[] originalParts = originalState.split(";");
        String[] resuppliedParts = resuppliedState.split(";");

        Assertions.assertEquals(originalParts.length, resuppliedParts.length, "State after resupplying " +
                "Construction Site has a different number of semicolons.\nOriginal: " + originalState + "\n" +
                "After Resupplying: " + resuppliedState);

        Assertions.assertEquals(originalParts[0], resuppliedParts[0], "Settings string incorrectly changed" +
                "after resupplying.\nOriginal: " + originalState + "\nAfter Resupplying: " + resuppliedState);

        Assertions.assertEquals(originalParts[1].substring(0, 3), resuppliedParts[1].substring(0, 3), "Turn and " +
                "last Construction Site tile must be the same after resupplying.\nOriginal: " + originalState + "\n" +
                "After Resupplying: " + resuppliedState);

        for (int i = 2; i < originalParts.length; i++) {
            Assertions.assertEquals(originalParts[i], resuppliedParts[i], "Player string incorrectly changed" +
                    "after resupplying.\nOriginal: " + originalState + "\nAfter Resupplying: " + resuppliedState);
        }
    }

    private void testRandomDistribution(List<Map<String, Integer>> positionTileCount, double expectedTimes, String state) {
        double maxDifference = 0.0;

        for (int i = 0; i < positionTileCount.size(); i++) {
            Map<String, Integer> tileCount = positionTileCount.get(i);

            double chiSquare = 0.0;
            int r = 0;

            for (String tile : tileCount.keySet()) {
                Integer occurrences = tileCount.get(tile);
                double difference = Math.abs(expectedTimes - occurrences);
                if (difference > maxDifference) {
                    maxDifference = difference;
                }
                chiSquare += difference * difference;
                r++;
            }
            chiSquare /= expectedTimes;
            Assertions.assertTrue(Math.abs(chiSquare - r) <= 5 * Math.sqrt(r), "Distribution of tiles " +
                    "does not follow a discrete uniform random distribution. The following displays the frequency" +
                    " of tile IDs in each position of the Construction Site. The expected frequency is " +
                    expectedTimes + ":\n" + positionTileCount + "\nInput State: " + state);
        }
    }

    private void testResupply(String state, Set<String> availableTiles, int playerCount) {
        int constructionSiteSize = playerCount + 2;
        // A list of maps to count the number of tiles in each position after a number of simulations
        List<Map<String, Integer>> positionTileCount = new ArrayList<>();
        for (int i = 0; i < constructionSiteSize - 1; i++) { // the first position is never refilled
            Map<String, Integer> tileCount = new HashMap<>();
            for (String tile : availableTiles) {
                tileCount.put(tile, 0);
            }
            positionTileCount.add(tileCount);
        }
        double expectedTimes = ((double) SIMULATIONS / positionTileCount.get(0).keySet().size());

        // Randomly distribute the tiles into the Construction Site over a number of simulations
        for (int sim = 0; sim < SIMULATIONS; sim++) {
            String result = Akropolis.resupplyConstructionSite(state);
            // Test nothing else changed
            testConstantInformation(state, result);
            // Test new tiles
            String newTiles = result.split(";")[1].substring(3);
            Assertions.assertEquals(newTiles.length(), 2 * (constructionSiteSize - 1), "Inserted tiles into the" +
                    "Construction Site is of incorrect length for state: " + state + "\nExpected Length Ignoring Turn " +
                    "and Last Tile: "
                    + 2 * (constructionSiteSize - 1) + "\nGot: " + newTiles.length() + "\nResulting State: " + result);

            for (int i = 0; i < constructionSiteSize - 1; i++) {
                // Count the tiles in each position
                String tile = newTiles.substring(2 * i, 2 * (i + 1));
                Assertions.assertTrue(positionTileCount.get(i).containsKey(tile), "tile: " + tile + " is not" +
                        " a valid tile to be inserted into the Construction Site for state: " + state);
                positionTileCount.get(i).put(tile, positionTileCount.get(i).get(tile) + 1);
            }
        }

        testRandomDistribution(positionTileCount, expectedTimes, state);
    }

    private void testGameResupply(List<String> gamePreResupply, List<Set<String>> availableTiles, int playerCount) {
        for (int i = 0; i < gamePreResupply.size(); i++) {
            testResupply(gamePreResupply.get(i), availableTiles.get(i), playerCount);
        }
    }

    @Override
    public void testPlayerCount(int playerCount) {
        List<List<String>> games = gameDataLoader.fetchForPlayers(playerCount);

        for (List<String> game : games) {
            testGame(game);
        }

        List<List<String>> preResupply = preResupplyDataLoader.fetchForPlayers(playerCount);
        List<List<Set<String>>> availableTiles = availableTilesDataLoader.fetchForPlayers(playerCount);

        testGameResupply(preResupply.get(0), availableTiles.get(0), playerCount);
    }
}
