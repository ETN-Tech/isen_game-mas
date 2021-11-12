package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();

    static boolean gameRunning = true;
    private int currentTurn = 0;
    private static final int maxTurns = 100;
    public static final int winMessagesNumber = 10;
    private static final int maxEnergyPoints = 20;

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

        if (currentTurn >= maxTurns) {
            System.out.println("Max number of turn reached. End of Game, no winner.");
        }

        System.out.println("\nGAME OVER\n");
    }

    /**
     * Play a game turn
     */
    private void playTurn() {
        // increase turns
        currentTurn++;

        map.moveEntities();
        map.print();
    }

    /* ----- GETTERS ----- */
    public Map getMap() {
        return map;
    }

    public static int getMaxEnergyPoints() {
        return maxEnergyPoints;
    }

    public static void setGameOver() {
        gameRunning = false;
    }
}
