package comp1140.ass2.testdata;

public class IsGameOverDataLoader extends DataLoader<Boolean> {
    @Override
    protected String getFileName() {
        return "isGameOver";
    }

    @Override
    protected Boolean processLine(String line) {
        return Boolean.parseBoolean(line);
    }
}
