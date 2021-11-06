package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.abstracts.Entity;

public class Obstacle extends Entity {

    public Obstacle() {
        super(EntityType.OBSTACLE, "x ");
    }
}
