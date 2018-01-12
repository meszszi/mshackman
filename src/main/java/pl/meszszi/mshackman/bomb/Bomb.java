package main.java.pl.meszszi.mshackman.bomb;

import main.java.pl.meszszi.mshackman.IDangerous;
import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

public class Bomb extends MapElement implements IDangerous{

    private final int timer;

    public Bomb(GameMap gameMap, Position position, int timer) {
        super(gameMap, position);
        this.timer = timer;
    }


    public int getTimer() {
        return this.timer;
    }
}
