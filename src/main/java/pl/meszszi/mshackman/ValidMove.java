package main.java.pl.meszszi.mshackman;

/**
 * Class used to represent valid moves on the map
 */

public class ValidMove {

    private final Position target;
    private final MoveDirection moveDirection;

    public ValidMove(Position target, MoveDirection moveDirection) {
        this.target = target;
        this.moveDirection = moveDirection;
    }


    public Position getTarget() {
        return target;
    }

    public MoveDirection getMoveDirection() {
        return moveDirection;
    }
}
