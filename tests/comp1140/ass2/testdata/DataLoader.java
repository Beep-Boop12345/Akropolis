package comp1140.ass2.testdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DataLoader<T> {
    private static final Map<Integer, Integer> gamesForPlayers = Map.of(2, 12, 3, 9, 4, 6);
    private final Map<Integer, List<List<T>>> GAMES = new HashMap<>();

    protected DataLoader() {
        for (int playerCount = 2; playerCount <= 4; playerCount++) {
            int numberOfGames = gamesForPlayers.get(playerCount);
            GAMES.put(playerCount, new ArrayList<>(numberOfGames));
            for (int game = 0; game < numberOfGames; game++) {
                GAMES.get(playerCount).add(loadData(playerCount, game));
            }
        }
    }

    public List<List<T>> fetchForPlayers(int playerCount) {
        return GAMES.get(playerCount);
    }

    public List<List<T>> fetchAll() {
        List<List<T>> all = new ArrayList<>();
        for (int playerCount = 2; playerCount <= 4; playerCount++) {
            all.addAll(GAMES.get(playerCount));
        }
        return all;
    }

    protected abstract String getFileName();

    protected abstract T processLine(String line);

    private List<T> loadData(int playerCount, int gameNumber) {
        return loadResourceStream(findResource(playerCount, gameNumber, getFileName()));
    }

    private List<T> loadResourceStream(InputStream stream) {
        try {
            BufferedReader reader;
            List<T> data = new ArrayList<>();

            reader = new BufferedReader(new InputStreamReader(stream));

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(processLine(line.strip()));
            }

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream findResource(int playerCount, int gameNumber, String filename) {
        return this.getClass().getResourceAsStream("data/" + gameFolder(playerCount, gameNumber) + filename + ".txt");
    }

    private static String gameFolder(int playerCount, int gameNumber) {
        return "game_p" + playerCount + "_" + String.format("%02d", gameNumber) + "/";
    }
}
