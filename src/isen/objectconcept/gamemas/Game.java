package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.entities.humanbeings.HumanBeing;
import isen.objectconcept.gamemas.entities.humanbeings.masters.Master;
import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();

    static boolean gameRunning = true;
    private int currentTurn = 0;
    public static final int winMessagesNumber = 4;
    private static final int maxEnergyPoints = 20;

    public Game() {
    }

    /**
     * Start the game
     */
    public void start() {
        System.out.println("\n> FINAL BOARD\n");
        map.print();
        System.out.println("Winners");
        System.out.println("---------");
        if(getMap().getWinners().size() > 0) {
            for (HumanBeing winner : getMap().getWinners()) {
                System.out.println(winner.getFigure());
                System.out.println("Messages: " + winner.getMessages());
            }
        }
        else{
            System.out.println("There was no winner, play again...");
        }

        System.out.println("\nGAME OVER\n");
    }

    /**
     * Play a game turn
     */
    private void playTurn() {
        // increase turns
        currentTurn++;

//        map.moveEntities();
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
