package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.abstracts.CellEntity;
import isen.objectconcept.gamemas.entities.Empty;
import isen.objectconcept.gamemas.enums.CellType;

public class Cell {
    private int x;
    private int y;
    private CellType type;
    private CellEntity entity = new Empty();

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

    public CellEntity getEntity() {
        return entity;
    }

    public void setEntity(CellEntity entity) {
        this.entity = entity;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MapCell{" +
                "x=" + x +
                ", y=" + y +
                ", type=" + type +
                ", entity=" + entity +
                '}';
    }
}
