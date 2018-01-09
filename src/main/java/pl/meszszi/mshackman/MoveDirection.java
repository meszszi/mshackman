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
    MoveDirection getOpposite() {
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
}
