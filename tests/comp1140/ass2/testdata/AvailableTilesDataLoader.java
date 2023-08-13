package comp1140.ass2.testdata;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AvailableTilesDataLoader extends DataLoader<Set<String>> {
    @Override
    protected String getFileName() {
        return "availableTiles";
    }

    @Override
    protected final Set<String> processLine(String line) {
        line = line.substring(1, line.length() - 1);

        return new HashSet<>(Arrays.asList(line.split(", ")));
    }
}
