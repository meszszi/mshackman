package main.java.pl.meszszi.mshackman.field;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that represents portals on game field.
 * There are two portals on the map.
 * When a player walks through a portal, he/she appears on the spot of the other one.
 */

public class Portal extends MapElement {
    private Portal matchingPortal;
    private final MoveDirection portalDirection;    // Direction in which player must move while standing on a portal
                                                    // in order to appear on the spot of the other one.

    public Portal(GameMap map, Position position, MoveDirection portalDirection) {
        super(map, position);
        this.portalDirection = portalDirection;
    }


    public void setMatchingPortal(Portal matchingPortal) {
        this.matchingPortal = matchingPortal;
    }


    public Portal getMatchingPortal() {
        return matchingPortal;
    }
}
