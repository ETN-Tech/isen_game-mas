package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class CellEntity {
    protected EntityType type = EntityType.EMPTY;

    public EntityType getType() {
        return type;
    }
}
