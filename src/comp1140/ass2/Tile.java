package comp1140.ass2;

public class Tile {
    private District districtType;

    private Boolean isPlaza;

    private int height;

    private Boolean isOccupied;

    /*The piece that it came from*/
    private int piece;

    public Tile(District districtType, Boolean isPlaza, int pieceID) {
        this.districtType = districtType;
        this.isPlaza = isPlaza;
        this.height = 0;
        this.isOccupied = false;
        this.piece = pieceID;
    }

    //Character Constructor for a tile
    public Tile(char tileChar, int pieceID) {

        this.districtType = characterToDistrict(tileChar);
        this.isPlaza = Character.isUpperCase(tileChar);
        this.height = 0;
        this.isOccupied = false;
        this.piece = pieceID;

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

    public District getDistrictType() {
        return districtType;
    }

    public int getPiece() {
        return piece;
    }

    @Override
    public String toString() {
        return "Tile( " + districtType + " | isPlaza: " + isPlaza + " | height: " + height + " | isOccupied: " + isOccupied + ")";
    }

    public char toStringRep() {
        switch (districtType) {
            case HOUSES:
                if (isPlaza) {
                    return 'H';
                } else {
                    return 'h';
                }
            case MARKETS:
                if (isPlaza) {
                    return 'M';
                } else {
                    return 'm';
                }
            case GARDENS:
                if (isPlaza) {
                    return 'G';
                } else {
                    return 'g';
                }
            case BARRACKS:
                if (isPlaza) {
                    return 'B';
                } else {
                    return 'b';
                }
            case TEMPLES:
                if (isPlaza) {
                    return 'T';
                } else {
                    return 't';
                }
            case QUARRY:
                if (isPlaza) {
                    return 'Q';
                } else {
                    return 'q';
                }
        }
        return '0';
    }

    //Constructor testing code
    public static void main(String[] args) {
        System.out.println(new Tile('h',0));
        System.out.println(new Tile('H',0));
        System.out.println(new Tile('m',0));
        System.out.println(new Tile('M',0));
        System.out.println(new Tile('b',0));
        System.out.println(new Tile('B',0));
        System.out.println(new Tile('t',0));
        System.out.println(new Tile('T',0));
        System.out.println(new Tile('g',0));
        System.out.println(new Tile('G',0));
        System.out.println(new Tile('q',0));

    }
}
