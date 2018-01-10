package main.java.pl.meszszi.mshackman.players;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that will represent any player on the game field.
 */

public class Player extends MapElement{
    private final PlayerType type;
    private MoveDirection facingDirection;
    private int codeSnippets;
    private int bombs;

    public Player(PlayerType type, GameMap map, Position position) {
        super(map, position);
        this.type = type;
        this.facingDirection = null;
        this.codeSnippets = 0;
        this.bombs = 0;
    }


    /**
     * Getter for type.
     * @return this.type.
     */
    public PlayerType getType() {
        return this.type;
    }


    /**
     * Getter for facingDirection.
     * @return this.facingDirection.
     */
    public MoveDirection getFacingDirection() {
        return this.facingDirection;
    }
}
