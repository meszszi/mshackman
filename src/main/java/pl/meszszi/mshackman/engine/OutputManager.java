package main.java.pl.meszszi.mshackman.engine;

import main.java.pl.meszszi.mshackman.MoveDirection;

/**
 * Class used for managing signals from bot to game engine.
 */

public class OutputManager {


    /**
     * Sets player name in the game (only possible values are "bixie" and "bixiette", this option doesn't change the game at all).
     */
    public void setPlayerName(String playerName) {
        System.out.println(playerName);
    }


    /**
     * Prints a request for a move in a specified direction, plants a bomb if timer is non-zero.
     */
    public void makeMove(MoveRequest moveRequest) {
        System.out.println(moveRequest);
    }



}
