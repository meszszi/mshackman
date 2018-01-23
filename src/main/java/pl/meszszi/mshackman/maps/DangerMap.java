package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.Position;
import main.java.pl.meszszi.mshackman.bomb.Bomb;
import main.java.pl.meszszi.mshackman.bugs.Bug;
import main.java.pl.meszszi.mshackman.field.BugSpawn;

import java.util.Arrays;

/**
 * Class used for representing danger areas on the board.
 */

public class DangerMap {

    private final int MAX_PREDICTION = 10;
    private final int width;
    private final int height;
    private final GameMap gameMap;
    private DangerField[][] dangerBoard;

    /**
     * Nested class representing fields potentially dangerous to hero.
     * Due to efficiency can simulate only MAX_PREDICTION moves forward.
     */

    private class DangerField {

        final int MAX_PREDICTION;
        int[] dangerMeasure;

        DangerField(int MAX_PREDICTION) {
            this.MAX_PREDICTION = MAX_PREDICTION;
            this.dangerMeasure = new int[MAX_PREDICTION];

            reset();
        }

        void reset() {
            Arrays.fill(dangerMeasure, 0);
        }

        void addDanger(int time, int measure) {
            this.dangerMeasure[time] += measure;
        }

        int getDanger(int time) {
            return this.dangerMeasure[time];
        }
    }

    public DangerMap(GameMap gameMap) {
        this.gameMap = gameMap;
        this.width = gameMap.getWidth();
        this.height = gameMap.getHeight();

        this.dangerBoard = new DangerField[width][height];

        for(int i = 0; i < width * height; i++)
            dangerBoard[i % width][i / width] = new DangerField(MAX_PREDICTION);
    }


    /**
     * Resets all the values on the board.
     */
    private void reset() {
        for(int i = 0; i < width * height; i++)
            dangerBoard[i % width][i / width].reset();
    }


    /**
     * Adds danger to given area at given time.
     * @param position - position of new danger area
     * @param time - time at which the field is potentially dangerous
     * @param dangerMeasure - measure of danger
     */
    public void addDanger(Position position, int time, int dangerMeasure) {
        if(time < MAX_PREDICTION)
            this.dangerBoard[position.getX()][position.getY()].addDanger(time, dangerMeasure);
    }


    /**
     * Gets danger on given position at given time.
     * @param position - position to look up
     * @param time - time to check at
     * @return - dangerMeasure specified by given parameters
     */
    public int getDanger(Position position, int time) {
        if(time < MAX_PREDICTION)
            return this.dangerBoard[position.getX()][position.getY()].getDanger(time);

        return 0;
    }

    void setAllDangerAreas() {

        reset();

        for(Bug bug : gameMap.getBugs())
            bug.setDanger(this);

        for(BugSpawn spawn : gameMap.getSpawns())
            spawn.setDanger(this);

        for(Bomb bomb : gameMap.getBombs())
            bomb.setDanger(this);
    }


    /**
     * Simple conversion to array-like string, used only for debugging.
     * @return - vague String representation of DangerMap
     */
    @Override
    public String toString() {
        String result = "";

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int danger = 0;
                int time = -1;

                for (int t = 0; t < dangerBoard[j][i].dangerMeasure.length; t++)
                    if(dangerBoard[j][i].dangerMeasure[t] > 0) {
                        danger = dangerBoard[j][i].dangerMeasure[t];
                        time = t;
                        break;
                    }

                if(danger > 0)
                    result += String.format("\t%d:%d", time, danger);

                else
                    result += String.format("\t-");
            }
            result += "\n";
        }

        return result;
    }
}
