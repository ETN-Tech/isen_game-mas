package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.map.Map;
import isen.objectconcept.gamemas.map.MapCell;

public class Game {
    
    private final Map map = new Map();
    private final int maxSteps;
    private int currectStep = 0;

    public Game(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    public void start() {
        System.out.println("Game started");

        boolean gameRunning = true;

        // Looping through steps
        while (currectStep < maxSteps && gameRunning) {
            // increase steps
            currectStep++;

            // TODO do things...
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
