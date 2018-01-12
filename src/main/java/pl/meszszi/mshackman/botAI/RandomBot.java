package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;

import java.util.Random;

public class RandomBot extends BotAI {

    private Random random;

    public RandomBot(String botName) {
        super(botName);
        this.random = new Random();
    }

    @Override
    protected MoveRequest getNextMove() {
        int rand = random.nextInt(MoveDirection.values().length);
        MoveRequest move = new MoveRequest();
        move.setMoveDirection(MoveDirection.values()[rand]);
        return move;
    }
}
