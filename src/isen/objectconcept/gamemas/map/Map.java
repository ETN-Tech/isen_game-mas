package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.Entity;
import isen.objectconcept.gamemas.entities.Obstacle;
import isen.objectconcept.gamemas.entities.humanbeings.*;
import isen.objectconcept.gamemas.entities.humanbeings.masters.*;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private final Random random = new Random();
    // Probability in percentage that a neutral cell contains an obstacle at game initialization
    private static final int obstacleProba = 5;
    private static final int numberCreaturesPerRace = 4;

    public static final int columns = 10;
    public static final int rows = 10;
    public static final int safeZoneColumns = 3;
    public static final int safeZoneRows = 3;

    private final Cell[][] cells = new Cell[columns][rows];

    private ArrayList<HumanBeing> humanBeings = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();

    public Map() {
        initMap();
        initCreatures();

        printCellsType();
        print();
    }

    /* ----- GETTERS ----- */

    public ArrayList<HumanBeing> getHumanBeings() {
        return humanBeings;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /* ------ INITIALIZATION ------ */
    private void initMap() {
        // Create map cells
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                // SafeZone Elfs (haut gauche)
                if (x < safeZoneColumns && y < safeZoneRows) {
                    cells[x][y] = new Cell(x, y, CellType.ELF);
                }
                // SafeZone Gobelins (haut droite)
                else if (x >= (columns - safeZoneColumns) && y < safeZoneRows) {
                    cells[x][y] = new Cell(x, y, CellType.GOBLIN);
                }
                // SafeZone Humains (bas droite)
                else if (x >= (columns - safeZoneColumns) && y >= (rows - safeZoneRows)) {
                    cells[x][y] = new Cell(x, y, CellType.HUMAN);
                }
                // SafeZone Orcs (bas gauche)
                else if (x < 3 && y >= (rows - safeZoneRows)) {
                    cells[x][y] = new Cell(x, y, CellType.ORC);
                }
                // Neutral cell
                else {
                    Cell newCell = new Cell(x, y, CellType.NEUTRAL);

                    // Add randomly an obstacle
                    int randomProba = random.nextInt(100) + 1;
                    if (randomProba < obstacleProba) {
                        obstacles.add(new Obstacle(newCell.getX(), newCell.getY()));
                    }
                    cells[x][y] = newCell;
                }
            }
        }
    }

    private void initCreatures() {
        // init masters
        MasterElf.getInstance().moveTo(0, 0);
        humanBeings.add(MasterElf.getInstance());

        MasterGoblin.getInstance().moveTo(rows - 1, 0);
        humanBeings.add(MasterGoblin.getInstance());

        MasterHuman.getInstance().moveTo(rows - 1, columns - 1);
        humanBeings.add(MasterHuman.getInstance());

        MasterOrc.getInstance().moveTo(0, columns - 1);
        humanBeings.add(MasterOrc.getInstance());

        // init creatures
        for (int i = 0; i < numberCreaturesPerRace - 1; i++) {
            Cell cell = pickRandomEmptyCell(0, safeZoneRows, 0, safeZoneColumns);
            humanBeings.add(new Elf(cell.getX(), cell.getY(),"E"+ (i + 1)));

            cell = pickRandomEmptyCell(0, safeZoneRows, columns - safeZoneColumns, columns);
            humanBeings.add(new Goblin(cell.getX(), cell.getY(), "G"+ (i + 1)));

            cell = pickRandomEmptyCell(rows - safeZoneRows, rows, columns - safeZoneColumns, columns);
            humanBeings.add(new Human(cell.getX(), cell.getY(), "H"+ (i + 1)));

            cell = pickRandomEmptyCell(rows - safeZoneRows, rows, 0, safeZoneColumns);
            humanBeings.add(new Orc(cell.getX(), cell.getY(), "O"+ (i + 1)));
        }

    }

    /**
     * Pick a random empty Cell in the specified area
     * @param rowStart area y start
     * @param rowEnd area y end
     * @param columnStart area x start
     * @param columnEnd area x end
     * @return a random Cell
     */
    private Cell pickRandomEmptyCell(int rowStart, int rowEnd, int columnStart, int columnEnd) {
        ArrayList<Cell> emptyCells = new ArrayList<>();

        for (int y = rowStart; y < rowEnd; y++) {
            for (int x = columnStart; x < columnEnd; x++) {
                Cell cell = cells[x][y];

                if (getEntityInCell(cell) == null) {
                    emptyCells.add(cell);
                }
            }
        }
        Cell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));

        return cells[randomCell.getX()][randomCell.getY()];
    }

    public Entity getEntityInCell(Cell cell) {
        Obstacle obstacle = getObstacleInCell(cell);
        if (obstacle != null) {
            return obstacle;
        }

        HumanBeing humanBeing = getHumanBeingInCell(cell);
        if (humanBeing != null) {
            return humanBeing;
        }
        return null;
    }

    public HumanBeing getHumanBeingInCell(Cell cell) {
        for (HumanBeing humanBeing: humanBeings) {
            // if humanBeing in this cell, return it
            if (humanBeing.getX() == cell.getX() && humanBeing.getY() == cell.getY()) {
                return humanBeing;
            }
        }
        return null;
    }

    public Obstacle getObstacleInCell(Cell cell) {
        for (Obstacle obstacle: obstacles) {
            // if humanBeing in this cell, return it
            if (obstacle.getX() == cell.getX() && obstacle.getY() == cell.getY()) {
                return obstacle;
            }
        }
        return null;
    }

    /* ----- UTILITY FUNCTIONS ----- */
    public static CellType getCellType(int x, int y) {
        // SafeZone Elfs (haut gauche)
        if (x < safeZoneColumns && y < safeZoneRows) {
            return CellType.ELF;
        }
        // SafeZone Gobelins (haut droite)
        else if (x >= (columns - safeZoneColumns) && y < safeZoneRows) {
            return CellType.GOBLIN;
        }
        // SafeZone Humains (bas droite)
        else if (x >= (columns - safeZoneColumns) && y >= (rows - safeZoneRows)) {
            return CellType.HUMAN;
        }
        // SafeZone Orcs (bas gauche)
        else if (x < 3 && y >= (rows - safeZoneRows)) {
            return CellType.ORC;
        }
        else {
            return CellType.NEUTRAL;
        }
    }

    /* ----- MAP PRINT ----- */
    public void print() {
        System.out.println("\nMAP: Entities");
        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = cells[x][y];
                Entity entity = getEntityInCell(cell);

                // check if entity exist in this cell
                if (entity != null){
                    strMap.append(" ").append(entity.getFigure()).append(" |");

                    switch (entity.getType()) {
                        case HUMAN -> System.out.print("\u001B[34m " + entity.getFigure() + " ");
                        case ORC -> System.out.print("\u001B[35m " + entity.getFigure() + " ");
                        case ELF -> System.out.print("\u001B[32m " + entity.getFigure() + " ");
                        case GOBLIN -> System.out.print("\u001B[33m " + entity.getFigure() + " ");
                        case OBSTACLE -> System.out.print("\u001B[31m " + " + ");
                    }
                    System.out.print("\u001B[0m");
                } else {
                    System.out.print("  . ");
                }

                // if end of map (width), print line
                if (cell.getX() == columns - 1) {
//                    System.out.println(strMap);

                    strMap = new StringBuilder("|");
                }
            }
            System.out.println();

        }
    }

    /* ----- DEBUG ----- */
    public void printCellsType() {
        System.out.println("\nMAP: Cells Type");
        System.out.println("---------------");

        StringBuilder strMap = new StringBuilder("|");

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = cells[x][y];

                switch (cell.getType()) {
                    case ELF -> strMap.append(" E |");
                    case GOBLIN -> strMap.append(" G |");
                    case HUMAN -> strMap.append(" H |");
                    case ORC -> strMap.append(" O |");
                    case NEUTRAL -> {
                        if (getEntityInCell(cell) != null) {
                            strMap.append(" x |");
                        } else {
                            strMap.append("   |");
                        }
                    }
                }

                // if end of map (width), print line
                if (cell.getX() == columns - 1) {
                    System.out.println(strMap);

                    strMap = new StringBuilder("|");
                }
            }
        }
    }
}
