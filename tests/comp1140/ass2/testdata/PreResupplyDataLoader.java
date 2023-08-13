package comp1140.ass2.testdata;

public class PreResupplyDataLoader extends DataLoader<String> {
    @Override
    protected String getFileName() {
        return "preResupply";
    }

    @Override
    protected String processLine(String line) {
        return line;
    }
}
