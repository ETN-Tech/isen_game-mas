package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.EntityType;

public class Goblin extends HumanBeing {

    public Goblin() {
        super.type = EntityType.GOBLIN;
    }
}
