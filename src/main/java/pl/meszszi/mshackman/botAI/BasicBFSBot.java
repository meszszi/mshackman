package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;

import java.util.LinkedList;
import java.util.Queue;

public class BasicBFSBot extends PathFindBot {

    public BasicBFSBot(String botName) {
        super(botName);
    }


    /**
     * Executes basic BFS algorithm on game board and sets distances from hero to every accessible field on the map
     */
    @Override
    GraphNode[][] setPaths() {

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();

        GraphNode distances[][] = new GraphNode[width][height];

        for(Bug bug : gameMap.getBugs()) {

            for(MoveDirection dir : MoveDirection.values()) {

                if(bug.getFacingDirection() == null || dir != bug.getFacingDirection().getOpposite()) {
                    distances[bug.getPosition().getX()][bug.getPosition().getY()] = new GraphNode(bug.getPosition(), null, null, Integer.MAX_VALUE);
                    Position nextBugPos = bug.getPosition().move(bug.getFacingDirection());

                    if(gameMap.isInsideMap(nextBugPos))
                        distances[nextBugPos.getX()][nextBugPos.getY()] = new GraphNode(bug.getPosition(), null, null, Integer.MAX_VALUE);
                }
            }
        }

        Queue<Position> queue = new LinkedList<>();
        Position heroPos = gameMap.getHero().getPosition();
        queue.add(heroPos);
        distances[heroPos.getX()][heroPos.getY()] = new GraphNode(heroPos, null, null, 0);

        Position position;
        int lol = 0;
        while(!queue.isEmpty() && (position = queue.remove()) != null) {

            GraphNode field = distances[position.getX()][position.getY()];

            for(ValidMove validMove : gameMap.getValidMoves(position)) {

                Position newPos = validMove.getTarget();

                if(distances[newPos.getX()][newPos.getY()] != null)
                    continue;

                distances[newPos.getX()][newPos.getY()] = new GraphNode(
                        newPos,
                        position,
                        validMove.getMoveDirection(),
                        field.distanceFromSource + 1);

                queue.add(newPos);
            }
        }

        return distances;
    }


    /**
     * Gets the position of the closest CodeSnippet to hero.
     * @param distances - array of distances from hero's position
     * @return position of the closest snippet
     */
    @Override
    Position getTargetPosition(GraphNode distances[][]) {
        Position result = new Position(0, 0);
        int distance = Integer.MAX_VALUE;

        System.err.println(String.format("round %d: snippets: %d", gameState.getCurrentRound(), gameMap.getCodeSnippets().size()));

        for(CodeSnippet snippet : gameMap.getCodeSnippets()) {

            Position snippetPosition = snippet.getPosition();

            if(distances[snippetPosition.getX()][snippetPosition.getY()] != null &&
                    distances[snippetPosition.getX()][snippetPosition.getY()].distanceFromSource < distance) {

                distance = distances[snippetPosition.getX()][snippetPosition.getY()].distanceFromSource;
                result = snippetPosition;
            }
        }

        if(distance == Integer.MAX_VALUE)   // In case there are no snippets on the field
            return null;

        System.err.println(distance);
        return result;
    }
}
