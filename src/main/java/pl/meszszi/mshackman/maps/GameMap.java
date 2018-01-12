package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.IValuable;
import main.java.pl.meszszi.mshackman.bomb.Bomb;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.GameState;
import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;

/**
 * Class used for storing information about all objects on the game field
 */

public class GameMap {

    private final GameState gameState;
    private final MapParser mapParser;

    private final int width;
    private final int height;

    private Boolean[][] walls; // Array representing game walls -> 0 stands for empty (accessible) field, 1 represents wall.
    private ArrayList<Player> players;
    private ArrayList<IValuable> items;
    private ArrayList<Bug> bugs;
    private ArrayList<Bomb> bombs;
    private ArrayList<Portal> portals;


    public GameMap(GameState gameState) {

        this.gameState = gameState;
        this.mapParser = new MapParser(this);
        this.width = gameState.getBoardWidth();
        this.height = gameState.getBoardHeight();

        walls = new Boolean[width][height];
    }


    /**
     * Initializes data about game field.
     */
    public void initialize() {
        mapParser.initializeBoard(gameState.getBoardUpdate());
        update();
    }


    /**
     * Updates info about all objects on the game field.
     */
    public void update() {
        mapParser.updateMapObjects(gameState.getBoardUpdate());
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


    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    void setBugs(ArrayList<Bug> bugs) {
        this.bugs = bugs;
    }


    void setItems(ArrayList<IValuable> items) {
        this.items = items;
    }

    void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }
}
