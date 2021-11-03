package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();
    private final int maxSteps;
    private int currentStep = 0;

    public Game(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public void start() {
        System.out.println("Game started");

        boolean gameRunning = true;

        // Looping through steps
        while (currentStep < maxSteps && gameRunning) {
            // increase steps
            currentStep++;

            // Play a turn
            map.playTurn();
        }

        System.out.println();
    }

    private void init() {
        // TODO init game...
    }

    /* ----- GETTERS ----- */
    public Map getMap() {
        return map;
    }
}
