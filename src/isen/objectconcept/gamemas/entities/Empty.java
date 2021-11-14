package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

public class Empty extends Entity {

    public Empty() {
        super(EntityType.EMPTY, "  ", Color.DEFAULT_COLOR.toString());
    }
}
