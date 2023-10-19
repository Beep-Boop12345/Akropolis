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

    public Tile(Tile original) {
        this.districtType = original.districtType;
        this.isPlaza = original.isPlaza;
        this.height = original.height;
        this.isOccupied = original.isOccupied;
        this.piece = original.piece;
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
        char tileCharLower = Character.toLowerCase(tileChar);
        switch (tileCharLower) {
            case 'h':
                return District.HOUSES;
            case 'm':
                return District.MARKETS;
            case 'b':
                return District.BARRACKS;
            case 't':
                return District.TEMPLES;
            case 'g':
                return District.GARDENS;
            case 'q':
                return District.QUARRY;
            default:
                System.out.println("Missing Accurate District Type, Got: " + tileChar);
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

    public Boolean getPlaza() {
        return isPlaza;
    }


    /**
     * Getter method to retrieve stars from a tile (Useful for scores calculations Tasks 15-20, 23A-F)
     * @author u7330006
    */
    public int getStars(Tile tile) {
        // Only Plaza's have stars
        if (tile.isPlaza) {
            return switch (tile.districtType) {
                case HOUSES -> 1;
                case MARKETS, TEMPLES, BARRACKS -> 2;
                case GARDENS -> 3;
                // Return 0 if it doesn't match a district
                default -> 0;
            };
        } else {
            return 0;
        }
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
}
