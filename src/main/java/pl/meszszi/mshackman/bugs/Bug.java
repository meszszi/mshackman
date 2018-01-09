package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.FieldObject;
import main.java.pl.meszszi.mshackman.maps.GameField;
import main.java.pl.meszszi.mshackman.IDangerous;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;

import java.util.ArrayList;

/**
 * Abstract class that represents any bug on the game field.
 */

public abstract class Bug extends FieldObject implements IDangerous {

    private MoveDirection facingDirection;

    private final GameField field;

    public Bug(GameField field) {
        this.field = field;
    }

    /**
     * Finds all potential targets on the game field according to bug's AI (most of the time there will be one target,
     * but some cases require analysing more possibilities, e.g. both players are equally distant from Chase type bug).
     * @return ArrayList of all possible target Positions
     */
    public abstract ArrayList<Position> obtainTargets();


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
     * Chooses all optimal moves (minimizing euclidean distance from target) from the list of valid moves.
     * @param validMoves - ArrayList of MoveDirections in which bug can move in the next round.
     * @return ArrayList of optimal MoveDirections (bug will certainly make a move towards one of those directions in the next round).
     */
    public ArrayList<MoveDirection> getOptimalMoves(ArrayList<MoveDirection> validMoves) {

        ArrayList<MoveDirection> optimalMoves = new ArrayList<MoveDirection>();

        //TODO

        return optimalMoves;
    }
}