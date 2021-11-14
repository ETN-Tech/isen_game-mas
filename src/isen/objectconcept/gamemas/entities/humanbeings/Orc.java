package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;

public class Orc extends HumanBeing {

    public Orc(String figure) {
        super(
                EntityType.ORC,
                figure,
                Color.PURPLE.toString()
        );
    }
}
