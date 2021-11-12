package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.entities.Entity;
import isen.objectconcept.gamemas.entities.Empty;
import isen.objectconcept.gamemas.enums.CellType;

public class Cell {
    private int x;
    private int y;
    private CellType type;
    private Entity entity = new Empty();

    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellType getType() {
        return type;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Move this cell's entity to targetCell
     * @param targetCell to move entity to
     */
    public void moveEntityTo(Cell targetCell) {
        targetCell.setEntity(entity);
        entity = new Empty();
    }

    @Override
    public String toString() {
        return "Cell{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", entity=" + entity.getFigure() +
                '}';
    }
}
