package main.java.pl.meszszi.mshackman.field;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.ValidMove;
import main.java.pl.meszszi.mshackman.maps.GameMap;

import java.util.ArrayList;

/**
 * Class used for representing accessibility properties of a field.
 */

public class MapField extends MapElement{

    private final boolean accessible;
    private ArrayList<ValidMove> validMoves;

    public MapField(GameMap map, Position position, boolean accessible) {
        super(map, position);
        this.accessible = accessible;
    }


    /**
     * Checks if the field can be accessed by any character (player or bug).
     * @return this.accessible
     */
    public boolean isAccessible() {
        return this.accessible;
    }


    /**
     * Gets all fields that can be accessed directly from this (assumes that this one is by definition accessible).
     * @return list of adjacent accessible MapFields.
     */
    public ArrayList<MapField> getAdjacentFields() {
        ArrayList<MapField> adjacent = new ArrayList<>();

        for(MoveDirection direction : MoveDirection.values()) {
            Position adjPosition = this.position.move(direction);
            //MapField field = this.map.getField(adjPosition);

            //if(field == null || !field.isAccessible())  // Skips the loop iteration if the field is outside the map or is inaccessible.
            //    continue;

        }

        return adjacent;
    }


    public void extendValidMoves(ValidMove move) {
        this.validMoves.add(move);
    }


    public ArrayList<ValidMove> getValidMoves() {
        return validMoves;
    }
}
