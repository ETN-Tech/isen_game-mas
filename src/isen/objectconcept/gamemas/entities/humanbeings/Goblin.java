package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageGoblin;

import java.util.ArrayList;
import java.util.List;

public class Goblin extends HumanBeing {

    public Goblin(int x, int y, String figure) {
        super(x, y, EntityType.GOBLIN, figure, new ArrayList<>(List.of(Direction.N, Direction.NE, Direction.E)), new MessageGoblin("Goblin message"));
    }
}
