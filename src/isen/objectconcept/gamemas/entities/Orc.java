package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageOrc;

public class Orc extends HumanBeing {

    public Orc(String figure) {
        super(
                EntityType.ORC,
                figure,
                new Direction[]{ Direction.NW, Direction.N, Direction.NE, Direction.E, Direction.SE },
                new MessageOrc("Orc message")
        );
    }
}
