package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Orc;
import isen.objectconcept.gamemas.map.Cell;
import isen.objectconcept.gamemas.map.Map;
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

    public void move(Map map) {
        //Do nothing
        Cell current_cell = map.getCells()[getPosition().getX()][getPosition().getY()];
        current_cell.setEntity(this);
    }

    @Override
    public String generateMessage() {
        return new String("Orc message");
    }
}
