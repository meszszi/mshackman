package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.IValuable;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bomb.Bomb;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.engine.GameState;
import main.java.pl.meszszi.mshackman.field.BugSpawn;
import main.java.pl.meszszi.mshackman.field.MapField;
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
    private DangerMap dangerMap;

    private int width;
    private int height;

    private MapField[][] board; // Array representing game walls -> 0 stands for empty (accessible) field, 1 represents wall.
    private ArrayList<Player> players;
    private ArrayList<BombItem> bombItems;
    private ArrayList<CodeSnippet> codeSnippets;
    private ArrayList<Bug> bugs;
    private ArrayList<Bomb> bombs;
    private ArrayList<BugSpawn> spawns;

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
        this.dangerMap = new DangerMap(this);

        this.board = new MapField[width][height];

        for(int i = 0; i < width * height; i++)
            this.board[i % width][i / width] = new MapField(this, new Position(i % width, i / width));

        mapParser.initializeBoard(gameState.getBoardUpdate());

        this.players = new ArrayList<>();
        this.bombItems = new ArrayList<>();
        this.codeSnippets = new ArrayList<>();
        this.bugs = new ArrayList<>();
        this.bombs = new ArrayList<>();
        update();
    }


    public DangerMap updateDanger() {
        this.dangerMap.setAllDangerAreas();
        return this.dangerMap;
    }


    public int getDanger(Position position, int time) {
        return this.dangerMap.getDanger(position, time);
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

    MapField[][] getBoard() {
        return this.board;
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

    public ArrayList<BugSpawn> getSpawns () {
        return this.spawns;
    }


    void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    void setBugs(ArrayList<Bug> bugs) {
        this.bugs = bugs;
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

    void setSpawns(ArrayList<BugSpawn> spawns) {
        this.spawns = spawns;
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
    public ArrayList<ValidMove> getValidMoves(Position position) {
        return board[position.getX()][position.getY()].getValidMoves();
    }


    public ArrayList<ValidMove> getNonPortalMoves(Position position) {
        return board[position.getX()][position.getY()].getNonPortalMoves();
    }


    public ArrayList<Bomb> getBombs() {
        return bombs;
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


    public boolean isAccessible(Position position) {
        return isInsideMap(position) && board[position.getX()][position.getY()].isAccessible();
    }
}
