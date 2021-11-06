package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Human;
import isen.objectconcept.gamemas.messages.MessageHuman;

public class MasterHuman extends Human implements Master {

    public MasterHuman() {
        super("HM");
    }

    @Override
    public MessageHuman generateMessage() {
        return new MessageHuman("Human message");
    }
}
