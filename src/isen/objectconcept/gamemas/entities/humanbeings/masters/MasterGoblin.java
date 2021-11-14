package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Goblin;
import isen.objectconcept.gamemas.messages.MessageGoblin;

public class MasterGoblin extends Goblin implements Master {

    private static MasterGoblin masterGoblin;
    private MasterGoblin() {
        super(0, 0, "GM");
    }

    public static MasterGoblin getInstance() {
        if(masterGoblin==null){
            masterGoblin = new MasterGoblin();
        }
        return masterGoblin;
    }

    @Override
    public MessageGoblin generateMessage() {
        return new MessageGoblin("Goblin message");
    }
}
