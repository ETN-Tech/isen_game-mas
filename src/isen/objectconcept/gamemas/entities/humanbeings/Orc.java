package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageOrc;

import java.util.ArrayList;
import java.util.List;

public class Orc extends HumanBeing {

    public Orc(String figure) {
        super(
                EntityType.ORC,
                figure,
                new ArrayList<>(List.of(Direction.S, Direction.SW, Direction.W)),
                new String("Orc message")
        );
    }
}
