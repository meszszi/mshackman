package main.java.pl.meszszi.mshackman.botAI;

import com.sun.org.apache.bcel.internal.classfile.Code;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class implementing Dijkstra's path finding algorithm (bot doesn't get confused when surrounded by bugs).
 */

public class DijkstraBot extends BotAI {

    public DijkstraBot(String name) {
        super(name);
    }


    @Override
    protected MoveRequest getNextMove() {

        System.err.println(gameState.getCurrentRound());
        System.err.println(gameMap.updateDanger());
        System.err.println("\n");

        Position heroPos = gameMap.getHero().getPosition();
        Position enemyPos = gameMap.getEnemy().getPosition();

        GraphNode paths[][] = setPaths(heroPos);
        int enemyPaths[][] = getEnemyPaths(enemyPos);

        Position target = getOptimalTarget(paths, enemyPaths);

        MoveDirection direction = getDirectionTowardsTarget(paths, heroPos, target);

        return new MoveRequest(direction);
    }


    /**
     * Sets distances from current hero's position by applying dijkstra's algorithm to current board.
     * @return array of distances
     */
    GraphNode[][] setPaths(Position source) {

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
    Position getOptimalTarget(GraphNode paths[][], int[][] enemyPaths) {

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
     * Calculates direction in which player must move to get to obtained target.
     * @param paths - array of graph nodes
     * @param source - starting position
     * @param target - target position
     * @return proper MoveDirection.
     */
    MoveDirection getDirectionTowardsTarget(GraphNode paths[][], Position source, Position target) {

        if(target == null || source.equals(target))
            return null;

        while(!paths[target.getX()][target.getY()].source.equals(source))
            target = paths[target.getX()][target.getY()].source;

        return paths[target.getX()][target.getY()].directionFromSource;
    }


    /**
     * Sets distances from source by performing BFS algorithm.
     * Treats bugs as walls.
     * Intended to be used for predicting enemy's next move.
     * @param source - starting position
     * @return - int array representing distances from source.
     */
    int[][] getEnemyPaths(Position source) {

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
