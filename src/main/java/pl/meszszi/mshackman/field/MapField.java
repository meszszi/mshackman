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

    private boolean accessible;
    private ArrayList<ValidMove> validMoves;
    private ArrayList<ValidMove> nonPortalMoves;    // all valid moves for objects that cannot go through portals

    public MapField(GameMap map, Position position) {
        super(map, position);
        this.accessible = false;
        this.validMoves = new ArrayList<>();
        this.nonPortalMoves = new ArrayList<>();
    }


    /**
     * Sets isAccessible field to true.
     */
    public void setAsAccessible() {
        this.accessible = true;
    }


    /**
     * Checks if the field can be accessed by any character (player, bug or a bomb blast).
     * @return this.accessible
     */
    public boolean isAccessible() {
        return this.accessible;
    }


    // Functions that extend validMoves lists of both types (regular and nonPortal)
    public void extendValidMoves(ValidMove move) {
        this.validMoves.add(move);
    }
    public void extendNonPortalMoves(ValidMove move) {
        this.nonPortalMoves.add(move);
    }


    // Getters for validMoves lists.
    public ArrayList<ValidMove> getValidMoves() {
        return validMoves;
    }
    public ArrayList<ValidMove> getNonPortalMoves() {
        return nonPortalMoves;
    }

}
