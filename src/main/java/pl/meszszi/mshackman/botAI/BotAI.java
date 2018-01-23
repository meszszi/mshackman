package main.java.pl.meszszi.mshackman.botAI;

import main.java.pl.meszszi.mshackman.MoveDirection;
import main.java.pl.meszszi.mshackman.engine.GameState;
import main.java.pl.meszszi.mshackman.engine.InputManager;
import main.java.pl.meszszi.mshackman.engine.MoveRequest;
import main.java.pl.meszszi.mshackman.engine.OutputManager;
import main.java.pl.meszszi.mshackman.maps.GameMap;

public abstract class BotAI {

    final String botName;
    final GameState gameState;
    final GameMap gameMap;
    final InputManager input;
    final OutputManager output;

    public BotAI(String botName) {
        this.botName = botName;
        gameState = new GameState();
        this.gameMap = new GameMap(gameState);
        input = new InputManager(gameState);
        output = new OutputManager();
    }

    public void run() {

        String[] action = input.readTillNextAction();

        if(action[1].equals("character"))
            output.setPlayerName(botName);

        boolean initialized = false;

        while((action = input.readTillNextAction()) != null) {

            if(!initialized) {
                this.gameMap.initialize();
                initialized = true;
            }

            if(action[1].equals("move")) {
                this.gameMap.update();
                output.makeMove(getNextMove());
            }

        }

    }

    protected abstract MoveRequest getNextMove();
}
