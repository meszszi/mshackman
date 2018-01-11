package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.IPrecious;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;

/**
 * Class used for storing information about all objects on the game field
 */

public class GameMap {

    private final int width;
    private final int height;

    private Boolean[][] walls; // Array representing game walls -> 0 stands for empty (accessible) field, 1 represents wall.
    private ArrayList<Player> players;
    private ArrayList<IPrecious> items;
    private ArrayList<Bug> bugs;
    private ArrayList<Portal> portals;


    public GameMap(int width, int height) {

        this.width = width;
        this.height = height;

        walls = new Boolean[width][height];
    }


    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }

    Boolean[][] getWalls() {
        return this.walls;
    }

    ArrayList<Portal> getPortals() {
        return this.portals;
    }

    /**
     * Getter for players.
     * @return this.players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }


    /**
     * Getter for bugs.
     * @return this.bugs.
     */
    public ArrayList<Bug> getBugs() {
        return this.bugs;
    }
}
