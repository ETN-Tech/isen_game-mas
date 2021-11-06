package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Goblin;
import isen.objectconcept.gamemas.messages.MessageGoblin;

public class MasterGoblin extends Goblin implements Master {

    public MasterGoblin() {
        super("GM");
    }

    @Override
    public MessageGoblin generateMessage() {
        return new MessageGoblin("Goblin message");
    }
}
