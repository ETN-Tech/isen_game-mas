package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.EntityType;

public class Orc extends HumanBeing {

    public Orc() {
        super.type = EntityType.ORC;
    }
}
