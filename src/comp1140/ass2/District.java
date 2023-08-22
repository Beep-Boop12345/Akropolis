package comp1140.ass2;

public enum District {
    HOUSES("Houses"),
    MARKETS("Markets"),
    GARDENS("Gardens"),
    BARRACKS("Barracks"),
    TEMPLES("Temples"),
    QUARRY("Quarry");

    private final String displayName;

    District(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Return the stars associated with the district
     *
     * @return the number of stars associated with the district
     */
    public Integer getStars() {
        return switch (this) {
            case HOUSES -> 1;
            case GARDENS -> 3;
            default -> 2;
        };
    }



    /**
     * Returns a string representation of the district.
     *
     * @return the display name of the district.
     */
    @Override
    public String toString() {
        return "District: " + displayName;
    }


}
