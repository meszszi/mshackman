package main.java.pl.meszszi.mshackman.engine;

import java.util.Scanner;

/**
 * Class used for managing signals from game engine to bot.
 * This is slightly modified BotParser class from Jim van Eeden's starterbot available at riddles.io.
 */

public class InputManager {

    private final Scanner scanner;
    private final GameState gameState;

    public InputManager(GameState gameState) {
        this.scanner = new Scanner(System.in);
        this.gameState = gameState;
    }


    /**
     * Reads and parses input properly until an action type String is read.
     * @return String line containing action info.
     */
    public String[] readTillNextAction (){

        while (scanner.hasNextLine()) {

            String line = scanner.nextLine();

            if (line.length() == 0) continue;

            String[] parts = line.split(" ");

            switch (parts[0]) {

                case "settings":
                    parseSettings(parts[1], parts[2]);
                    break;

                case "update":
                    if (parts[1].equals("game")) {
                        parseGameUpdates(parts[2], parts[3]);
                    }
                    else {
                        parsePlayerUpdates(parts[1], parts[2], parts[3]);
                    }
                    break;

                case "action":
                    return parts;

                default:
                    System.err.println("Unknown command");
                    break;
            }
        }

        return null;
    }


    /**
     * Parses all the game settings given by the engine
     * @param key Type of setting given
     * @param value Value
     */
    private void parseSettings(String key, String value) {
        try {
            switch(key) {

                case "timebank":
                    int time = Integer.parseInt(value);
                    this.gameState.setMaxTimebank(time);
                    this.gameState.setTimebank(time);
                    break;

                case "time_per_move":
                    this.gameState.setTimePerMove(Integer.parseInt(value));
                    break;

                case "player_names":
                    String[] playerNames = value.split(",");
                    this.gameState.setHeroName(playerNames[0]);
                    this.gameState.setEnemyName(playerNames[1]);
                    break;

                case "your_bot":
                    if(!this.gameState.getHeroName().equals(value)) {
                        this.gameState.setEnemyName(this.gameState.getHeroName());
                        this.gameState.setHeroName(value);
                    }
                    break;

                case "your_botid":
                    int heroID = Integer.parseInt(value);
                    int enemyID = 1 - heroID;
                    this.gameState.setHeroID(heroID);
                    this.gameState.setEnemyID(enemyID);
                    break;

                case "field_width":
                    this.gameState.setBoardWidth(Integer.parseInt(value));
                    break;

                case "field_height":
                    this.gameState.setBoardHeight(Integer.parseInt(value));
                    break;

                case "max_rounds":
                    this.gameState.setMaxRounds(Integer.parseInt(value));
                    break;

                default:
                    System.err.println(String.format(
                            "Cannot parse settings input with key '%s'", key));
            }
        }
        catch (Exception e) {

            System.err.println(String.format(
                    "Cannot parse settings value '%s' for key '%s'", value, key));

            e.printStackTrace();
        }
    }


    /**
     * Parse data about the game given by the engine
     * @param key Type of game data given
     * @param value Value
     */
    private void parseGameUpdates(String key, String value) {
        try {
            switch(key) {
                case "round":
                    this.gameState.setCurrentRound(Integer.parseInt(value));
                    break;

                case "field":
                    this.gameState.setBoardUpdate(value);
                    break;

                default:
                    System.err.println(String.format(
                            "Cannot parse game data input with key '%s'", key));
            }
        }
        catch (Exception e) {

            System.err.println(String.format(
                    "Cannot parse game data value '%s' for key '%s'", value, key));

            e.printStackTrace();
        }
    }


    /**
     * Parse data about given player that the engine has sent
     * @param playerName Player name that this data is about
     * @param key Type of player data given
     * @param value Value
     */
    private void parsePlayerUpdates(String playerName, String key, String value) {

        try {
            if(this.gameState.getHeroName().equals(playerName)) {
                switch(key) {

                    case "bombs":
                        this.gameState.setHeroBombCount(Integer.parseInt(value));
                        break;

                    case "snippets":
                        this.gameState.setHeroSnippetCount(Integer.parseInt(value));
                        break;

                    default:
                        System.err.println(String.format(
                                "Cannot parse %s data input with key '%s'", playerName, key));
                }
            }

            else {
                switch(key) {

                    case "bombs":
                        this.gameState.setEnemyBombCount(Integer.parseInt(value));
                        break;

                    case "snippets":
                        this.gameState.setEnemySnippetCount(Integer.parseInt(value));
                        break;

                    default:
                        System.err.println(String.format(
                                "Cannot parse %s data input with key '%s'", playerName, key));
                }
            }

        }
        catch (Exception e) {

            System.err.println(String.format(
                    "Cannot parse %s data value '%s' for key '%s'", playerName, value, key));

            e.printStackTrace();
        }
    }
}
