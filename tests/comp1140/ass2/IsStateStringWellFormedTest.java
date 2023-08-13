package comp1140.ass2;

import comp1140.ass2.testdata.GameDataLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Timeout(value = 5000, unit = TimeUnit.MILLISECONDS)
public class IsStateStringWellFormedTest {
    private static final GameDataLoader gameDataLoader = new GameDataLoader();
    private final List<List<String>> allGames = gameDataLoader.fetchAll();

    private final VariantMutator mutator = new VariantMutator();

    private static void testTrue(String input) {
        boolean result = Akropolis.isStateStringWellFormed(input);
        Assertions.assertTrue(result, "The following input is a well-formed state but returns false: " + input);
    }

    private static void testFalse(String input) {
        boolean result = Akropolis.isStateStringWellFormed(input);
        Assertions.assertFalse(result, "The following input is not a well-formed state but returns true: " + input);
    }

    private static List<List<String>> fetchOneGameOfEachPlayerCount() {
        List<List<String>> oneOfEach = new LinkedList<>();
        for (int i = 2; i <= 4; i++) {
            oneOfEach.add(gameDataLoader.fetchForPlayers(i).get(0));
        }
        return oneOfEach;
    }

    @Test
    public void testAllGameStates() {
        // Guard against case where true returned by default for everything
        testFalse("");
        for (List<String> game : allGames) {
            for (int line = 0; line < game.size(); line += 2) {
                String state = game.get(line);
                state = mutator.mutateVariant(state);
                testTrue(state);
                mutator.updateMutator();
            }
        }
    }

    @Test
    public void testAllMoves() {
        // Guard against case where false returned by default for everything
        testTrue(allGames.get(0).get(0));
        for (List<String> game : allGames) {
            for (int line = 1; line < game.size(); line += 2) {
                testFalse(game.get(line));
            }
        }
    }

    @Test
    public void testMissingCharacters() {
        // Guard against case where false returned by default for everything
        List<List<String>> games = fetchOneGameOfEachPlayerCount();
        testTrue(games.get(0).get(0));
        for (List<String> game : games) {
            for (int line = 0; line < game.size(); line += 2) {
                for (int i = 0; i < game.get(line).length(); i++) {
                    String missing = game.get(line).substring(0, i) + game.get(line).substring(i + 1);
                    testFalse(missing);
                    if (i > 1) {
                        testFalse(game.get(line).charAt(i) + missing);
                    }
                    if (i < game.get(line).length() - 1) {
                        testFalse(missing + game.get(line).charAt(i));
                    }
                }
            }
        }
    }

    @Test
    public void testWrongCharacters() {
        // Guard against case where false returned by default for everything
        List<List<String>> games = fetchOneGameOfEachPlayerCount();
        testTrue(games.get(0).get(0));
        for (List<String> game : games) {
            for (int line = 0; line < game.size(); line += 2) {
                for (int i = 0; i < game.get(line).length(); i++) {
                    char character = game.get(line).charAt(i);
                    if (!Character.isDigit(character)) {
                        testFalse(game.get(line).substring(0, i) + (char) (character + 1) + game.get(line).substring(i + 1));
                        testFalse(game.get(line).substring(0, i) + (char) (character - 1) + game.get(line).substring(i + 1));
                    }
                    if (game.get(line).startsWith("S00", i)) {
                        testFalse(game.get(line).substring(0, i) + "N" + game.get(line).substring(i + 1));
                    }
                    if (game.get(line).startsWith("E00", i)) {
                        testFalse(game.get(line).substring(0, i) + "W" + game.get(line).substring(i + 1));
                    }
                }
            }
        }
    }

    @Test
    public void testReplacedDigit() {
        // Guard against case where false returned by default for everything
        List<List<String>> games = fetchOneGameOfEachPlayerCount();
        testTrue(games.get(0).get(0));
        for (List<String> game : games) {
            for (int line = 0; line < game.size(); line += 2) {
                for (int i = 0; i < game.get(line).length(); i++) {
                    char character = game.get(line).charAt(i);
                    if (Character.isDigit(character)) {
                        testFalse(game.get(line).substring(0, i) + '-' + game.get(line).substring(i + 1));
                        testFalse(game.get(line).substring(0, i) + (char) ('A' + ((line + i) % 26)) + game.get(line).substring(i + 1));
                        testFalse(game.get(line).substring(0, i) + (char) ('a' + ((line + i) % 26)) + game.get(line).substring(i + 1));
                    }
                }
            }
        }
    }

