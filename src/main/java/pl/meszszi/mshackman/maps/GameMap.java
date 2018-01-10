package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.IDangerous;
import main.java.pl.meszszi.mshackman.IPrecious;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.field.MapField;
import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;

/**
 * Class used for storing information about all objects on the game field
 */

public class GameMap {

    private final int width;
    private final int height;

    private ArrayList<ArrayList<MapField>> field;
    private ArrayList<Player> players;
    private ArrayList<IPrecious> items;
    private ArrayList<Bug> bugs;
    private ArrayList<Portal> portals;


    /**
     * Constructs two dimensional array of FieldObjects and sets it as a 'field' value.
     * @param width - field width
     * @param height - field height
     */
    public GameMap(int width, int height) {

        this.width = width;
        this.height = height;

        field = new ArrayList<>(width);
        for(int i = 0; i < field.size(); i++)
            field.set(i, new ArrayList<MapField>(height));
    }


    private MapField getField(int x, int y) {

        if(x < 0 || x >= this.width || y < 0 || y >= this.height)
            return null;

        return this.field.get(x).get(y);
    }


    /**
     * Gets field specified by given position.
     * @param position - Position with proper coordinates
     * @return MapElement specified by position.
     */
    public MapField getField(Position position) {
        return this.getField(position.getX(), position.getY());
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

    public void updateField(String fieldStr) {

    }
}
