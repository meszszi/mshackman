package main.java.pl.meszszi.mshackman.items;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;


/**
 * Class that represents collectible bombs on the game field (should not be confused with bombs planted by the players).
 */

public class BombItem extends MapElement {

    public BombItem(GameMap map, Position position) {
        super(map, position);
    }
}
