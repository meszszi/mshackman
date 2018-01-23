package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import main.java.pl.meszszi.mshackman.players.Player;

/**
 * Class that represents FarChase bug type.
 * This type of bug sets position of the furthest player as its target.
 */

public class BugFarChase extends Bug{

    public BugFarChase(GameMap map, Position position) {
        super(map, position, BugType.FAR_CHASE);
    }


    /**
     * Gets potential target according to bug's AI.
     * @return list of Positions that will most likely be the bug's next target.
     */
    @Override
    public Position obtainTarget() {
        Position target = new Position(0, 0);
        int maxDistance = Integer.MIN_VALUE;

        for(Player player : this.map.getPlayers()) {
            int distance = player.getPosition().getDistanceSquare(this.position);

            if(distance > maxDistance) {
                target = player.getPosition();
                maxDistance = distance;
            }
        }

        return target;
    }
}
