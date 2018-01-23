package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;

public abstract class PathFindBot extends BotAI {


    /**
     * Class representing node in basic BFS path finding
     * @param botName
     */
    PathFindBot(String botName) {
        super(botName);
    }


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    protected abstract Position getTargetPosition(GraphNode distances[][]);


    /**
     * Executes path finding algorithm on game board and sets distances from hero to every accessible field on the map
     */
    protected abstract GraphNode[][] setDistances();


    @Override
    public MoveRequest getNextMove() {

        GraphNode distances[][] = setDistances();

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
    private MoveDirection getDirectionToTarget(GraphNode distances[][], Position start, Position target) {

        GraphNode field = distances[target.getX()][target.getY()];

        while(field.source != start)
            field = distances[field.source.getX()][field.source.getY()];

        return field.directionFromSource;
    }
}
