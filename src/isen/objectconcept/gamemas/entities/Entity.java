package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class Entity {
    protected EntityType type;
    protected String figure;
    protected int x;
    protected int y;

    public Entity(int x, int y, EntityType type, String figure) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.figure = figure;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public EntityType getType() {
        return type;
    }

    public String getFigure() {
        return figure;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
