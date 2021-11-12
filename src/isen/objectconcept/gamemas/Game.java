package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();

    static boolean gameRunning = true;
    private static final int maxTurns = 20;
    private int currentTurn = 0;
    private static final int maxEnergyPoints = 4;

    public Game() {
    }

    /**
     * Start the game
     */
    public void start() {
        System.out.println("\n> GAME STARTED\n");


        // Looping through steps
        while (currentTurn < maxTurns && gameRunning) {
            // Play a turn
            playTurn();
        }

        System.out.println();
    }

    /**
     * Play a game turn
     */
    private void playTurn() {
        // increase turns
        currentTurn++;

        map.moveEntities();
        map.print();

        gameRunning = map.checkGameOver();
    }

    /* ----- GETTERS ----- */
    public Map getMap() {
        return map;
    }

    public static int getMaxEnergyPoints() {
        return maxEnergyPoints;
    }

    public static void setGameOver(String message) {
        gameRunning = false;
        System.out.println(message);
    }
}
