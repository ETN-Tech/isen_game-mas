package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Orc;
import isen.objectconcept.gamemas.messages.MessageOrc;

public class MasterOrc extends Orc implements Master {


    private static MasterOrc masterOrc;
    private MasterOrc(){
        super("OM");
    }

    public static MasterOrc getInstance(){
        if(masterOrc==null){
            masterOrc = new MasterOrc();
        }
        return masterOrc;
    }

    @Override
    public MessageOrc generateMessage() {
        return new MessageOrc("Orc message");
    }
}
