package main.java.pl.meszszi.mshackman.players;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that will represent our player
 */

public class Hero extends Player {

    public Hero(GameMap map, Position position) {
        super(PlayerType.HERO, map, position);
    }
}
