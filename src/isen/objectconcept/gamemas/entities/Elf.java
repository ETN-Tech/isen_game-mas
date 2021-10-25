package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.EntityType;

public class Elf extends HumanBeing {

    public Elf() {
        super.type = EntityType.ELF;
    }
}
