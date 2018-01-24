package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;

public abstract class PathFindBot extends BotAI {

    PathFindBot(String botName) {
        super(botName);
    }


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    abstract Position getTargetPosition(GraphNode distances[][]);


    /**
     * Executes path finding algorithm on game board and sets distances from hero to every accessible field on the map
     */
    abstract GraphNode[][] setPaths();


    @Override
    MoveRequest getNextMove() {

        GraphNode paths[][] = setPaths();

        Position target = getTargetPosition(paths);

        if(target == null)
            return new MoveRequest();

        MoveDirection direction = getDirectionToTarget(paths, gameMap.getHero().getPosition(), target);

        return new MoveRequest(direction);
    }


    /**
     * Calculates optimal move in which player must move to get closer to its target point.
     * @param distances - int array with set distances from hero's current position
     * @param start - starting position
     * @param target - position that hero wants to reach
     * @return optimal MoveDirection.
     */
    MoveDirection getDirectionToTarget(GraphNode distances[][], Position start, Position target) {

        GraphNode field = distances[target.getX()][target.getY()];

        while(field.source != start)
            field = distances[field.source.getX()][field.source.getY()];

        return field.directionFromSource;
    }
}
