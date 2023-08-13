package comp1140.ass2.testdata;

import java.util.ArrayList;
import java.util.List;

public abstract class ScoreDataLoader extends DataLoader<List<Integer>> {
    @Override
    protected final List<Integer> processLine(String line) {
        line = line.substring(1, line.length() - 1);

        List<Integer> score = new ArrayList<>();
        for (String s : line.split(", ")) {
            score.add(Integer.parseInt(s));
        }
        return score;
    }
}
