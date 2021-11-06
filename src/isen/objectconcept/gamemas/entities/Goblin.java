package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageGoblin;

import java.util.ArrayList;
import java.util.List;

public class Goblin extends HumanBeing {

    public Goblin(String figure) {
        super(
                EntityType.GOBLIN,
                figure,
                new ArrayList<>(List.of(Direction.N, Direction.NE, Direction.E)),
                new MessageGoblin("Goblin message")
        );
    }
}
