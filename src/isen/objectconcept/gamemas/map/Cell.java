package isen.objectconcept.gamemas.map;

import isen.objectconcept.gamemas.abstracts.CellEntity;
import isen.objectconcept.gamemas.entities.Empty;
import isen.objectconcept.gamemas.enums.CellType;
import isen.objectconcept.gamemas.enums.EntityType;

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

    public void entityMeet(Cell targetCell) {
        if (targetCell.getEntity().getType() == EntityType.EMPTY) {
            // move entity to targetCell
            targetCell.setEntity(entity);
            this.setEntity(new Empty());
        } else {
            // check allies
            EntityType ally;

            switch (entity.getType()) {
                case ELF -> ally = EntityType.HUMAN;
                case GOBLIN -> ally = EntityType.ORC;
                case HUMAN -> ally = EntityType.ELF;
                case ORC -> ally = EntityType.GOBLIN;
                default -> throw new IllegalStateException("Unexpected value: " + entity.getType());
            }

            // check if ally
            if (targetCell.getEntity().getType() == ally) {
                // ally, exchange message
                entity.exchangeMessageWith(targetCell.getEntity());
            } else {
                // enemy, fight
                entity.fightWith(targetCell.getEntity());
            }
        }
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
