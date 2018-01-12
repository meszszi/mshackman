package main.java.pl.meszszi.mshackman.engine;

/**
 * Class used for storing information about current game state.
 * This is slightly modified BotState class from Jim van Eeden's starterbot available at riddles.io.
 */

public class GameState {

    // Values that will not be changing during the game
    private int MAX_TIMEBANK;
    private int TIME_PER_MOVE;
    private int ROUNDS_NUMBER;
    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;

    // Values related to current round settings
    private int currentRound;
    private int timebank;
    private String[] boardUpdate;

    // Values related to our bot
    private int heroSnippetCount;
    private int heroBombCount;
    private int heroID;
    private String heroName;

    // Values related to our enemy
    private int enemySnippetCount;
    private int enemyBombCount;
    private int enemyID;
    private String enemyName;

    public GameState() {
        this.heroSnippetCount = 0;
        this.enemySnippetCount = 0;
        this.heroBombCount = 0;
        this.enemyBombCount = 0;
    }


    void setMaxTimebank(int value) {
        this.MAX_TIMEBANK = value;
    }

    void setTimePerMove(int value) {
        this.TIME_PER_MOVE = value;
    }

    void setMaxRounds(int value) {
        this.ROUNDS_NUMBER = value;
    }

    void setBoardWidth(int boardWidth) {
        this.BOARD_WIDTH = boardWidth;
    }

    void setBoardHeight(int boardHeight) {
        this.BOARD_HEIGHT = boardHeight;
    }



    void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    void setTimebank(int value) {
        this.timebank = value;
    }

    void setBoardUpdate(String boardUpdate) {
        this.boardUpdate = boardUpdate.split(",");
    }



    void setHeroSnippetCount(int heroSnippetCount) {
        this.heroSnippetCount = heroSnippetCount;
    }

    void setHeroBombCount(int heroBombCount) {
        this.heroBombCount = heroBombCount;
    }

    void setHeroID(int heroID) {
        this.heroID = heroID;
    }

    void setHeroName(String heroName) {
        this.heroName = heroName;
    }



    void setEnemySnippetCount(int enemySnippetCount) {
        this.enemySnippetCount = enemySnippetCount;
    }

    void setEnemyBombCount(int enemyBombCount) {
        this.enemyBombCount = enemyBombCount;
    }

    void setEnemyID(int enemyID) {
        this.enemyID = enemyID;
    }

    void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }




    public int getMaxTimebank() {
        return this.MAX_TIMEBANK;
    }

    public int getTimePerMove() {
        return this.TIME_PER_MOVE;
    }

    public int getRoundNumber() {
        return this.ROUNDS_NUMBER;
    }

    public int getBoardWidth() {
        return BOARD_WIDTH;
    }

    public int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }

    public int getTimebank() {
        return this.timebank;
    }

    public String[] getBoardUpdate() {
        return this.boardUpdate;
    }

    public int getHeroSnippetCount() {
        return heroSnippetCount;
    }

    public int getHeroBombCount() {
        return heroBombCount;
    }

    public int getHeroID() {
        return heroID;
    }

    public String getHeroName() {
        return heroName;
    }



    public int getEnemySnippetCount() {
        return enemySnippetCount;
    }

    public int getEnemyBombCount() {
        return enemyBombCount;
    }

    public int getEnemyID() {
        return enemyID;
    }

    public String getEnemyName() {
        return enemyName;
    }
}
