package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

public class Goblin extends HumanBeing {

    public Goblin(String figure) {
        super(
                EntityType.GOBLIN,
                figure,
                Color.YELLOW.toString()
        );
    }
}
