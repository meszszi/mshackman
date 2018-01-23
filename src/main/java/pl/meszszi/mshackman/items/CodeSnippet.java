package main.java.pl.meszszi.mshackman.items;

import main.java.pl.meszszi.mshackman.MapElement;
import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.maps.GameMap;

/**
 * Class that represents code snippets - items that players collect in order to win the game.
 */

public class CodeSnippet extends MapElement {

    public CodeSnippet(GameMap map, Position position) {
        super(map, position);
    }
}
