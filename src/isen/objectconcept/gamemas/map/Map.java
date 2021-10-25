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

    private int width = 10;
    private int height = 10;
    private int safeZoneWidth = 3;
    private int safeZoneHeight = 3;

    private ArrayList<MapCell> cells = new ArrayList<>();

    public Map() {
        // Create map cells
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                // SafeZone Elfs (haut gauche)
                if (x < safeZoneWidth && y < safeZoneHeight) {
                    cells.add(new MapCell(x, y, CellType.ELF));
                }
                // SafeZone Gobelins (haut droite)
                else if (x >= (width - safeZoneWidth) && y < safeZoneHeight) {
                    cells.add(new MapCell(x, y, CellType.GOBLIN));
                }
                // SafeZone Humains (bas droite)
                else if (x >= (width - safeZoneWidth) && y >= (height - safeZoneHeight)) {
                    cells.add(new MapCell(x, y, CellType.HUMAN));
                }
                // SafeZone Orcs (bas gauche)
                else if (x < 3 && y >= (height - safeZoneHeight)) {
                    cells.add(new MapCell(x, y, CellType.ORC));
                }
                // Neutral cell
                else {
                    MapCell newCell = new MapCell(x, y, CellType.NEUTRAL);

                    // Add randomly an obstacle
                    int randomProba = random.nextInt(100) + 1;
                    if (randomProba < obstacleProba) {
                        newCell.setEntity(new Obstacle());
                    }

                    cells.add(newCell);
                }
            }
        }
    }

    public ArrayList<MapCell> getCells() {
        return cells;
    }

    /* ----- MAP PRINT ----- */
    public void print() {
        System.out.println("\nMAP: Entities");
        System.out.println("-------------");

        StringBuilder strMap = new StringBuilder("|");

        for (MapCell cell: cells) {
            if (cell.getEntity() == null) {
                strMap.append(" |");
            }
            else if (cell.getEntity().getType() == EntityType.OBSTACLE) {
                strMap.append("x|");
            }
            else if (cell.getEntity().getType() == EntityType.ELF) {
                strMap.append("E|");
            }
            else if (cell.getEntity().getType() == EntityType.GOBLIN) {
                strMap.append("G|");
            }
            else if (cell.getEntity().getType() == EntityType.HUMAN) {
                strMap.append("H|");
            }
            else if (cell.getEntity().getType() == EntityType.ORC) {
                strMap.append("O|");
            }

            // if end of map (width), print line
            if (cell.getX() == width - 1) {
                System.out.println(strMap);
                strMap = new StringBuilder("|");
            }
        }
    }

    /* ----- DEBUG ----- */
    public void printCellsType() {
        System.out.println("\nMAP: Cells Type");
        System.out.println("---------------");

        StringBuilder strMap = new StringBuilder("|");

        for (MapCell cell: cells) {
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
            if (cell.getX() == width - 1) {
                System.out.println(strMap);
                strMap = new StringBuilder("|");
            }
        }
    }
}
