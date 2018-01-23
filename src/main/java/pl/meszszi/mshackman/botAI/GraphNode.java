package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.Position;

public class GraphNode {

    final Position position;
    final Position source;
    final MoveDirection directionFromSource;
    final int distanceFromSource;

    GraphNode(Position position, Position source, MoveDirection directionFromSource, int distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
        this.source = source;
        this.position = position;
        this.directionFromSource = directionFromSource;
    }
}
