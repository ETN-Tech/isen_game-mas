package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

public abstract class HumanBeing extends CellEntity {

    protected Direction[] availableDirections;

    protected HumanBeing(EntityType type, String figure, Direction[] availableDirections) {
        super(type, figure);
        this.availableDirections = availableDirections;
    }
}
