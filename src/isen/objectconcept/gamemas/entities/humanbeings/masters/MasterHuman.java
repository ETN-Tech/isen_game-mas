package isen.objectconcept.gamemas.entities.humanbeings.masters;

import isen.objectconcept.gamemas.entities.humanbeings.Human;
import isen.objectconcept.gamemas.map.Cell;
import isen.objectconcept.gamemas.map.Map;

public class MasterHuman extends Human implements Master {


    private static MasterHuman masterHuman;
    private MasterHuman(){
        super("HM");
    }

    public static MasterHuman getInstance(){
        if(masterHuman ==null){
            masterHuman  = new MasterHuman();
        }
        return masterHuman;
    }

    public void move(Map map) {
        //Do nothing
        Cell current_cell = map.getCells()[getPosition().getX()][getPosition().getY()];
        current_cell.setEntity(this);
    }
    @Override
    public String generateMessage() {
        return new String("Human message");
    }
}
