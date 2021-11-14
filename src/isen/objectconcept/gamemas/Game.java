package isen.objectconcept.gamemas;

import isen.objectconcept.gamemas.entities.humanbeings.HumanBeing;
import isen.objectconcept.gamemas.entities.humanbeings.masters.Master;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.map.Cell;
import isen.objectconcept.gamemas.map.Map;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private final Random random = new Random();
    private final Map map = new Map();

    private static boolean gameRunning = true;
    private static EntityType winnerTeam;

    private int currentTurn = 0;
    private static final int maxTurns = 100;
    public static final int winMessagesNumber = 10;
    public static final int maxEnergyPoints = 20;


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
            System.out.println("\nMax number of turn reached. End of Game, no winner.");
        } else {
            System.out.println("\n***** "+ winnerTeam + " team won the Game! *****");
        }

        System.out.println("\n> GAME IS OVER\n");
    }

    /**
     * Play a game turn
     */
    private void playTurn() {
        // increase turns
        currentTurn++;
        System.out.println("> Turn "+ currentTurn);

        moveEntities();
        map.print();
    }

    /**
     * Move all entities in the Map and make them meet people around
     */
    public void moveEntities() {
        boolean validMove;

        ArrayList<Direction> availableDirections;
        ArrayList<Cell> attainableCells;

        Cell targetCell;
        int randomIndex;

        // Loop through cells
        for (HumanBeing humanBeing: map.getHumanBeings()) {
            System.out.println("# "+ humanBeing.getFigure() +"'s turn");

            // Check if HumanBeing is not a Master
            if (!(humanBeing instanceof Master)) {
                //System.out.println("humanBeing: "+ humanBeing);
                validMove = false;

                availableDirections = new ArrayList<>();

                // check energyPoints and baseMessage, decide to go forward or backward
                if (humanBeing.getEnergyPoints() > 10 || humanBeing.getBaseMessage() == null) {
                    //System.out.println("Go forward");
                    availableDirections.addAll(humanBeing.getForwardDirections());
                } else {
                    //System.out.println("Go backward");
                    availableDirections.addAll(humanBeing.getBackwardDirections());
                }

                attainableCells = getAttainableCellsAround(humanBeing, availableDirections);

                // Find an empty cell to move to
                do {
                    //System.out.println("AttainableCells: "+ attainableCells);

                    // check if entity can move around
                    if (attainableCells.size() > 0) {
                        randomIndex = random.nextInt(attainableCells.size());
                        targetCell = attainableCells.get(randomIndex);

                        // Check if targetCell is empty, otherwise invalid move
                        if (map.getEntityInCell(targetCell) == null) {
                            // move entity to targetCell
                            humanBeing.moveTo(targetCell.getX(), targetCell.getY());
                            System.out.println("  "+ humanBeing.getFigure() +" move to "+ humanBeing.getPosition());
                            humanBeing.decreaseEnergyPoints();

                            // Check for people around
                            //System.out.println("AttainableHBs: "+ getAttainableHumanBeingAround(humanBeing));
                            for (HumanBeing aroundHumanBeing: getAttainableHumanBeingAround(humanBeing)) {

                                System.out.print("@ "+ humanBeing.getFigure() +" meet "+ aroundHumanBeing.getFigure() +" - ");
                                //System.out.println(humanBeing.getPosition() +" meet "+ aroundHumanBeing.getPosition());
                                // Do MEET actions
                                humanBeing.meet(aroundHumanBeing);
                            }
                            validMove = true;
                        } else {
                            attainableCells.remove(randomIndex);
                        }
                    } else {
                        System.out.println("Entity can't move. Process to next entity...");
                        // entity can't move, process to next one
                        validMove = true;
                    }

                } while(!validMove); // Loop while move is invalid
            }
        }
    }

    /**
     * Return humanBeings around humanBeing
     * @param humanBeing to look around
     * @return a list of humanBeings
     */
    public ArrayList<HumanBeing> getAttainableHumanBeingAround(HumanBeing humanBeing) {
        ArrayList<HumanBeing> humanBeingsAround = new ArrayList<>();

        int x = humanBeing.getX();
        int y = humanBeing.getY();
        //System.out.println("#"+ humanBeing.getFigure() +" ("+ humanBeing.getPosition() +")");

        int selectedX, selectedY;
        // check every humanBeing from the game
        for (HumanBeing selectedHumanBeing: map.getHumanBeings()) {
            // verify not himself
            if (selectedHumanBeing != humanBeing) {
                selectedX = selectedHumanBeing.getX();
                selectedY = selectedHumanBeing.getY();

                // check if selectedHumanBeing is attainable
                if (x - 1 <= selectedX && selectedX <= x + 1 && y - 1 <= selectedY && selectedY <= y + 1) {
                    humanBeingsAround.add(selectedHumanBeing);
                    //System.out.println(selectedHumanBeing.getFigure() +"("+ selectedHumanBeing.getPosition() +") is around "+ humanBeing.getFigure());
                }
            }
        }
        return humanBeingsAround;
    }

    /**
     * Return cells around humanBeing, considering forwardDirections
     * @param humanBeing to look around
     * @param availableDirections allowed directions to move
     * @return a list of cells
     */
    public ArrayList<Cell> getAttainableCellsAround(HumanBeing humanBeing, ArrayList<Direction> availableDirections) {
        ArrayList<Cell> cellsAround = new ArrayList<>();

        for (Direction direction: availableDirections) {
            int targetX, targetY;
            // get targetX, targetY
            switch (direction) {
                case NW -> {
                    targetX = humanBeing.getX() - 1;
                    targetY = humanBeing.getY() - 1;
                }
                case N -> {
                    targetX = humanBeing.getX();
                    targetY = humanBeing.getY() - 1;
                }
                case NE -> {
                    targetX = humanBeing.getX() + 1;
                    targetY = humanBeing.getY() - 1;
                }
                case E -> {
                    targetX = humanBeing.getX() + 1;
                    targetY = humanBeing.getY();
                }
                case SE -> {
                    targetX = humanBeing.getX() + 1;
                    targetY = humanBeing.getY() + 1;
                }
                case S -> {
                    targetX = humanBeing.getX();
                    targetY = humanBeing.getY() + 1;
                }
                case SW -> {
                    targetX = humanBeing.getX() - 1;
                    targetY = humanBeing.getY() + 1;
                }
                case W -> {
                    targetX = humanBeing.getX() - 1;
                    targetY = humanBeing.getY();
                }
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }

            // check if cell exist in the map
            if (0 <= targetX && targetX < Map.columns && 0 <= targetY && targetY < Map.rows) {
                CellType targetCellType = Map.getCellType(targetX, targetY);

                // check if cell type is neutral or the same as the humanBeing
                if (targetCellType == CellType.NEUTRAL || targetCellType.toString() == humanBeing.getType().toString()) {
                    cellsAround.add(new Cell(targetX, targetY));
                }
            }
        }
        return cellsAround;
    }

    /* ----- GETTERS ----- */
    public static void setGameOver(EntityType entityType) {
        gameRunning = false;
        winnerTeam = entityType;
    }
}
