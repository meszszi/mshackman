package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import main.java.pl.meszszi.mshackman.players.Player;

/**
 * Class that represents Lever type of Bug.
 * This type looks for the closest player and the closest bug to that player and sets its target to the point that is symmetric to other bug's position
 * prior to player's position (somehow tries to approach the player from opposite side than the other bug).
 */

public class BugLever extends Bug{

    public BugLever(GameMap map, Position position) {
        super(map, position, BugType.LEVER);
    }


    /**
     * Gets potential target according to bug's AI.
     * @return Position that most likely be the bug's next target.
     */
    @Override
    public Position obtainTarget() {
        Position closestPlayerPos = new Position(0, 0);
        int minDistance = Integer.MAX_VALUE;

        for(Player player : this.map.getPlayers()) {
            int distance = player.getPosition().getDistanceSquare(this.position);

            if(distance < minDistance) {
                closestPlayerPos = player.getPosition();
                minDistance = distance;
            }
        }

        minDistance = Integer.MAX_VALUE;
        Position closestBugPos = new Position(0, 0);

        for(Bug bug : this.map.getBugs()) {
            int distance = bug.getPosition().getDistanceSquare(closestPlayerPos);

            if(distance < minDistance) {
                closestBugPos = bug.getPosition();
                minDistance = distance;
            }
        }

        return closestPlayerPos.add(closestPlayerPos.subtract(closestBugPos));
    }
}
