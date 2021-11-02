package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class CellEntity {
    protected EntityType type;
    protected String figure;

    public CellEntity(EntityType type, String figure) {
        this.type = type;
        this.figure = figure;
    }

    public EntityType getType() {
        return type;
    }

    public String getFigure() {
        return figure;
    }
}
