package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.IValuable;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.bomb.Bomb;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.GameState;
import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.items.BombItem;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Class used for storing information about all objects on the game field
 */

public class GameMap {

    private final GameState gameState;
    private final MapParser mapParser;

    private int width;
    private int height;

    private Boolean[][] walls; // Array representing game walls -> 0 stands for empty (accessible) field, 1 represents wall.
    private ArrayList<Player> players;
    private ArrayList<BombItem> bombItems;
    private ArrayList<CodeSnippet> codeSnippets;
    private ArrayList<Bug> bugs;
    private ArrayList<Bomb> bombs;
    private ArrayList<Portal> portals;


    public GameMap(GameState gameState) {

        this.gameState = gameState;
        this.mapParser = new MapParser(this);
    }


    /**
     * Initializes data about game field.
     */
    public void initialize() {
        this.width = gameState.getBoardWidth();
        this.height = gameState.getBoardHeight();

        this.walls = new Boolean[width][];
        for(int i = 0; i < width; i++)
            this.walls[i] = new Boolean[height];

        mapParser.initializeBoard(gameState.getBoardUpdate());

        this.players = new ArrayList<>();
        this.bombItems = new ArrayList<>();
        this.codeSnippets = new ArrayList<>();
        this.portals = new ArrayList<>();
        this.bugs = new ArrayList<>();
        this.bombs = new ArrayList<>();
        update();
    }


    /**
     * Updates info about all objects on the game field.
     */
    public void update() {
        mapParser.updateMapObjects(gameState.getBoardUpdate());
    }

    void setWidth(int width) {
        this.width = width;
    }

    void setHeight(int height) {
        this.height = height;
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

    void setPortals(ArrayList<Portal> portals) {
        this.portals = portals;
    }


    void setBombItems(ArrayList<BombItem> bombItems) {
        this.bombItems = bombItems;
    }

    void setCodeSnippets(ArrayList<CodeSnippet> codeSnippets) {
        this.codeSnippets = codeSnippets;
    }

    void setBombs(ArrayList<Bomb> bombs) {
        this.bombs = bombs;
    }


    public ArrayList<BombItem> getBombItems() {
        return bombItems;
    }

    public ArrayList<CodeSnippet> getCodeSnippets() {
        return codeSnippets;
    }

    /**
     * Creates array of all valid moves that can be made from given position.
     * @param position - starting position
     * @return array of moves from position that do not collide with any wall.
     */
    public MoveDirection[] getValidMoves(Position position) {
        LinkedList<MoveDirection> validMoves = new LinkedList<>();

        if(!this.walls[position.getX()][position.getY()]) {

            for(MoveDirection direction : MoveDirection.values()){

                Position nextPos = position.move(direction);

                if(isInsideMap(nextPos) && !this.walls[nextPos.getX()][nextPos.getY()])
                    validMoves.add(direction);
            }
        }

        return validMoves.toArray(new MoveDirection[validMoves.size()]);
    }


    /**
     * Retrieves Hero player from players list.
     * @return Hero player type object
     */
    public Player getHero() {
        for(Player player : players)
            if(player.getID() == gameState.getHeroID())
                return player;

        return null;
    }


    /**
     * Retrieves Enemy player from players list.
     * @return Enemy player type object
     */
    public Player getEnemy() {
        for(Player player : players)
            if(player.getID() == gameState.getEnemyID())
                return player;

        return null;
    }


    /**
     * Checks if given position lays inside the map.
     * @param position - position to check
     * @return true if positions is inside the map.
     */
    public boolean isInsideMap(Position position) {
        return position.getX() >= 0 && position.getX() < width && position.getY() >= 0 && position.getY() < height;
    }
}
