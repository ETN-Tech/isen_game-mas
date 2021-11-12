package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Elf;
import isen.objectconcept.gamemas.messages.MessageElf;

public class MasterElf extends Elf implements Master {


    private static MasterElf masterElf;
    private MasterElf(){
        super("EM");
    }

    public static MasterElf getInstance(){
        if(masterElf==null){
            masterElf = new MasterElf();
        }
        return masterElf;
    }

    @Override
    public MessageElf generateMessage() {
        return new MessageElf("Elf message");
    }

}
