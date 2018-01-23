package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;

/**
 * Class that represents Chase type of Bug.
 * Chase type sets its target to the closest (by Euclidean distance) player on the field.
 */

public class BugChase extends Bug{
    public BugChase(GameMap map, Position position) {
        super(map, position, BugType.CHASE);
    }


    /**
     * Gets potential target according to bug's AI.
     * @return Position that will most likely be the bug's next target.
     */
    @Override
    public Position obtainTarget() {
        Position target = new Position(0, 0);
        int minDistance = Integer.MAX_VALUE;

        for(Player player : this.map.getPlayers()) {
            int distance = player.getPosition().getDistanceSquare(this.position);

            if(distance < minDistance) {
                target = player.getPosition();
                minDistance = distance;
            }
        }

        return target;
    }
}
