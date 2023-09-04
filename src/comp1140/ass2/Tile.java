package comp1140.ass2;

public class Tile {
    private District districtType;

    private Boolean isPlaza;

    private int height;

    private Boolean isOccupied;

    public Tile(District districtType, Boolean isPlaza) {

    }
    private int getHeight (){
        return this.height;
    }

    private void setHeight(int height) {
        this.height = height;
    }
}
