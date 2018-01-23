package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.IDangerous;
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


    private void reset() {
        for(int i = 0; i < width * height; i++)
            dangerBoard[i % width][i / width].reset();
    }


    public void addDanger(Position position, int time, int dangerMeasure) {
        if(time < MAX_PREDICTION)
            this.dangerBoard[position.getX()][position.getY()].addDanger(time, dangerMeasure);
    }


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

    @Override
    public String toString() {
        String xd = "";

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                int sum = 0;
                int time = -1;

                for (int ii = 0; ii < dangerBoard[j][i].dangerMeasure.length; ii++)
                    if(dangerBoard[j][i].dangerMeasure[ii] > 0) {
                        sum = dangerBoard[j][i].dangerMeasure[ii];
                        time = ii;
                        break;
                    }

                if(sum > 0)
                    xd += String.format("\t%d:%d", time, sum);

                else
                    xd += String.format("\t-");
            }
            xd += "\n";
        }

        return xd;
    }
}
