package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class Entity {
    protected EntityType type;
    protected String figure;
    protected String color_code;


    public Entity(EntityType type, String figure, String color_code) {
        this.type = type;
        this.figure = figure;
        this.color_code =color_code;
    }

    public EntityType getType() {
        return type;
    }

    public String getFigure() {
        return figure;
    }

    public String getColor_code() {
        return color_code;
    }
}
