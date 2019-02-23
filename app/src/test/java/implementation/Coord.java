package implementation;

public class Coord {
    private int x;
    private int y;

    public Coord(int inX, int inY) {
        x=inX;
        y=inY;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    //Getter Methods
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
