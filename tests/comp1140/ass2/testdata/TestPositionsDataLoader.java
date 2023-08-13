package comp1140.ass2.testdata;

public class TestPositionsDataLoader extends DataLoader<TestPosition> {


    @Override
    protected String getFileName() {
        return "testPositions";
    }

    @Override
    protected TestPosition processLine(String line) {
        String[] parts = line.split(" ");
        return new TestPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), parts[2]);
    }
}
