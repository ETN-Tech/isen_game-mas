package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();
    private final int maxTurns;
    private int currentTurn = 0;

    public Game(int maxTurns) {
        this.maxTurns = maxTurns;
    }

    public void start() {
        System.out.println("Game started");

        boolean gameRunning = true;

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
    }

    /* ----- GETTERS ----- */
    public Map getMap() {
        return map;
    }
}
