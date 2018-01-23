package main.java.pl.meszszi.mshackman.botAI;

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
 * Tries to predict few next moves of bugs.
 */

public class SmartBot extends DijkstraBot {

    public SmartBot(String name) {
        super(name);
    }


    /**
     * Dijkstra node class for algorithm purposes.
     */
    private class DijkstraNode extends GraphNode {

        final int time;

        DijkstraNode(Position position, Position source, MoveDirection directionFromSource, int distanceFromSource, int time) {
            super(position, source, directionFromSource, distanceFromSource);
            this.time = time;
        }
    }

    @Override
    protected MoveRequest getNextMove() {

        gameMap.updateDanger();

        Position heroPos = gameMap.getHero().getPosition();
        Position enemyPos = gameMap.getEnemy().getPosition();

        DijkstraNode paths[][] = setPaths(heroPos);
        int enemyPaths[][] = getEnemyPaths(enemyPos);

        Position target = getOptimalTarget(paths, enemyPaths);

        MoveDirection direction = getDirectionTowardsTarget(paths, heroPos, target);

        return new MoveRequest(direction);
    }


    /**
     * Sets distances from current hero's position by applying dijkstra's algorithm to current board.
     *
     * @return array of distances
     */
    @Override
    DijkstraNode[][] setPaths(Position source) {

        int width = this.gameState.getBoardWidth();
        int height = this.gameState.getBoardHeight();

        DijkstraNode paths[][] = new DijkstraNode[width][height];

        // Comparator for priority queue.
        Comparator<DijkstraNode> comparator = new Comparator<DijkstraNode>() {
            @Override
            public int compare(DijkstraNode node1, DijkstraNode node2) {
                return node1.distanceFromSource - node2.distanceFromSource;
            }
        };

        PriorityQueue<DijkstraNode> queue = new PriorityQueue<>(comparator);
        queue.add(new DijkstraNode(source, source, null,
                gameMap.getDanger(source, 0), 0));

        // Actual algorithm is done here.
        while (queue.size() > 0) {

            DijkstraNode node = queue.remove();
            Position position = node.position;

            if (paths[position.getX()][position.getY()] != null)
                continue;

            paths[position.getX()][position.getY()] = node;

            for (ValidMove move : gameMap.getValidMoves(position)) {

                Position target = move.getTarget();
                queue.add(new DijkstraNode(
                        target,
                        position,
                        move.getMoveDirection(),
                        node.distanceFromSource + 1 + gameMap.getDanger(target, node.time + 1),
                        node.time + 1
                ));
            }
        }

        return paths;
    }
}