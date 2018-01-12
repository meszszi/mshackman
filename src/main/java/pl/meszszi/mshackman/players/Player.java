package main.java.pl.meszszi.mshackman.players;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that will represent any player on the game field.
 */

public class Player extends MapElement{
    private final int playerID;
    private MoveDirection facingDirection;
    private int codeSnippets;
    private int bombs;

    public Player(GameMap map, Position position, int playerID) {
        super(map, position);
        this.playerID = playerID;
        this.facingDirection = null;
        this.codeSnippets = 0;
        this.bombs = 0;
    }


    /**
     * Getter for type.
     * @return this.type.
     */
    public int getID() {
        return this.playerID;
    }


    /**
     * Getter for facingDirection.
     * @return this.facingDirection.
     */
    public MoveDirection getFacingDirection() {
        return this.facingDirection;
    }


    /**
     * Setter for facingDirection
     * @param facingDirection
     */
    public void setFacingDirection(MoveDirection facingDirection) {
        this.facingDirection = facingDirection;
    }
}
