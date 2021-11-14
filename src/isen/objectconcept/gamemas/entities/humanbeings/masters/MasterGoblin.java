package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Goblin;
import isen.objectconcept.gamemas.map.Cell;
import isen.objectconcept.gamemas.map.Map;
import isen.objectconcept.gamemas.messages.MessageGoblin;

public class MasterGoblin extends Goblin implements Master {

    private static MasterGoblin masterGoblin;
    private MasterGoblin(){
        super("GM");    }

    public static MasterGoblin getInstance(){
        if(masterGoblin==null){
            masterGoblin = new MasterGoblin();
        }
        return masterGoblin;
    }
    public void move(Map map) {
        //Do nothing
        Cell current_cell = map.getCells()[getPosition().getX()][getPosition().getY()];
        current_cell.setEntity(this);
    }

    @Override
    public String generateMessage() {
        return new String("Goblin message");
    }
}
