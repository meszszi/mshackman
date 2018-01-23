package main.java.pl.meszszi.mshackman.bomb;

import main.java.pl.meszszi.mshackman.*;
import main.java.pl.meszszi.mshackman.maps.DangerMap;
import main.java.pl.meszszi.mshackman.maps.GameMap;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Class that represents ticking bombs on the board.
 */

public class Bomb extends MapElement implements IDangerous{

    private final static int DANGER_MEASURE = 40;

    private final int timer;

    public Bomb(GameMap gameMap, Position position, int timer) {
        super(gameMap, position);
        this.timer = timer;
    }


    /**
     * Sets danger areas on DangerMap.
     * @param dangerMap - DangerMap to set danger areas on.
     */
    @Override
    public void setDanger(DangerMap dangerMap) {

        dangerMap.addDanger(this.position, this.timer, this.DANGER_MEASURE);

        for(ValidMove validMove : this.map.getNonPortalMoves(this.position)) {

            MoveDirection direction = validMove.getMoveDirection();

            Position fireRange = this.position.move(direction);

            while(this.map.isAccessible(fireRange)) {

                dangerMap.addDanger(fireRange, this.timer, this.DANGER_MEASURE);
                fireRange = fireRange.move(direction);
            }
        }
    }
}
