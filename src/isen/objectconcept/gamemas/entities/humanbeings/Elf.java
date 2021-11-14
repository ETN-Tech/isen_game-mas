package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.enums.EntityType;


public class Elf extends HumanBeing {

    public Elf(String figure) {
        super(
                EntityType.ELF,
                figure,
                Color.GREEN.toString()
        );
    }
}
