package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BasicBFSBot extends BotAI{

    public BasicBFSBot(String botName) {
        super(botName);
    }

    public MoveRequest getNextMove() {

        GraphField distances[][] = setDistances();

        System.err.println(String.format("round: %d", gameState.getCurrentRound()));

        /*for(int i = 0; i < distances[0].length; i++) {
            for(int j = 0; j < distances.length; j++) {
                System.err.print(String.format("%d,\t", distances[j][i].getDistanceFromSource()));
            }
            System.err.print("\n");
        }*/

        Position target = getClosestSnippetPosition(distances);

        if(target == null)
            return new MoveRequest();

        MoveDirection direction = getDirectionToTarget(distances, gameMap.getHero().getPosition(), target);

        MoveRequest moveRequest = new MoveRequest();
        moveRequest.setMoveDirection(direction);

        return moveRequest;
    }


    /**
     * Executes basic BFS algorithm on game board and sets distances from hero to every accessible field on the map
     */
    private GraphField[][] setDistances() {

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();

        GraphField distances[][] = new GraphField[width][height];

        for(Bug bug : gameMap.getBugs()) {

            for(MoveDirection dir : MoveDirection.values()) {

                if(bug.getFacingDirection() == null || dir != bug.getFacingDirection().getOpposite()) {
                    distances[bug.getPosition().getX()][bug.getPosition().getY()] = new GraphField(Integer.MAX_VALUE, null, null);
                    Position nextBugPos = bug.getPosition().move(bug.getFacingDirection());

                    if(gameMap.isInsideMap(nextBugPos))
                        distances[nextBugPos.getX()][nextBugPos.getY()] = new GraphField(Integer.MAX_VALUE, null, null);
                }
            }
        }

        Queue<Position> queue = new LinkedList<>();
        Position heroPos = gameMap.getHero().getPosition();
        queue.add(heroPos);
        distances[heroPos.getX()][heroPos.getY()] = new GraphField(0, null, heroPos);

        Position position;
        int lol = 0;
        while(!queue.isEmpty() && (position = queue.remove()) != null) {

            System.err.println(lol++);

            GraphField field = distances[position.getX()][position.getY()];

            for(ValidMove validMove : gameMap.getValidMoves(position)) {

                Position newPos = validMove.getTarget();

                if(distances[newPos.getX()][newPos.getY()] != null)
                    continue;

                distances[newPos.getX()][newPos.getY()] = new GraphField(
                        field.getDistanceFromSource() + 1,
                        validMove.getMoveDirection(),
                        position);

                queue.add(newPos);
            }
        }

        return distances;
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


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    private Position getClosestSnippetPosition(GraphField distances[][]) {
        Position result = new Position(0, 0);
        int distance = Integer.MAX_VALUE;

        System.err.println(String.format("round %d: snippets: %d", gameState.getCurrentRound(), gameMap.getCodeSnippets().size()));

        for(CodeSnippet snippet : gameMap.getCodeSnippets()) {

            Position snippetPosition = snippet.getPosition();

            if(distances[snippetPosition.getX()][snippetPosition.getY()] != null &&
                    distances[snippetPosition.getX()][snippetPosition.getY()].getDistanceFromSource() < distance) {

                distance = distances[snippetPosition.getX()][snippetPosition.getY()].getDistanceFromSource();
                result = snippetPosition;
            }
        }

        if(distance == Integer.MAX_VALUE)   // In case there are no snippets on the field
            return null;

        System.err.println(distance);
        return result;
    }
}
