package main.java.pl.meszszi.mshackman.players;

import main.java.pl.meszszi.mshackman.FieldObject;
import main.java.pl.meszszi.mshackman.MoveDirection;

/**
 * Class that will represent any player on the game field.
 */

public class Player extends FieldObject {
    private MoveDirection moveDirection;
    private int codeSnippets;
    private int bombs;
}
