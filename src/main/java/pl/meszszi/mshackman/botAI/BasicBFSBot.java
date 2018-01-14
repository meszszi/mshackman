package main.java.pl.meszszi.mshackman.botAI;

import com.sun.org.apache.bcel.internal.classfile.Code;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
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

        Position XD = gameMap.getHero().getPosition().move(null);

        int distances[][] = setDistances();

        System.err.println(String.format("round: %d", gameState.getCurrentRound()));

        for(int i = 0; i < distances[0].length; i++) {
            for(int j = 0; j < distances.length; j++) {
                System.err.print(String.format("%d,\t", distances[j][i]));
            }
            System.err.print("\n");
        }

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
    private int[][] setDistances() {

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();

        int distances[][] = new int[width][height];
        for(int i = 0; i < distances.length; i++)
            Arrays.fill(distances[i], -1);

        for(Bug bug : gameMap.getBugs()) {

            for(MoveDirection dir : MoveDirection.values()) {

                if(bug.getFacingDirection() == null || dir != bug.getFacingDirection().getOpposite()) {
                    distances[bug.getPosition().getX()][bug.getPosition().getY()] = Integer.MAX_VALUE;
                    Position nextBugPos = bug.getPosition().move(bug.getFacingDirection());

                    if(gameMap.isInsideMap(nextBugPos))
                        distances[nextBugPos.getX()][nextBugPos.getY()] = Integer.MAX_VALUE;
                }
            }
        }

        Queue<Position> queue = new LinkedList<>();
        Position heroPos = gameMap.getHero().getPosition();
        queue.add(heroPos);
        distances[heroPos.getX()][heroPos.getY()] = 0;

        Position position;
        while(!queue.isEmpty() && (position = queue.remove()) != null) {


            for(MoveDirection direction : gameMap.getValidMoves(position)) {

                Position newPos = position.move(direction);

                if(distances[newPos.getX()][newPos.getY()] >= 0)
                    continue;

                distances[newPos.getX()][newPos.getY()] = distances[position.getX()][position.getY()] + 1;
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
    private MoveDirection getDirectionToTarget(int distances[][], Position start, Position target) {

        int distance = distances[target.getX()][target.getY()];

        while(distance > 1) {

            for(MoveDirection direction : gameMap.getValidMoves(target)) {

                Position closerTarget = target.move(direction);

                if(distances[closerTarget.getX()][closerTarget.getY()] == distance - 1) {

                    target = closerTarget;
                    distance--;
                    break;
                }
            }
        }

        return target.getDirectionFrom(start);
    }


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    private Position getClosestSnippetPosition(int distances[][]) {
        Position result = new Position(0, 0);
        int distance = Integer.MAX_VALUE;

        System.err.println(String.format("round %d: snippets: %d", gameState.getCurrentRound(), gameMap.getCodeSnippets().size()));

        for(CodeSnippet snippet : gameMap.getCodeSnippets()) {

            Position snippetPosition = snippet.getPosition();

            if(distances[snippetPosition.getX()][snippetPosition.getY()] < distance) {

                distance = distances[snippetPosition.getX()][snippetPosition.getY()];
                result = snippetPosition;
            }
        }

        if(distance == Integer.MAX_VALUE)   // In case there are no snippets on the field
            return null;

        System.err.println(distance);
        return result;
    }
}
