package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.abstracts.CellEntity;

public class Obstacle extends CellEntity {

    public Obstacle() {
        super.type = EntityType.OBSTACLE;
    }
}
