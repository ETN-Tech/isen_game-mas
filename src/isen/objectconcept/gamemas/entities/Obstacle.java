package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

public class Obstacle extends Entity {

    public Obstacle() {
        super(EntityType.OBSTACLE, "x ", Color.RED.toString());
    }
}
