package main.java.pl.meszszi.mshackman.field;

import main.java.pl.meszszi.mshackman.IDangerous;
import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.bugs.BugType;
import main.java.pl.meszszi.mshackman.maps.DangerMap;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that represents bug spawning point on game field.
 * There are four such spots on the map, each one spawning different type of bugs.
 */

public class BugSpawn extends MapElement implements IDangerous {

    private final BugType spawnType;
    private int nextSpawnTime = -1;

    public BugSpawn(GameMap map, Position position, BugType spawnType, int nextSpawnTime) {
        super(map, position);
        this.spawnType = spawnType;
        this.nextSpawnTime = nextSpawnTime;
    }


    /**
     * Sets proper danger areas on DangerMap.
     * @param dangerMap - DangerMap to set danger areas on
     */
    @Override
    public void setDanger(DangerMap dangerMap) {

        // Sets danger for 2 rounds after bug spawn.
        if(nextSpawnTime > 0)
            for(int i = nextSpawnTime; i < nextSpawnTime + 2; i++)
                dangerMap.addDanger(this.position, i, Bug.DANGER_MEASURE*2 - 10);
    }
}
