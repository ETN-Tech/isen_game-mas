package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Elf;
import isen.objectconcept.gamemas.messages.MessageElf;

public class MasterElf extends Elf implements Master {

    public MasterElf() {
        super("EM");
    }

    @Override
    public MessageElf generateMessage() {
        return new MessageElf("Elf message");
    }

}
