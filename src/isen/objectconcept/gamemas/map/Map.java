package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.*;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;

public class Map {

    private final Random random = new Random();
    // Probability in percentage that a neutral cell contains an obstacle at game initialization
    private final int obstacleProba = 10;
    private final int numberCreaturesPerRace = 4;

    private final int columns = 10;
    private final int rows = 10;
    private final int safeZoneColumns = 3;
    private final int safeZoneRows = 3;

    private final MapCell[][] cells = new MapCell[columns][rows];

    public Map() {
        initMap();
        initCreatures();
    }

    public MapCell[][] getCells() {
        return cells;
    }

    private void initMap() {
        // Create map cells
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                // SafeZone Elfs (haut gauche)
                if (x < safeZoneColumns && y < safeZoneRows) {
                    cells[x][y] = new MapCell(x, y, CellType.ELF);
                }
                // SafeZone Gobelins (haut droite)
                else if (x >= (columns - safeZoneColumns) && y < safeZoneRows) {
                    cells[x][y] = new MapCell(x, y, CellType.GOBLIN);
                }
                // SafeZone Humains (bas droite)
                else if (x >= (columns - safeZoneColumns) && y >= (rows - safeZoneRows)) {
                    cells[x][y] = new MapCell(x, y, CellType.HUMAN);
                }
                // SafeZone Orcs (bas gauche)
                else if (x < 3 && y >= (rows - safeZoneRows)) {
                    cells[x][y] = new MapCell(x, y, CellType.ORC);
                }
                // Neutral cell
                else {
                    MapCell newCell = new MapCell(x, y, CellType.NEUTRAL);

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

    private MapCell pickRandomEmptyCell(int rowStart, int rowEnd, int columnStart, int columnEnd) {
        ArrayList<MapCell> emptyCells = new ArrayList<>();

        for (int y = rowStart; y < rowEnd; y++) {
            for (int x = columnStart; x < columnEnd; x++) {
                MapCell cell = cells[x][y];

                if (cell.getEntity() == null || cell.getEntity().getType() == EntityType.EMPTY) {
                    emptyCells.add(cell);
                }
            }
        }
        MapCell randomCell = emptyCells.get(random.nextInt(emptyCells.size()));

        return cells[randomCell.getX()][randomCell.getY()];
    }

    /* ----- MAP PRINT ----- */
    public void print() {
        System.out.println("\nMAP: Entities");
        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                MapCell cell = cells[x][y];

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
                MapCell cell = cells[x][y];

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

    private void printSeparatorLigne() {
        // add a ligne separator
        System.out.println("." + "   .".repeat(columns));
    }
}
