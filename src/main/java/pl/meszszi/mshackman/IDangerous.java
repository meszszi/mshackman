package main.java.pl.meszszi.mshackman;

import main.java.pl.meszszi.mshackman.maps.DangerMap;

/**
 * Interface for objects that may be potentially dangerous to our Hero.
 */

public interface IDangerous {
    void setDanger(DangerMap dangerMap);
}
