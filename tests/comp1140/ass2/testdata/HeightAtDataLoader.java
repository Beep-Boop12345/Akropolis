package comp1140.ass2.testdata;

public class HeightAtDataLoader extends DataLoader<Integer> {
    @Override
    protected String getFileName() {
        return "heightAt";
    }

    @Override
    protected Integer processLine(String line) {
        return Integer.parseInt(line);
    }
}
