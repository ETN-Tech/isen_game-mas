package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageOrc;

import java.util.ArrayList;
import java.util.List;

public class Orc extends HumanBeing {

    public Orc(int x, int y, String figure) {
        super(x, y, EntityType.ORC, figure, new ArrayList<>(List.of(Direction.S, Direction.SW, Direction.W)), new MessageOrc("Orc message"));
    }
}
