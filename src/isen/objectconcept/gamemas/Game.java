package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.entities.humanbeings.HumanBeing;
import isen.objectconcept.gamemas.entities.humanbeings.masters.Master;
import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.map.Map;

public class Game {
    
    private final Map map = new Map();

    static boolean gameRunning = true;
    public static final int winMessagesNumber = 8;
    private static final int maxEnergyPoints = 20;
    private static final int maxTurns = 100;

    public Game() {
    }

    /**
     * Start the game
     */
    public void start() {
        System.out.println("\n> GAME STARTED\n");
        int step = 1;
        try {
            while (map.getWinners().size() < 1 && step <= maxTurns) {
                // Simulation Starts
                System.out.println("\nStep: " + step);

                for (HumanBeing being : map.getPlayers()) {
                    System.out.print(being.getColor_code());
                    // Move entities
                    being.move(map);

                    if (being instanceof Master && being.getMessages().size() == 8) {
                        map.addWinners(being);
                    }

                }
                System.out.println(Color.DEFAULT_COLOR);
                map.print();
                System.out.flush();

                Thread.sleep(10);
                step++;
            }
        }
        catch (InterruptedException ex){
            System.out.println("\nInterrupted!");
            System.out.println(ex);
        }

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


    /* ----- GETTERS ----- */
    public Map getMap() {
        return map;
    }

    public static void setGameOver() {
        gameRunning = false;
    }
}
