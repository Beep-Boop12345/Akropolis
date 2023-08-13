package comp1140.ass2.testdata;

public class TileAtDataLoader extends DataLoader<Character> {
    @Override
    protected String getFileName() {
        return "tileAt";
    }

    @Override
    protected Character processLine(String line) {
        if (line.equals("null")) {
            return null;
        }
        if (line.length() != 1)
            throw new IllegalArgumentException("Must be character");
        return line.charAt(0);
    }
}
