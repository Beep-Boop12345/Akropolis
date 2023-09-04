package comp1140.ass2;

public class Tile {
    private District districtType;

    private Boolean isPlaza;

    private int height;

    private Boolean isOccupied;

    public Tile(District districtType, Boolean isPlaza) {
        this.districtType = districtType;
        this.isPlaza = isPlaza;
        this.height = 0;
        this.isOccupied = false;
    }

    //Character Constructor for a tile
    public Tile(char tileChar) {

        this.districtType = characterToDistrict(tileChar);
        this.isPlaza = Character.isUpperCase(tileChar);
        this.height = 0;
        this.isOccupied = false;

    };

    // Turns the character representation of a tile to its district type
    private static District characterToDistrict(char tileChar) {
        if ((tileChar == 'h') || (tileChar == 'H')) {
            return District.HOUSES;
        } else if ((tileChar == 'm') || (tileChar == 'M')) {
            return District.MARKETS;
        } else if ((tileChar == 'b') || (tileChar == 'B')) {
            return District.BARRACKS;
        } else if ((tileChar == 't') || (tileChar == 'T')) {
            return District.TEMPLES;
        } else if ((tileChar == 'g') || (tileChar == 'G')) {
            return District.GARDENS;
        } else if (tileChar == 'q') {
            return District.QUARRY;
        } else {
            System.out.println("Missing Accurate District Type");
            return District.HOUSES;
        }
    }

    public int getHeight (){
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Tile( " + districtType + " | isPlaza: " + isPlaza + " | height: " + height + " | isOccupied: " + isOccupied + ")";
    }

    //Constructor testing code
    public static void main(String[] args) {
        System.out.println(new Tile('h'));
        System.out.println(new Tile('H'));
        System.out.println(new Tile('m'));
        System.out.println(new Tile('M'));
        System.out.println(new Tile('b'));
        System.out.println(new Tile('B'));
        System.out.println(new Tile('t'));
        System.out.println(new Tile('T'));
        System.out.println(new Tile('g'));
        System.out.println(new Tile('G'));
        System.out.println(new Tile('q'));

    }
}
