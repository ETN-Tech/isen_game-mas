package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.Game;
import isen.objectconcept.gamemas.entities.humanbeings.HumanBeing;
import isen.objectconcept.gamemas.entities.*;
import isen.objectconcept.gamemas.entities.humanbeings.Elf;
import isen.objectconcept.gamemas.entities.humanbeings.Goblin;
import isen.objectconcept.gamemas.entities.humanbeings.Human;
import isen.objectconcept.gamemas.entities.humanbeings.Orc;
import isen.objectconcept.gamemas.entities.humanbeings.masters.*;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {

    private final Random random = new Random();
    // Probability in percentage that a neutral cell contains an obstacle at game initialization
    private static final int obstacleProba = 10;
    private static final int numberCreaturesPerRace = 4;

    private static final int columns = 10;
    private static final int rows = 10;
    private static final int safeZoneColumns = 3;
    private static final int safeZoneRows = 3;

    private final Cell[][] cells = new Cell[columns][rows];

    public Map() {
        initMap();
        initCreatures();

        printCellsType();
        print();
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
                        newCell.setEntity(new Obstacle());
                    }

                    cells[x][y] = newCell;
                }
            }
        }
    }

    private void initCreatures() {
        // init masters
        cells[0][0].setEntity(new MasterElf());
        cells[rows - 1][0].setEntity(new MasterGoblin());
        cells[rows - 1][columns - 1].setEntity(new MasterHuman());
        cells[0][columns - 1].setEntity(new MasterOrc());

        // init creatures
        for (int i = 0; i < numberCreaturesPerRace; i++) {
            pickRandomEmptyCell(0, safeZoneRows, 0, safeZoneColumns)
                    .setEntity(new Elf("E"+ (i + 1)));
            pickRandomEmptyCell(0, safeZoneRows, columns - safeZoneColumns, columns)
                    .setEntity(new Goblin("G"+ (i + 1)));
            pickRandomEmptyCell(rows - safeZoneRows, rows, columns - safeZoneColumns, columns)
                    .setEntity(new Human("H"+ (i + 1)));
            pickRandomEmptyCell(rows - safeZoneRows, rows, 0, safeZoneColumns)
                    .setEntity(new Orc("O"+ (i + 1)));
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

                if (cell.getEntity() == null || cell.getEntity().getType() == EntityType.EMPTY) {
                    emptyCells.add(cell);
                }
            }
        }
        Cell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));

        return cells[randomCell.getX()][randomCell.getY()];
    }

    /* ----- GAME MANAGEMENT ----- */

    /**
     * Move all entities in the Map and make them meet people around
     */
    public void moveEntities() {

        // Loop through cells
        for (Cell[] cellsx: cells) {
            for (Cell currentCell: cellsx) {
                //System.out.println("CurrentCell: "+ currentCell);

                // Check if cell contains a HumanBeing that is not a Master
                if (currentCell.getEntity() instanceof HumanBeing currentEntity && !(currentEntity instanceof Master)) {
                    //System.out.println("CurrentEntity: "+ currentEntity);
                    boolean validMove = false;

                    ArrayList<Direction> availableDirections = new ArrayList<>();

                    // check energyPoints and baseMessage, decide to go forward or backward
                    if (currentEntity.getEnergyPoints() > 10 || currentEntity.getBaseMessage() == null) {
                        //System.out.println("Go forward");
                        availableDirections.addAll(currentEntity.getForwardDirections());
                    } else {
                        //System.out.println("Go backward");
                        availableDirections.addAll(currentEntity.getBackwardDirections());
                    }

                    ArrayList<Cell> attainableCells = getAttainableCellsAround(currentCell, availableDirections);
                    Cell targetCell;

                    // Find an empty cell to move to
                    do {
                        //System.out.println("AttainableCells: "+ attainableCells);

                        // check if entity can move around
                        if (attainableCells.size() > 0) {
                            int randomIndex = random.nextInt(attainableCells.size());
                            targetCell = attainableCells.get(randomIndex);

                            // Check if targetCell is empty, otherwise invalid move
                            if (targetCell.getEntity().getType() == EntityType.EMPTY) {
                                // move entity to targetCell
                                currentCell.moveEntityTo(targetCell);
                                currentEntity.decreaseEnergyPoints();

                                // Check for people around
                                for (Cell aroundCell: getAttainableCellsAround(targetCell)) {
                                    // check if aroundCell has a HumanBeing inside to meet
                                    if (aroundCell.getEntity() instanceof HumanBeing aroundEntity) {
                                        currentEntity.meet(aroundEntity);
                                    }
                                }

                                validMove = true;
                            } else {
                                attainableCells.remove(randomIndex);
                            }
                        } else {
                            System.out.println("Entity can't move. Process next cell");
                            // entity can't move, process next cell
                            validMove = true;
                        }

                    } while(!validMove); // Loop while move is invalid
                }
            }
        }
    }

    /**
     * Return cells around currentCell attainable by its entity
     * @param currentCell cell to look around
     * @return a list of cells
     */
    public ArrayList<Cell> getAttainableCellsAround(Cell currentCell) {
        return getAttainableCellsAround(currentCell, new ArrayList<>(List.of(Direction.values())));
    }

    /**
     * Return cells around currentCell attainable by its entity, considering forwardDirections
     * @param currentCell cell to look around
     * @param availableDirections allowed directions to move
     * @return a list of cells
     */
    public ArrayList<Cell> getAttainableCellsAround(Cell currentCell, ArrayList<Direction> availableDirections) {
        ArrayList<Cell> cellsAround = new ArrayList<>();

        for (Direction direction: availableDirections) {
            int targetX, targetY;
            // get targetX, targetY
            switch (direction) {
                case NW -> {
                    targetX = currentCell.getX() - 1;
                    targetY = currentCell.getY() - 1;
                }
                case N -> {
                    targetX = currentCell.getX();
                    targetY = currentCell.getY() - 1;
                }
                case NE -> {
                    targetX = currentCell.getX() + 1;
                    targetY = currentCell.getY() - 1;
                }
                case E -> {
                    targetX = currentCell.getX() + 1;
                    targetY = currentCell.getY();
                }
                case SE -> {
                    targetX = currentCell.getX() + 1;
                    targetY = currentCell.getY() + 1;
                }
                case S -> {
                    targetX = currentCell.getX();
                    targetY = currentCell.getY() + 1;
                }
                case SW -> {
                    targetX = currentCell.getX() - 1;
                    targetY = currentCell.getY() + 1;
                }
                case W -> {
                    targetX = currentCell.getX() - 1;
                    targetY = currentCell.getY();
                }
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            }

            // check if cell exist in the map
            if (0 <= targetX && targetX < columns && 0 <= targetY && targetY < rows) {
                Cell targetCell = cells[targetX][targetY];

                // check if cell type is neutral or the same as the currentCell
                if (targetCell.getType() == CellType.NEUTRAL || targetCell.getType() == currentCell.getType()) {
                    cellsAround.add(targetCell);
                }
            }
        }
        return cellsAround;
    }

    /* ----- MAP PRINT ----- */
    public void print() {
        System.out.println("\nMAP: Entities");
        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = cells[x][y];

                strMap.append(" ").append(cell.getEntity().getFigure()).append(" |");

                // if end of map (width), print line
                if (cell.getX() == columns - 1) {
                    System.out.println(strMap);

                    strMap = new StringBuilder("|");
                }
            }
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
                        if (cell.getEntity() != null && cell.getEntity().getType() == EntityType.OBSTACLE) {
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
