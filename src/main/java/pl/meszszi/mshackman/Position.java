package main.java.pl.meszszi.mshackman;

/**
 * Class that represent the position of an object on game field.
 * (0, 0) - left-upper corner
 * (w, h) - right-bottom corner (w, h -> width and height of the map)
 */

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Getter for x.
     * @return x
     */
    public int getX() {
        return this.x;
    }


    /**
     * Getter for y.
     * @return y
     */
    public int getY() {
        return this.y;
    }


    /**
     * Adds given coordinates to this.
     * @param x - x-axis coordinate
     * @param y - y-axis coordinate
     * @return new Position created by adding proper coordinates
     */
    public Position add(int x, int y) {
        return new Position(this.x + x, this.y + y);
    }


    /**
     * Adds given Position's coordinates to this.
     * @param p - Position to add
     * @return new Position created by adding p to this
     */
    public Position add(Position p) {
        return new Position(this.x + p.getX(), this.y + p.getY());
    }


    /**
     * Subtracts given Position's coordinates from this.
     * @param p - Position to subtract
     * @return new Position created by subtracting p from this.
     */
    public Position subtract(Position p) {
        return new Position(this.x - p.getX(), this.y - p.getY());
    }


    /**
     * Creates new Position by multiplying coordinates by given scalar.
     * @param scalar - number by which coordinates are multiplied
     * @return scaled position.
     */
    public Position multiply(int scalar) {
        return new Position(this.getX() * scalar, this.getY() * scalar);
    }


    /**
     * Creates new Position by moving this in given MoveDirection.
     * @param direction - MoveDirection in witch this should be moved
     * @return new Position created by moving this to given direction
     */
    public Position move(MoveDirection direction) {

        if(direction == MoveDirection.LEFT)
            return this.add(-1, 0);

        if(direction == MoveDirection.RIGHT)
            return this.add(1, 0);

        if(direction == MoveDirection.UP)
            return this.add(0, -1);

        if(direction == MoveDirection.DOWN)
            return this.add(0, 1);

        return this;
    }


    /**
     * Checks if given Position has the same coordinates as this.
     * @param p - another Position
     * @return true if both coordinates match
     */
    public boolean equals(Position p) {
        return this.x == p.getX() && this.y == p.getY();
    }


    /**
     * Calculates the square of distance between this and given position.
     * Square is used instead of actual distance to avoid unnecessary floating point calculations.
     * @param p - another Position
     * @return square of euclidean distance to p
     */
    public int getDistanceSquare(Position p) {
        return (this.x + p.getX())^2 + (this.y + p.getY())^2;
    }



}
