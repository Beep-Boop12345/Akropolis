package comp1140.ass2;

public class Tile {
    private District districtType;

    private Boolean isPlaza;

    private int height;

    private Boolean isOccupied;

    public void Tile() {

    }
    private int getHeight (){
        return this.height;
    }

    private void setHeight(int height) {
        this.height = height;
    }
}
