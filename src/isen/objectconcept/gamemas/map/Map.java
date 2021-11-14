package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.Obstacle;
import isen.objectconcept.gamemas.entities.humanbeings.*;
import isen.objectconcept.gamemas.entities.humanbeings.masters.*;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private final Random random = new Random();
    // Probability in percentage that a neutral cell contains an obstacle at game initialization
    private static final int obstacleProba = 5;
    private static final int numberCreaturesPerRace = 5;

    private static final int columns = 12;
    private static final int rows = 12;
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
                    System.out.print(being.getColor_code()+being.getFigure());
                    being.move(this);
                    if (being instanceof Master masterEntity && being.getMessages().size() == 8) {
                        winners.add(being);
                    }
                }
                print();
                System.out.flush();
                Thread.sleep(10);
                step++;
            }
        }
        catch (InterruptedException ex){
            System.out.println(ex);
        }
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
                        case OBSTACLE:
                            System.out.print(cell.getEntity().getColor_code() + " + ");
                            break;
                        case EMPTY:
                            System.out.print("  . ");
                            break;
                        default:
                            System.out.print(cell.getEntity().getColor_code() + cell.getEntity().getFigure() + " ");
                    }
                    System.out.print(Color.DEFAULT_COLOR.toString());

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
