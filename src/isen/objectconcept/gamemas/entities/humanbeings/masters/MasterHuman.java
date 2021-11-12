package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Human;
import isen.objectconcept.gamemas.messages.MessageHuman;

public class MasterHuman extends Human implements Master {


    private static MasterHuman masterHumana;
    private MasterHuman(){
        super("HM");
    }

    public static MasterHuman getInstance(){
        if(masterHumana==null){
            masterHumana = new MasterHuman();
        }
        return masterHumana;
    }
    @Override
    public MessageHuman generateMessage() {
        return new MessageHuman("Human message");
    }
}
