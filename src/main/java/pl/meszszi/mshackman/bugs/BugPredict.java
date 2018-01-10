package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import main.java.pl.meszszi.mshackman.players.Player;

/**
 * Class that represents Predict type of bug.
 * Predict type tries to predict where the closest player is going by setting its target 4 fields ahead of current player's direction.
 */

public class BugPredict extends Bug {

    public BugPredict(GameMap map, Position position) {
        super(map, position, BugType.PREDICT);
    }


    /**
     * Gets potential target according to bug's AI.
     * @return Position that most likely be the bug's next target.
     */
    @Override
    public Position obtainTarget() {
        Player closestPlayer = new Player(null, null, null);
        Position target = new Position(0, 0);
        int minDistance = Integer.MAX_VALUE;

        for(Player player : this.map.getPlayers()) {
            int distance = player.getPosition().getDistanceSquare(this.position);

            if(distance < minDistance) {
                closestPlayer = player;
                target = player.getPosition();
                minDistance = distance;
            }
        }

        Position vector = closestPlayer.getFacingDirection().getVector();

        target = target.add(vector.multiply(4)); // Moves target towards player's facing direction by 4 fields.

        return target;
    }
}
