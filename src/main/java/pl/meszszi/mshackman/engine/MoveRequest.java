package main.java.pl.meszszi.mshackman.engine;

import main.java.pl.meszszi.mshackman.MoveDirection;

/**
 * Class used to represent moves that out bot wants to make.
 */

public class MoveRequest {
    private MoveDirection moveDirection;
    private int bombTimer;

    public void setMoveDirection(MoveDirection moveDirection) {
        this.moveDirection = moveDirection;
    }


    /**
     * Sets bomb timer, if the time value is invalid -> prints message to stderr.
     * @param time - requested timer value
     */
    public void setBombTimer(int time) {

        if(time >= 2 && time <= 5)
            this.bombTimer = time;

        else
            System.err.println(String.format("Tried to set bombTimer to %d", time));
    }


    @Override
    public String toString() {
        String moveRequest = "pass";

        if(moveDirection != null)
            moveRequest = moveDirection.toString();

        if(bombTimer != 0) {
            moveRequest.concat(String.format(";drop_bomb %d", bombTimer));
            bombTimer = 0;
        }

        return moveRequest;
    }
}
