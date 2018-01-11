package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;

public class RandomBot extends BotAI {

    public RandomBot(String botName) {
        super(botName);
    }

    @Override
    protected MoveRequest getNextMove() {
        int rand = (int) (Math.random() % MoveDirection.values().length);
        MoveRequest move = new MoveRequest();
        move.setMoveDirection(MoveDirection.values()[rand]);
        return move;
    }
}
