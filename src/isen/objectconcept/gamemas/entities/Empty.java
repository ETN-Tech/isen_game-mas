package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.CellEntity;
import isen.objectconcept.gamemas.enums.EntityType;

public class Empty extends CellEntity {

    public Empty() {
        super(EntityType.EMPTY, "  ");
    }
}
