package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.engine.GameState;
import main.java.pl.meszszi.mshackman.engine.InputManager;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;
import main.java.pl.meszszi.mshackman.engine.OutputManager;

public abstract class BotAI {
    private final String botName;
    protected final GameState gameState;
    protected final InputManager input;
    protected final OutputManager output;

    public BotAI(String botName) {
        this.botName = botName;
        gameState = new GameState();
        input = new InputManager(gameState);
        output = new OutputManager();
    }

    public void run() {

        String[] action = input.readTillNextAction();

        if(action[1].equals("character"))
            output.setPlayerName(botName);

        while((action = input.readTillNextAction()) != null) {

            if(action[1].equals("move"))
                output.makeMove(getNextMove());

        }

    }

    protected abstract MoveRequest getNextMove();
}
