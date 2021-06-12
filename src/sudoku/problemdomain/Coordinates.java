package sudoku.problemdomain;

import java.util.Objects;

//a coordinate object with x and y values
public class Coordinates {
    private final int x;
    private final int y;

    //constructor
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //getters for x and y values
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //new definitions for 2 object class methods
    //used with hashmap implementation of sudoku tiles
    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
