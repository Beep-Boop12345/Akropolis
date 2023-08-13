package comp1140.ass2.testdata;

public class GameDataLoader extends DataLoader<String> {

    @Override
    protected String getFileName() {
        return "game";
    }

    @Override
    protected String processLine(String line) {
        return line;
    }
}
