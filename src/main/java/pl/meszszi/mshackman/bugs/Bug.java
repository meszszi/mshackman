package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import main.java.pl.meszszi.mshackman.IDangerous;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;

import java.util.ArrayList;

/**
 * Abstract class that represents any bug on the game map.
 */

public abstract class Bug extends MapElement implements IDangerous {

    protected final BugType type;
    protected MoveDirection facingDirection;


    public Bug(GameMap map, Position position, BugType type) {
        super(map, position);
        this.type = type;
    }


    /**
     * Getter for type.
     * @return this.type
     */
    public BugType getType() {
        return this.type;
    }


    /**
     * Finds potential target on the game map according to bug's AI (most of the time there will be one target,
     * in case more positions meet requirements to be bug's target, only one is selected).
     * @return Position of target.
     */
    public abstract Position obtainTarget();


    /**
     * Finds all possible MoveDirections in which a bug can move in the next round.
     * @return ArrayList of valid MoveDirections
     */
    public ArrayList<MoveDirection> getValidMoves(){

        ArrayList<MoveDirection> validMoves = new ArrayList<MoveDirection>();

        //TODO

        return validMoves;
    }


    /**
     * Chooses optimal move (minimizing euclidean distance from target) from the list of valid moves.
     * @param validMoves - ArrayList of MoveDirections in which bug can move in the next round.
     * @return optimal MoveDirection (if there are more than one, only one of them is selected).
     */
    public ArrayList<MoveDirection> getOptimalMove(ArrayList<MoveDirection> validMoves) {

        ArrayList<MoveDirection> optimalMoves = new ArrayList<MoveDirection>();

        //TODO

        return optimalMoves;
    }


    /**
     * Setter for facingDirection
     * @param facingDirection - new facingDirection
     */
    public void setFacingDirection(MoveDirection facingDirection) {
        this.facingDirection = facingDirection;
    }
}