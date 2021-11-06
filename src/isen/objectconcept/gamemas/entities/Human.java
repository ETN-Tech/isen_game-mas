package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageHuman;

public class Human extends HumanBeing {

    public Human(String figure) {
        super(
                EntityType.HUMAN,
                figure,
                new Direction[]{ Direction.SW, Direction.W, Direction.NW, Direction.N, Direction.NE },
                new MessageHuman("Human message")
        );
    }
}
