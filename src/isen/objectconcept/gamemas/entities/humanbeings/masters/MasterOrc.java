package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Orc;
import isen.objectconcept.gamemas.messages.MessageOrc;

public class MasterOrc extends Orc implements Master {

    public MasterOrc() {
        super("OM");
    }

    @Override
    public MessageOrc generateMessage() {
        return new MessageOrc("Orc message");
    }
}
