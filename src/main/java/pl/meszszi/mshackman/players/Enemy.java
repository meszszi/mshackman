package main.java.pl.meszszi.mshackman.players;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that will represent our opponent.
 */

public class Enemy extends Player {

    public Enemy(GameMap map, Position position) {
        super(PlayerType.ENEMY, map, position);
    }
}
