package main.java.pl.meszszi.mshackman;

/**
 * Enum type for different directions of movement (for players and for bugs).
 */

public enum MoveDirection {
    LEFT,
    RIGHT,
    UP,
    DOWN;


    /**
     * Finds opposite direction to this.
     * @return MoveDirection opposite to this
     */
    public MoveDirection getOpposite() {
        switch (this){

            case LEFT:
                return RIGHT;

            case RIGHT:
                return LEFT;

            case UP:
                return DOWN;

            case DOWN:
                return UP;

            default:
                return null;
        }
    }


    /**
     * Creates Position vector according to move direction.
     * @return proper Position (vector).
     */
    public Position getVector() {
        switch (this){

            case LEFT:
                return new Position(-1, 0);

            case RIGHT:
                return new Position(1, 0);

            case UP:
                return new Position(0, -1);

            case DOWN:
                return new Position(0, 1);

            default:
                return new Position(0, 0);
        }
    }
}
