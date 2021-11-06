package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class Entity {
    protected EntityType type;
    protected String figure;

    public Entity(EntityType type, String figure) {
        this.type = type;
        this.figure = figure;
    }

    public EntityType getType() {
        return type;
    }

    public String getFigure() {
        return figure;
    }

    public void fightWith(Entity entity) {

    }

    public void exchangeMessageWith(Entity entity) {

    }
}