    @Test
    public void testPlayableForPlayerCount() {
        // Guard against case where false returned by default for everything
        testTrue(allGames.get(0).get(0));
        int bound = 38;
        for (int playerCount = 2; playerCount < 4; playerCount++) {
            List<String> game = gameDataLoader.fetchForPlayers(playerCount).get(0);

            for (int line = 0; line < game.size(); line += 2) {
                for (int piece = bound; piece < 62; piece++) {
                    // Test in Construction Site
                    String[] parts = game.get(line).split(";");
                    for (int i = 1; i < parts[1].length(); i += 2) {
                        String newConstructionSite = parts[1].substring(0, i) + piece + parts[1].substring(i + 2);
                        String testState = mergeReplacingIndex(parts, 1, newConstructionSite);
                        testFalse(testState);
                    }

                    // Test in Player
                    for (int p = 2; p < parts.length; p++) {
                        String player = parts[p];
                        for (int i = 4; i < player.length(); i += 10) {
                            String newPlayer = player.substring(0, i) + piece + player.substring(i + 2);
                            String testState = mergeReplacingIndex(parts, p, newPlayer);
                            testFalse(testState);
                        }
                    }
                }
            }

            bound += 12;
        }
    }

    @Test
    public void testDuplicatePieces() {
        // Guard against case where false returned by default for everything
        List<List<String>> games = fetchOneGameOfEachPlayerCount();
        testTrue(games.get(0).get(0));
        for (List<String> game : games) {
            for (int line = 0; line < game.size(); line += 2) {
                String[] parts = game.get(line).split(";");

                // Construction Site Duplication
                for (int i = 1; i < parts[1].length(); i += 2) {
                    String piece = parts[1].substring(i, i + 2);

                    // Construction Site Duplicate in Construction Site
                    for (int j = i + 2; j < parts[1].length(); j += 2) {
                        String newConstructionSite = parts[1].substring(0, i + 2) + parts[1].substring(i + 2, j) + piece + parts[1].substring(j + 2);
                        String testState = mergeReplacingIndex(parts, 1, newConstructionSite);
                        testFalse(testState);
                    }

                    // Construction Site Duplicate in Player
                    for (int p = 2; p < parts.length; p++) { // For each player
                        String player = parts[p];
                        for (int j = 4; j < player.length(); j += 10) { // For each move
                            String newPlayer = player.substring(0, j) + piece + player.substring(j + 2);

                            String testState = mergeReplacingIndex(parts, p, newPlayer);
                            testFalse(testState);
                        }
                    }
                }

                // Player Duplication
                for (int p1 = 2; p1 < parts.length; p1++) {
                    String player = parts[p1];
                    for (int i = 4; i < player.length(); i += 10) {
                        String piece = player.substring(i, i + 2);

                        // Duplicate in same player
                        for (int j = i + 10; j < player.length(); j += 10) {
                            String newPlayer = player.substring(0, j) + piece + player.substring(j + 2);

                            String testState = mergeReplacingIndex(parts, p1, newPlayer);
                            testFalse(testState);
                        }

                        // Duplicate tile in different player
                        for (int p2 = p1 + 1; p2 < parts.length; p2++) {
                            String otherPlayer = parts[p2];
                            for (int j = 4; j < otherPlayer.length(); j += 10) {
                                String newPlayer = otherPlayer.substring(0, j) + piece + otherPlayer.substring(j + 2);

                                String testState = mergeReplacingIndex(parts, p2, newPlayer);
                                testFalse(testState);
                            }
                        }
                    }
                }
            }
        }
    }

    public static String mergeReplacingIndex(String[] parts, int replaceIndex, String replace) {
        StringBuilder merged = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i == replaceIndex)
                merged.append(replace).append(";");
            else
                merged.append(parts[i]).append(";");
        }
        return merged.toString();
    }
}
