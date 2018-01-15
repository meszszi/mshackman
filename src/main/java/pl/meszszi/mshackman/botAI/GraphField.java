package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;

/**
 * Class used for representing nodes in a graph (useful for path finding graph algorithms).
 */

public class GraphField {

    private int distanceFromSource;
    private final MoveDirection directionFromSource;
    private final Position previousPosition;

    GraphField(int distanceFromSource, MoveDirection directionFromSource, Position previousPosition) {
        this.distanceFromSource = distanceFromSource;
        this.directionFromSource = directionFromSource;
        this.previousPosition = previousPosition;
    }

    int getDistanceFromSource() {
        return distanceFromSource;
    }

    MoveDirection getDirectionFromSource() {
        return directionFromSource;
    }

    Position getPreviousPosition() {
        return previousPosition;
    }

    void setDistanceFromSource(int distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }
}
