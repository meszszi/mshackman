package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.bomb.Bomb;
import main.java.pl.meszszi.mshackman.bugs.*;
import main.java.pl.meszszi.mshackman.field.BugSpawn;
import main.java.pl.meszszi.mshackman.field.MapField;
import main.java.pl.meszszi.mshackman.field.Portal;
import main.java.pl.meszszi.mshackman.items.BombItem;
import main.java.pl.meszszi.mshackman.items.CodeSnippet;
import main.java.pl.meszszi.mshackman.players.Player;

import java.util.ArrayList;

/**
 * Class used for converting String board representation into proper objects on GameMap.
 */

public class MapParser {

    private final GameMap map;

    MapParser(GameMap map) {
        this.map = map;
    }


    /**
     * Fills board array with proper values, creates portals list. Note that this needs to be done only at the beginning of the game
     * as the board layout doesn't change throughout the game.
     * @param boardRepresentation - String array, each element representing single field on the board.
     */
    void initializeBoard(String[] boardRepresentation) {

        int width = this.map.getWidth();
        int height = this.map.getHeight();
        MapField[][] board = this.map.getBoard();
        ArrayList<Portal> portals = new ArrayList<>();

        if(boardRepresentation.length != width * height) {
            System.err.println(String.format("Board string representation length (%d) doesn't match with given width (%d) and height (%d)",
                    boardRepresentation.length, width, height));

            return;
        }

        // Searches for portals on the map.
        for(int i = 0; i < boardRepresentation.length; i++) {

            if(!boardRepresentation[i].equals("x"))
                board[i % width][i / width].setAsAccessible();

            int index = boardRepresentation[i].indexOf("G");    // Searches for 'G' letter, which stands for portal field.

            if(index >= 0) {
                Position position = new Position(i % width, i / width);
                MoveDirection direction = (boardRepresentation[i].charAt(index + 1) == 'l') ? MoveDirection.LEFT : MoveDirection.RIGHT;
                portals.add(new Portal(this.map, position, direction));
            }
        }

        // Sets matching portals.
        portals.get(0).setMatchingPortal(portals.get(1));
        portals.get(1).setMatchingPortal(portals.get(0));

        // Sets all validMoves for all fields on the board.
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++)
                if(board[i][j].isAccessible())
                    for(MoveDirection direction : MoveDirection.values()) {

                        Position position = (new Position(i, j)).move(direction);

                        if(!map.isInsideMap(position))
                            continue;

                        MapField field = board[position.getX()][position.getY()];

                        if(field.isAccessible()) {
                            board[i][j].extendValidMoves(new ValidMove(position, direction));
                            board[i][j].extendNonPortalMoves(new ValidMove(position, direction));
                        }
                    }

        // Sets additional portal moves for regular validMoves lists of each field.
        for(Portal portal : portals)
            board[portal.getPosition().getX()][portal.getPosition().getY()].extendValidMoves(
                    new ValidMove(portal.getMatchingPortal().getPosition(), portal.getPortalDirection()));
    }


    /**
     * Updates all changeable objects present on gameMap (players, items, bugs and bug spawns).
     * @param boardRepresentation - String array, each element containing info about single field.
     */
    void updateMapObjects(String[] boardRepresentation) {

        int width = this.map.getWidth();
        int height = this.map.getHeight();

        if(boardRepresentation.length != width * height) {
            System.err.println(String.format("Board string representation length (%d) doesn't match with given width (%d) and height (%d)",
                    boardRepresentation.length, width, height));

            return;
        }

        ArrayList<Player> players = new ArrayList<>();
        ArrayList<BombItem> bombItems = new ArrayList<>();
        ArrayList<CodeSnippet> codeSnippets = new ArrayList<>();
        ArrayList<Bug> bugs = new ArrayList<>();
        ArrayList<BugSpawn> spawns = new ArrayList<>();
        ArrayList<Bomb> bombs = new ArrayList<>();

        for(int i = 0; i < boardRepresentation.length; i++) {

            Position position = new Position(i % width, i / width);

            String[] fieldArray = boardRepresentation[i].split(";");

            for(String field : fieldArray) {
                int playerIndex = field.indexOf("P");
                if(playerIndex >= 0) {
                    int playerID = Character.getNumericValue(field.charAt(playerIndex + 1));

                    players.add(new Player(map, position, playerID));
                }

                int bugIndex = field.indexOf("E");
                if(bugIndex >= 0) {
                    switch(field.charAt(bugIndex + 1)) {

                        case '0':
                            bugs.add(new BugChase(map, position));
                            break;

                        case '1':
                            bugs.add(new BugPredict(map, position));
                            break;

                        case '2':
                            bugs.add(new BugLever(map, position));
                            break;

                        case '3':
                            bugs.add(new BugFarChase(map, position));
                            break;
                    }
                }

                int spawnIndex = field.indexOf("S");
                if(spawnIndex >= 0) {
                    int time = -1;
                    if(field.length() > spawnIndex + 1 && Character.isDigit(field.charAt(spawnIndex + 1)))
                        time = Integer.parseInt(field.substring(spawnIndex + 1, spawnIndex + 2));

                    BugType bugType = BugType.values()[spawns.size()];

                    spawns.add(new BugSpawn(map, position, bugType, time));
                }

                int bombIndex = field.indexOf("B");
                if(bombIndex >= 0) {

                    if(field.length() > bombIndex + 1 && Character.isDigit(field.charAt(bombIndex + 1))) {
                        int time = Character.getNumericValue(field.charAt(bombIndex + 1));
                        bombs.add(new Bomb(map, position, time));
                    }

                    else {
                        bombItems.add(new BombItem(map, position));
                    }
                }

                int snippetIndex = field.indexOf("C");
                if(snippetIndex >= 0) {
                    codeSnippets.add(new CodeSnippet(map, position));
                }
            }
        }

        // Updates facingDirections for players and bugs.
        updateBugsInfo(bugs);
        updatePlayersInfo(players);

        // Saves updates lists to gameMap.
        map.setPlayers(players);
        map.setBugs(bugs);
        map.setBombItems(bombItems);
        map.setCodeSnippets(codeSnippets);
        map.setBombs(bombs);
        map.setSpawns(spawns);
    }


    /**
     * Sets MoveDirection fields for players on map according to current position in relation to previous position.
     * @param newPlayers - array of new players on the map
     */
    private void updatePlayersInfo(ArrayList<Player> newPlayers) {
        ArrayList<Player> oldPlayers = map.getPlayers();

        for(Player newPlayer : newPlayers) {
            for(Player oldPlayer : oldPlayers)
                if(newPlayer.getID() == oldPlayer.getID())
                    newPlayer.setFacingDirection(newPlayer.getPosition().getDirectionFrom(oldPlayer.getPosition()));
        }
    }


    /**
     * Sets MoveDirection fields for bugs on map according to previous positions.
     * @param newBugs - array of new bugs on the map
     */
    private void updateBugsInfo(ArrayList<Bug> newBugs) {
        ArrayList<Bug> oldBugs = map.getBugs();

        for(Bug newBug : newBugs) {
            for(Bug oldBug : oldBugs)
                if(newBug.getType() == oldBug.getType() && newBug.getPosition().getDirectionFrom(oldBug.getPosition()) != null)
                    newBug.setFacingDirection(newBug.getPosition().getDirectionFrom(oldBug.getPosition()));
        }
    }
}
