package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

public class Goblin extends HumanBeing {

    public Goblin(String figure) {
        super(EntityType.GOBLIN, figure, new Direction[]{ Direction.SE, Direction.S, Direction.SW, Direction.W, Direction.NW });
    }
}
