package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.Obstacle;
import isen.objectconcept.gamemas.entities.humanbeings.*;
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
    private static final int obstacleProba = 5;
    private static final int numberCreaturesPerRace = 4;

    private static final int columns = 10;
    private static final int rows = 10;
    private static final int safeZoneColumns = 3;
    private static final int safeZoneRows = 3;
    private static final int maxTurns = 1000;


    private final Cell[][] cells = new Cell[columns][rows];

    ArrayList<HumanBeing> players = new ArrayList<>();
    ArrayList<HumanBeing> winners = new ArrayList<>();


    public Map() {
        initMap();
        initCreatures();
        int step = 1;
        try {
            while (winners.size() < 1 && step <= maxTurns) {
                // Simulation Starts
                System.out.println("Step: " + step);
                for (HumanBeing being : players) {
                    being.move(this);
                    if (being instanceof Master masterEntity && being.getMessages().size() == 4) {
                        winners.add(being);
                    }
                }
                print();
                System.out.flush();
                Thread.sleep(1000);
                step++;
            }
        }
        catch (InterruptedException ex){
            System.out.println(ex);
        }

//        System.out.println("Winners");
//        System.out.println("---------");
//        for (HumanBeing winner: winners){
//            System.out.println(winner.getFigure());
//            System.out.println(winner.getMessages());
//        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList<HumanBeing> getWinners() {
        return winners;
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
        Elf masterElf = MasterElf.getInstance();
        masterElf.setPosition(new Point(0,0));
        Goblin masterGoblin = MasterGoblin.getInstance();
        masterGoblin.setPosition(new Point(rows - 1,0));
        Human masterHuman = MasterHuman.getInstance();
        masterHuman.setPosition(new Point(rows - 1,columns - 1));
        Orc masterOrc = MasterOrc.getInstance();
        masterOrc.setPosition(new Point(0,columns - 1));
        players.add(masterElf);
        players.add(masterGoblin);
        players.add(masterHuman);
        players.add(masterOrc);

        // init creatures
        for (int i = 0; i < numberCreaturesPerRace - 1; i++) {
            Elf elf = new Elf("E"+ (i + 1));
            Goblin goblin = new Goblin("G"+ (i + 1));
            Human human = new Human("H"+ (i + 1));
            Orc orc = new Orc("O"+ (i + 1));
            players.add(elf);
            players.add(goblin);
            players.add(orc);
            players.add(human);



            Cell elfPosition = pickRandomEmptyCell(0, safeZoneRows, 0, safeZoneColumns);
            elfPosition.setEntity(elf);
            elf.setPosition(new Point(elfPosition.getX(), elfPosition.getY()));

            Cell goblinPosition = pickRandomEmptyCell(0, safeZoneRows, columns - safeZoneColumns, columns);
            goblinPosition.setEntity(goblin);
            goblin.setPosition(new Point(goblinPosition.getX(), goblinPosition.getY()));

            Cell humanPosition = pickRandomEmptyCell(rows - safeZoneRows, rows, columns - safeZoneColumns, columns);
            humanPosition.setEntity(elf);
            human.setPosition(new Point(humanPosition.getX(), humanPosition.getY()));

            Cell orcPosition = pickRandomEmptyCell(rows - safeZoneRows, rows, 0, safeZoneColumns);
            orcPosition.setEntity(elf);
            orc.setPosition(new Point(orcPosition.getX(), orcPosition.getY()));
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
//                    if (currentEntity.getEnergyPoints() > 10 || currentEntity.getBaseMessage() == null) {

                    if (currentEntity.getEnergyPoints() > 10 ) {
                        //System.out.println("Go forward");
                        System.out.println("Forward: "+currentEntity.getForwardDirections());

                        availableDirections.addAll(currentEntity.getForwardDirections());
                    } else {
                        //System.out.println("Go backward");
                        System.out.println("Back: "+currentEntity.getBackwardDirections());
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
                            if (targetCell.getEntity().getType() == EntityType.EMPTY && currentEntity.getEnergyPoints() > 0) {
                                // move entity to targetCell
                                currentCell.moveEntityTo(targetCell);
                                // if safezone, set energy_points to 20
                                if(targetCell.getType().toString() == currentCell.getEntity().getType().toString()){
                                    currentEntity.setEnergyPoints(20);
                                }
                                // reduce energy_points
                                else {
                                    currentEntity.decreaseEnergyPoints();
                                }
                                System.out.println(currentEntity.getFigure()+"- Energy Points: "+currentEntity.getEnergyPoints());


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
//        System.out.println("\nMAP: Entities");
//        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                Cell cell = cells[x][y];
                strMap.append(" ").append(cell.getEntity().getFigure()).append(" |");
                    switch (cell.getEntity().getType()) {
                        case HUMAN:
                            System.out.print("\u001B[34m " + cell.getEntity().getFigure() + " ");
                            break;
                        case ORC:
                            System.out.print("\u001B[35m " + cell.getEntity().getFigure() + " ");
                            break;
                        case ELF:
                            System.out.print("\u001B[32m " + cell.getEntity().getFigure() + " ");
                            break;
                        case GOBLIN:
                            System.out.print("\u001B[33m " + cell.getEntity().getFigure() + " ");
                            break;
                        case OBSTACLE:
                            System.out.print("\u001B[31m " + " + ");
                            break;
                        case EMPTY:
                            System.out.print("  . ");
                            break;
                    }
                    System.out.print("\u001B[0m");

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
