package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.EntityType;

public class Obstacle extends Entity {

    public Obstacle(int x, int y) {
        super(x, y, EntityType.OBSTACLE, "x ");
    }
}
