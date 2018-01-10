package main.java.pl.meszszi.mshackman;

import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class used to represent objects that create the game field (walls, portals, empty fields, bug spawns)
 * as well as objects present on the field (items, players, bugs).
 */

public abstract class MapElement {

    protected final GameMap map;
    protected final Position position;

    public MapElement(GameMap map, Position position) {
        this.map = map;
        this.position = position;
    }


    /**
     * Getter for position.
     * @return this position
     */
    public Position getPosition() {
        return this.position;
    }
}
