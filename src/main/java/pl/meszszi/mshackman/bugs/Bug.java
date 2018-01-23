package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.*;
import main.java.pl.meszszi.mshackman.maps.DangerMap;
import main.java.pl.meszszi.mshackman.maps.GameMap;

import java.util.ArrayList;

/**
 * Abstract class that represents any bug on the game map.
 */

public abstract class Bug extends MapElement implements IDangerous {

    public final static int DANGER_MEASURE = 40;

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


    public MoveDirection getFacingDirection() {
        return facingDirection;
    }


    /**
     * Finds potential target on the game map according to bug's AI (most of the time there will be one target,
     * in case more positions meet requirements to be bug's target, only one is selected).
     * @return Position of target.
     */
    public abstract Position obtainTarget();


    /**
     * Finds all possible MoveDirections in which a bug can move in the next round.
     * @return ArrayList of validMoves
     */
    public ArrayList<ValidMove> getValidMoves(){
        return this.map.getNonPortalMoves(this.position);
    }


    /**
     * Chooses optimal move (minimizing euclidean distance from target) from the list of valid moves.
     * @param validMoves - ArrayList of MoveDirections in which bug can move in the next round.
     * @return optimal MoveDirection (if there are more than one, only one of them is selected).
     */
    public ArrayList<MoveDirection> getOptimalMove(ArrayList<MoveDirection> validMoves) {

        ArrayList<MoveDirection> optimalMoves = new ArrayList<>();

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


    @Override
    public void setDanger(DangerMap dangerMap) {

        if(this.facingDirection == null) {

            for(int i = 0; i < 5; i++) {
                dangerMap.addDanger(position, i, this.DANGER_MEASURE * 2);

                for(ValidMove validMove : map.getNonPortalMoves(this.position))
                    dangerMap.addDanger(position.move(validMove.getMoveDirection()), i, this.DANGER_MEASURE);
            }



            return;
        }


        MoveDirection backDirection = this.facingDirection.getOpposite();
        Position predictedPosition = this.position;
        int time = 0;

        while(map.getNonPortalMoves(predictedPosition).size() <= 2) {
            dangerMap.addDanger(predictedPosition, time, this.DANGER_MEASURE * 2);
            dangerMap.addDanger(predictedPosition, time + 1, this.DANGER_MEASURE * 2);
            MoveDirection nextDirection = null;

            for(ValidMove validMove : map.getNonPortalMoves(predictedPosition))
                if(validMove.getMoveDirection() != backDirection) {
                    nextDirection = validMove.getMoveDirection();
                    break;
                }

            if(nextDirection == null)
                break;

            predictedPosition = predictedPosition.move(nextDirection);
            backDirection = nextDirection.getOpposite();
            //dangerMap.addDanger(predictedPosition, time, this.DANGER_MEASURE);
            time ++;
        }

        for(int i = time; i < time + 4; i++) {
            dangerMap.addDanger(predictedPosition, i, this.DANGER_MEASURE * 2);

            //for(ValidMove validMove : map.getNonPortalMoves(predictedPosition))
            //    dangerMap.addDanger(predictedPosition.move(validMove.getMoveDirection()), i, this.DANGER_MEASURE);
        }

    }
}