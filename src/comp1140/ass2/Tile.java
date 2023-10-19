package comp1140.ass2;

public class Tile {
    private District districtType;

    private Boolean isPlaza;

    private int height;

    private Boolean isOccupied;

    /*The piece ID of piece that it came from*/
    private int piece;

    /**
     * Constructor from internal representation
     * @author u7683699
     *
     * @param districtType type of district the tile is
     * @param isPlaza true if the tile is a plaza
     * @param pieceID the pieceID of the piece it is from*/
    public Tile(District districtType, Boolean isPlaza, int pieceID) {
        this.districtType = districtType;
        this.isPlaza = isPlaza;
        this.height = 0;
        this.isOccupied = false;
        this.piece = pieceID;
    }

    /**
     * Copy constructor for Tile
     * @author u7683699
     *
     * @param original the tile that it is copying
     * */
    public Tile(Tile original) {
        this.districtType = original.districtType;
        this.isPlaza = original.isPlaza;
        this.height = original.height;
        this.isOccupied = original.isOccupied;
        this.piece = original.piece;
    }

    /**
     * Constructor for tile using String representation
     * @author u7683699
     *
     * @param tileChar character that corresponds to tile
     * @param pieceID pieceID of the piece that it belongs to*/
    public Tile(char tileChar, int pieceID) {

        this.districtType = characterToDistrict(tileChar);
        this.isPlaza = Character.isUpperCase(tileChar);
        this.height = 0;
        this.isOccupied = false;
        this.piece = pieceID;

    };

    /**
     * Matches the character that represents a tile to a district type
     * @author u7683699 u7330006
     *
     * @param  tileChar the character that represents the tile*/
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

    /**
     * Getter method to retrieve stars from a tile (Useful for scores calculations Tasks 15-20, 23A-F)
     * @author u7330006
     *
     */
    public int getStars() {
        // Only Plaza's have stars
        if (this.isPlaza) {
            return switch (this.districtType) {
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



    //For testing
    @Override
    public String toString() {
        return "Tile( " + districtType + " | isPlaza: " + isPlaza + " | height: " + height + " | isOccupied: " + isOccupied + ")";
    }

    //For recovering gameString
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
