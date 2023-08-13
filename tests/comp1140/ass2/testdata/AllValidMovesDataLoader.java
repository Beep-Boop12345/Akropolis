package comp1140.ass2.testdata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllValidMovesDataLoader extends DataLoader<Set<String>> {
    @Override
    protected String getFileName() {
        return "allValidMoves";
    }

    @Override
    protected Set<String> processLine(String line) {
        line = line.substring(1, line.length() - 1);
        if (line.length() > 0) {
            return new HashSet<>(List.of(line.split(", ")));
        } else {
            return new HashSet<>();
        }
    }
}
