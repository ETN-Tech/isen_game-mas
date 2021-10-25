package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.abstracts.CellEntity;
import isen.objectconcept.gamemas.enums.CellType;

public class MapCell {
    private int x;
    private int y;
    private CellType type;
    private CellEntity entity = null;

    public MapCell(int x, int y, CellType type) {
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
}
