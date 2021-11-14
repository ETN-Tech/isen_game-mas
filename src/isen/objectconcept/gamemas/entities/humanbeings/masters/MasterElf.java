package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Elf;
import isen.objectconcept.gamemas.map.Cell;
import isen.objectconcept.gamemas.map.Map;

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

    public void move(Map map) {
        //Do nothing
        Cell current_cell = map.getCells()[getPosition().getX()][getPosition().getY()];
        current_cell.setEntity(this);
    }

    @Override
    public String generateMessage() {
        return "Elf message";
    }

}
