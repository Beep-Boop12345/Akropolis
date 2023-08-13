package comp1140.ass2.testdata;

public class VariantDataLoader extends DataLoader<String> {

    @Override
    protected String getFileName() {
        return "variant";
    }

    @Override
    protected String processLine(String line) {
        return line;
    }
}
