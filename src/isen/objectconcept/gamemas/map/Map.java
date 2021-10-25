package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.*;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;

public class Map {

    private Random random = new Random();
    // Probability in percentage that a neutral cell contains an obstacle at game initialization
    private int obstacleProba = 10;

    private int columns = 10;
    private int rows = 10;
    private int safeZoneColumns = 3;
    private int safeZoneRows = 3;

    private final MapCell[][] cells = new MapCell[columns][rows];

    public Map() {
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

    public MapCell[][] getCells() {
        return cells;
    }

    /* ----- MAP PRINT ----- */
    public void print() {
        System.out.println("\nMAP: Entities");
        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                MapCell cell = cells[x][y];

                if (cell.getEntity() == null) {
                    strMap.append(" |");
                } else if (cell.getEntity().getType() == EntityType.OBSTACLE) {
                    strMap.append("x|");
                } else if (cell.getEntity().getType() == EntityType.ELF) {
                    strMap.append("E|");
                } else if (cell.getEntity().getType() == EntityType.GOBLIN) {
                    strMap.append("G|");
                } else if (cell.getEntity().getType() == EntityType.HUMAN) {
                    strMap.append("H|");
                } else if (cell.getEntity().getType() == EntityType.ORC) {
                    strMap.append("O|");
                }

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
                    case ELF -> strMap.append("E|");
                    case GOBLIN -> strMap.append("G|");
                    case HUMAN -> strMap.append("H|");
                    case ORC -> strMap.append("O|");
                    case NEUTRAL -> {
                        if (cell.getEntity().getType() == EntityType.OBSTACLE) {
                            strMap.append("x|");
                        } else {
                            strMap.append(" |");
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
