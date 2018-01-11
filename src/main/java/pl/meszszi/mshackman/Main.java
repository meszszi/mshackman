package main.java.pl.meszszi.mshackman;

import main.java.pl.meszszi.mshackman.botAI.BotAI;
import main.java.pl.meszszi.mshackman.botAI.RandomBot;

/**
 * Main class.
 */

public class Main {
    public static void main(String[] args) {
        BotAI random = new RandomBot("bixie");
        random.run();
    }
}
