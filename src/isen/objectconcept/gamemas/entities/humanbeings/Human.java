package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

public class Human extends HumanBeing {

    public Human(String figure) {
        super(
                EntityType.HUMAN,
                figure,
                Color.BLUE.toString()
        );
    }
}
