package main.java.pl.meszszi.mshackman;

import main.java.pl.meszszi.mshackman.botAI.*;

/**
 * Main class.
 */

public class Main {
    public static void main(String[] args) {
        BotAI bot = new SmartBot("bixie");
        bot.run();
    }
}
