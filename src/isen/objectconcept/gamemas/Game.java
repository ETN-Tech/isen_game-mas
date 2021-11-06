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

    public void start() {
        System.out.println("Game started");


        // Looping through steps
        while (currentTurn < maxTurns && gameRunning) {
            // Play a turn
            playTurn();
        }

        System.out.println();
    }

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

    public static void setGameOver(String message) {
        gameRunning = false;
        System.out.println(message);
    }
}
