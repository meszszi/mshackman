package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class implementing Dijkstra's path finding algorithm (bot doesn't get confused when surrounded by bugs).
 */

public class DijkstraBot extends PathFindBot {

    public DijkstraBot(String name) {
        super(name);
    }


    /**
     * Sets distances from current hero's position by applying dijkstra's algorithm to current board.
     * @return array of distances
     */
    @Override
    GraphNode[][] setPaths() {

        Position source = gameMap.getHero().getPosition();

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();
        int weights[][] = new int[width][height];

        GraphNode paths[][] = new GraphNode[width][height];

        // Initializes weights.
        for(int i = 0; i < width * height; i++)
            weights[i % width][i / width] = 1;

        // Gives weight of 40 to every field covered by a Bug.
        for(Bug bug : this.gameMap.getBugs()) {

            Position bugPos = bug.getPosition();
            weights[bugPos.getX()][bugPos.getY()] += 40;

            for(MoveDirection direction : MoveDirection.values()) {
                if(bug.getFacingDirection() == null || direction != bug.getFacingDirection().getOpposite()) {

                    Position nextToBug = bugPos.move(direction);

                    if(gameMap.isInsideMap(nextToBug))
                        weights[nextToBug.getX()][nextToBug.getY()] += 40;
                }
            }
        }

        // Comparator for priority queue.
        Comparator<GraphNode> comparator = new Comparator<GraphNode>() {
            @Override
            public int compare(GraphNode node1, GraphNode node2) {
                return node1.distanceFromSource - node2.distanceFromSource;
            }
        };

        PriorityQueue<GraphNode> queue = new PriorityQueue<>(comparator);
        queue.add(new GraphNode(source, source, null, 0));

        // Actual algorithm is done here.
        while(queue.size() > 0) {

            GraphNode node = queue.remove();
            Position position = node.position;

            if(paths[position.getX()][position.getY()] != null)
                continue;

            paths[position.getX()][position.getY()] = node;

            for(ValidMove move : gameMap.getValidMoves(position)) {

                Position target = move.getTarget();
                queue.add(new GraphNode(
                        target,
                        position,
                        move.getMoveDirection(),
                        node.distanceFromSource + weights[target.getX()][target.getY()]
                ));
            }
        }

        return paths;
    }


    /**
     * Looks for the closest snippet on the map.
     * @param paths - array of graph nodes
     * @return position of the closest snippet.
     */
    @Override
    Position getTargetPosition(GraphNode paths[][]) {

        int[][] enemyPaths = getEnemyPaths(gameMap.getEnemy().getPosition());

        Position enemyTarget = gameMap.getEnemy().getPosition();
        int enemyDistance = Integer.MAX_VALUE;
        for(CodeSnippet snippet : gameMap.getCodeSnippets()) {

            Position snippetPos = snippet.getPosition();
            if(enemyPaths[snippetPos.getX()][snippetPos.getY()] < enemyDistance) {

                enemyDistance = enemyPaths[snippetPos.getX()][snippetPos.getY()];
                enemyTarget = snippetPos;
            }
        }

        boolean enemyIsCloser = paths[enemyTarget.getX()][enemyTarget.getY()].distanceFromSource > enemyDistance;

        Position result = enemyTarget;
        int distance = Integer.MAX_VALUE;

        for(CodeSnippet snippet : gameMap.getCodeSnippets()) {

            Position snippetPos = snippet.getPosition();
            if(paths[snippetPos.getX()][snippetPos.getY()].distanceFromSource < distance
                    && !(snippetPos.equals(enemyTarget) && enemyIsCloser)) {

                distance = paths[snippetPos.getX()][snippetPos.getY()].distanceFromSource;
                result = snippetPos;
            }
        }

        return result;
    }


    /**
     * Sets distances from source by performing BFS algorithm.
     * Sees bugs as walls.
     * Intended to be used for predicting enemy's next move.
     * @param source - starting position
     * @return - int array representing distances from source.
     */
    private int[][] getEnemyPaths(Position source) {

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();

        int distances[][] = new int[width][height];

        for(int i = 0; i < width * height; i++)
            distances[i % width][i / width] = -1;

        for(Bug bug : gameMap.getBugs())
            distances[bug.getPosition().getX()][bug.getPosition().getY()] = Integer.MAX_VALUE;



        Queue<Position> queue = new LinkedList<>();
        queue.add(source);
        distances[source.getX()][source.getY()] = 0;

        Position position;
        while(!queue.isEmpty() && (position = queue.remove()) != null) {

            for(ValidMove validMove : gameMap.getValidMoves(position)) {

                Position newPos = validMove.getTarget();

                if(distances[newPos.getX()][newPos.getY()] >= 0)
                    continue;

                distances[newPos.getX()][newPos.getY()] = distances[position.getX()][position.getY()] + 1;
                queue.add(newPos);
            }
        }

        return distances;
    }
}
