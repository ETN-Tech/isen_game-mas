package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageHuman;

import java.util.ArrayList;
import java.util.List;

public class Human extends HumanBeing {

    public Human(String figure) {
        super(
                EntityType.HUMAN,
                figure,
                new ArrayList<>(List.of(Direction.E, Direction.SE, Direction.S)),
                new MessageHuman("Human message")
        );
    }
}
