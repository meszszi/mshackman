package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;

import java.util.LinkedList;
import java.util.Queue;

public abstract class BasicPathFindBot extends BotAI {

    public BasicPathFindBot(String botName) {
        super(botName);
    }


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    protected abstract Position getTargetPosition(GraphField distances[][]);


    /**
     * Executes path finding algorithm on game board and sets distances from hero to every accessible field on the map
     */
    protected abstract GraphField[][] setDistances();


    @Override
    public MoveRequest getNextMove() {

        GraphField distances[][] = setDistances();

        System.err.println(String.format("round: %d", gameState.getCurrentRound()));

        Position target = getTargetPosition(distances);

        if(target == null)
            return new MoveRequest();

        MoveDirection direction = getDirectionToTarget(distances, gameMap.getHero().getPosition(), target);

        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setMoveDirection(direction);

        return moveRequest;
    }




    /**
     * Calculates optimal move in which player must move to get closer to its target point.
     * @param distances - int array with set distances from hero's current position
     * @param start - starting position
     * @param target - position that hero wants to reach
     * @return optimal MoveDirection.
     */
    private MoveDirection getDirectionToTarget(GraphField distances[][], Position start, Position target) {

        GraphField field = distances[target.getX()][target.getY()];

        while(field.getPreviousPosition() != start)
            field = distances[field.getPreviousPosition().getX()][field.getPreviousPosition().getY()];

        return field.getDirectionFromSource();
    }
}
