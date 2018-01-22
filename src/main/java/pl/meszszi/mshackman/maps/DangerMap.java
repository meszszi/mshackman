package main.java.pl.meszszi.mshackman.maps;

import main.java.pl.meszszi.mshackman.Position;

import java.util.Arrays;

/**
 * Class used for representing danger areas on the board.
 */

public class DangerMap {

    private final int MAX_PREDICTION = 10;
    private final int width;
    private final int height;
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

    public DangerMap(int width, int height) {
        this.width = width;
        this.height = height;

        this.dangerBoard = new DangerField[width][height];

        for(int i = 0; i < width * height; i++)
            dangerBoard[i % width][i / width] = new DangerField(MAX_PREDICTION);
    }


    public void reset() {
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
}
