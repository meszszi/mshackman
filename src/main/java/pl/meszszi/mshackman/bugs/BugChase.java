package main.java.pl.meszszi.mshackman.bugs;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that represents Chase type of Bug.
 * Chase type sets its target to the closest (by Euclidean distance) player on the field.
 */

public class BugChase extends Bug{
    public BugChase(GameMap map, BugType type, Position position) {
        super(map, type);
        this.position = position;
    }
}
