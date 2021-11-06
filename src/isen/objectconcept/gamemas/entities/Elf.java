package isen.objectconcept.gamemas.entities;

import isen.objectconcept.gamemas.abstracts.HumanBeing;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;
import isen.objectconcept.gamemas.messages.MessageElf;

import java.util.ArrayList;
import java.util.List;

public class Elf extends HumanBeing {

    public Elf(String figure) {
        super(
                EntityType.ELF,
                figure,
                new ArrayList<>(List.of(Direction.W, Direction.NW, Direction.N)),
                new MessageElf("Elf message")
        );
    }
}
