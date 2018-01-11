package main.java.pl.meszszi.mshackman.engine;

import main.java.pl.meszszi.mshackman.MoveDirection;

/**
 * Class used for managing signals from bot to game engine.
 */

public class OutputManager {

    private final String characterName;
    private int bombTimer = 0;

    public OutputManager(String characterName) {
        this.characterName = characterName;
    }


    /**
     * Sets player name in the game (only possible values are "bixie" and "bixiette", this option doesn't change the game at all).
     */
    public void setPlayerName() {
        System.out.println(this.characterName);
    }


    /**
     * Prints a request for a move in a specified direction, plants a bomb if timer is non-zero.
     * @param direction - direction of requested move
     */
    public void makeMove(MoveDirection direction) {
        String moveRequest = "pass";

        if(direction != null)
            moveRequest = direction.toString();

        if(this.bombTimer != 0) {
            moveRequest.concat(String.format(";drop_bomb %d", this.bombTimer));
            this.bombTimer = 0;
        }

        System.out.println(moveRequest);
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
}
