package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.field.Portal;

import java.util.ArrayList;

/**
 * Class used for converting String board representation into proper objects on GameMap.
 */

public class MapParser {
    private final GameMap map;

    public MapParser(GameMap map) {
        this.map = map;
    }


    /**
     * Fills walls array with proper values, creates portals list. Note that this needs to be done only at the beginning of the game
     * as the board layout doesn't change throughout the game.
     * @param boardRepresentation - String array, each element representing single field on the board.
     */
    public void initializeBoard(String[] boardRepresentation) {

        int width = this.map.getWidth();
        int height = this.map.getHeight();
        Boolean[][] walls = this.map.getWalls();
        ArrayList<Portal> portals = this.map.getPortals();

        if(boardRepresentation.length != width * height) {
            System.err.println(String.format("Board string representation length (%d) doesn't match with given width (%d) and height (%d)",
                    boardRepresentation.length, width, height));

            return;
        }

        for(int i = 0; i < boardRepresentation.length; i++) {
            walls[i % width][i / height] = boardRepresentation[i].equals("x");

            int index = boardRepresentation[i].indexOf("G");    // Searches for 'G' letter, which stands for portal field.

            if(index >= 0) {
                Position position = new Position(i % width, i / height);
                MoveDirection direction = (boardRepresentation[i].charAt(index + 1) == 'l') ? MoveDirection.LEFT : MoveDirection.RIGHT;
                portals.add(new Portal(this.map, position, direction));
            }
        }

        // Sets matching portals.
        portals.get(0).setMatchingPortal(portals.get(1));
        portals.get(1).setMatchingPortal(portals.get(0));
    }
}
