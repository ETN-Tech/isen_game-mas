package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.Game;
import isen.objectconcept.gamemas.entities.Empty;
import isen.objectconcept.gamemas.entities.Entity;
import isen.objectconcept.gamemas.entities.Obstacle;
import isen.objectconcept.gamemas.entities.humanbeings.masters.Master;
import isen.objectconcept.gamemas.enums.Color;
import isen.objectconcept.gamemas.map.*;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;

import static isen.objectconcept.gamemas.enums.EntityType.EMPTY;
import static isen.objectconcept.gamemas.enums.EntityType.OBSTACLE;


public abstract class HumanBeing extends Entity {

    private final Random random = new Random();
    protected ArrayList<String> messages = new ArrayList<>();
    protected int energyPoints = 30;
    protected int refillPoints = 3;
    protected int numberOfMessages = 2;

    protected Point position;
    protected Point last_position;
    protected boolean moved;

    protected HumanBeing(EntityType type, String figure, String color_code) {
        super(type, figure, color_code);
        // Generate initial messages
        for (int i=1; i <= numberOfMessages; i++){
            this.messages.add(this.type.toString()+" message "+i);
        }

    }

    /* ---- GETTERS & SETTERS ----- */


    public ArrayList<String> getMessages() {
        return messages;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public void setEnergyPoints(int energyPoints) {
        this.energyPoints = energyPoints;
    }

    public void decreaseEnergyPoints() {
        energyPoints--;
    }

    public String getColor_code() {
        return color_code;
    }

    /**
     * Perform actions when this entity meet the otherEntity
     * @param otherEntity entity to meet
     */
    public void meet(HumanBeing otherEntity) {
        EntityType ally;

        switch (type) {
            case ELF :
                ally = EntityType.HUMAN;
                break;
            case GOBLIN :
                ally = EntityType.ORC;
                break;
            case HUMAN :
                ally = EntityType.ELF;
                break;
            case ORC :
                ally = EntityType.GOBLIN;
                break;
            default :
                throw new IllegalStateException("Unexpected value: " + type);
        }

        // check if ally
        if (otherEntity.getType() == ally) {
            // ally, exchange message
            exchangeMessageWith(otherEntity);
        }
        // check if same type and Master
        else if (otherEntity.getType() == type && otherEntity instanceof Master masterEntity) {
            // give messages to master
            System.out.println(this.getFigure()+": Giving messages to master");
            if (this.messages.size() > 0 ) {
                for (String servant_message: this.getMessages()) {
                    if (!otherEntity.getMessages().contains(servant_message)) {
                        otherEntity.addMessage(servant_message);
                    }
                }
            }
            // reset messages
            messages.removeAll(messages);


            // check victory
            if (otherEntity.getMessages().size() == Game.winMessagesNumber) {
                System.out.println(otherEntity);
                // VICTORY
                Game.setGameOver();
                System.out.println(otherEntity.getType() + " KINGDOM  has won!");
            }
        }
        else {
            // enemy, fight
            fightWith(otherEntity);
        }
    }

    /**
     * Fight with an enemy
     * @param enemy to fight with
     */
    public void fightWith(HumanBeing enemy) {
        if (random.nextBoolean()) {
            if (enemy.messages.size() > 0 ) {
                for (String enemy_message: enemy.getMessages()) {
                    if (!messages.contains(enemy_message)) {
                        messages.add(enemy_message);
                    }
                }
//                enemy.messages.remove(0);
            }
            System.out.println(this.getFigure()+": Fight Status - won");

        } else {
            if (messages.size() > 0 ) {
                for (String my_message: enemy.getMessages()) {
                    if (!enemy.messages.contains(my_message)) {
                        enemy.messages.add(my_message);
                    }
                }
//                messages.remove(0);
            }
            System.out.println(this.getFigure()+": Fight Status - lost");
        }
    }

    /**
     * Exchange a message with an ally
     * @param ally to exchange message with
     */
    public void exchangeMessageWith(HumanBeing ally) {
        int msgSize = messages.size();
        if (msgSize > 0) {
            // share a message with ally
            for (String my_message: this.getMessages()) {
                if (!ally.messages.contains(my_message)) {
                    ally.messages.add(my_message);
                }
                break;

            }
        }

        msgSize = ally.getMessages().size();
        if (msgSize > 0) {
            // ally share message with this
            for (String ally_message: ally.getMessages()) {
                if (!messages.contains(ally_message)) {
                    messages.add(ally_message);
                }
                break;
            }
        }
        System.out.println("Messages shared between allies: "+this.getFigure()+" and "+ally.getFigure());
    }


    /* ----- MESSAGES MANAGEMENT ----- */
    public void addMessage(String message) {
        messages.add(message);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getLast_position() {
        return last_position;
    }

    public void setLast_position(Point last_position) {
        this.last_position = last_position;
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "type=" + type +
                ", figure='" + figure + '\'' +
                ", messages=" + messages +
                ", energyPoints=" + energyPoints +
                '}';
    }


    /**
     * the movement manager method
     * calls the right moves based on conditions
     * @param map
     */
    public void move(Map map){
        this.moved = false;
        Cell[][] cells = map.getCells();
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        // Go to master if all messages are found
        if(this.energyPoints > 0 && this.messages.size() == 8){
            Point start = new Point(x,y);
            ShortestPoint stop = findMaster(cells, start);

            if(stop == null) {
                direction(map);
            }
        }
        else if(this.energyPoints > refillPoints ){
            int number_of_steps = random.nextInt(3) + 1;
            for (int i = 0; i < number_of_steps; i++){
                direction( map);
            }
        }
        // if low on energy points, find way back to safezone
        else if(this.energyPoints > 0 && this.energyPoints <= refillPoints){
            Point start = new Point(x,y);
            ShortestPoint stop = findSafeZone(cells, start);
        }
        // Turn into an obstacle if energy points is 0
        else if(this.energyPoints == 0){
            Cell current_cell = cells[getPosition().getX()][getPosition().getY()];

            System.out.println("\u001B[31m"+this.getFigure()+": Has Turned into an obstacle");
            System.out.print("\u001B[0m");

            current_cell.setEntity(new Obstacle());
        }

        // Check Interaction only if Entity was moved
        if(moved == true){
            checkInteraction(map);
        }
    }

    /**
     * check neighbour cells for interactions
     * @param map
     */
    public void checkInteraction(Map map){
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        int row_limit = map.getCells().length;
        int column_limit = map.getCells()[0].length;

        //First Check if master is found
        for(int row = Math.max(0, x-1); row <= Math.min(x+1, row_limit-1); row++){
            for(int col = Math.max(0,y- 1); col <= Math.min(y+1, column_limit-1); col++){
                if(row != x || col != y){
                    boolean is_not_empty = map.getCells()[row][col].getEntity().getType() != EMPTY;
                    boolean is_same_type = map.getCells()[row][col].getEntity().getType() == this.getType();
                    boolean is_not_obstacle = map.getCells()[row][col].getEntity().getType() != OBSTACLE;
                    if ( is_same_type && map.getCells()[row][col].getEntity() instanceof HumanBeing entity && map.getCells()[row][col].getEntity() instanceof Master masterEntity) {
                        System.out.println(this.getFigure()+ ": Master found - "+map.getCells()[row][col].getEntity().getFigure());
                        this.meet(entity);
                        return;
                    }
                }
            }
        }
        // If no master Check Neighbour cells for interactions
        for(int row = Math.max(1, x-1); row <= Math.min(x+1, row_limit-1); row++){
            for(int col = Math.max(1,y- 1); col <= Math.min(y+1, column_limit-1); col++){
                if(row != x || col != y){
                    boolean is_not_empty = map.getCells()[row][col].getEntity().getType() != EMPTY;
                    boolean is_not_same_type = map.getCells()[row][col].getEntity().getType() != this.getType();
                    boolean is_not_obstacle = map.getCells()[row][col].getEntity().getType() != OBSTACLE;

                    if(is_not_empty && is_not_same_type && is_not_obstacle){
                        System.out.println(this.getFigure()+ ": I have found " +map.getCells()[row][col].getEntity().getFigure());
                        if (map.getCells()[row][col].getEntity() instanceof HumanBeing aroundEntity) {
                            this.meet(aroundEntity);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Find way back to safezone when low on energy points
     * @param cells
     * @param start
     * @return
     */
    public ShortestPoint findSafeZone(Cell[][] cells, Point start){
        for(Cell[] row: cells) {
            for (Cell cols: row) {
                if(cols.getType().toString() == this.getType().toString()){
                    Point end = new Point(cols.getX(), cols.getY());
                    System.out.println(this.getFigure()+": Shortest path to safezone");
                    ShortestPoint stop = ShortestPath.shortestPath(cells, start, end);
                    if(stop != null){
                        this.setLast_position(getPosition());
                        this.setPosition(new Point(stop.getX(),stop.getY()));
                        Cell last_cell = cells[getLast_position().getX()][getLast_position().getY()];

                        Cell current_cell = cells[this.getPosition().getX()][this.getPosition().getY()];

                        last_cell.setEntity(new Empty());

                        current_cell.setEntity(this);

                        if(current_cell.getType().toString() == this.getType().toString()){
                            this.setEnergyPoints(30);
                        }
                        else{
                            this.energyPoints -= stop.getSteps();
                        }
                        this.moved = true;

                    }
                    else {
                        this.moved = false;
                    }
                    return stop;
                }
            }
        }
        return null;
    }

    /**
     * find the masters location on the map
     * @param cells
     * @return
     */
    public Point findMasterIndex(Cell[][] cells){
        for(Cell[] row: cells) {
            for (Cell cols : row) {
                if (cols.getType().toString() == this.getType().toString() && cols.getEntity() instanceof Master masterEntity) {
                    return new Point(cols.getX(), cols.getY());
                }
            }
        }
        return null;
    }

    /**
     * find the shortest path to master
     * @param cells
     * @param start
     * @return
     */
    public ShortestPoint findMaster(Cell[][] cells, Point start){
        Point index = findMasterIndex(cells);
        System.out.println(this.getFigure()+": Shortest path to Master "+cells[index.getX()][index.getY()].getEntity().getFigure());

        int row_limit = cells.length ;
        int column_limit = cells[0].length ;
        for(int x = Math.max(0, index.getX() -1); x <= Math.min(index.getX() +1, row_limit-1); x++){
            for(int y = Math.max(0, index.getY()- 1); y <= Math.min(index.getY() +1, column_limit-1); y++){
                if(index.getX() != x || index.getY() != y){
                    if(cells[x][y].getEntity().getType() == EMPTY){
                        Point end = new Point(x,y);
                        ShortestPoint stop = ShortestPath.shortestPath(cells, start, end);
                        if(stop != null){

                            this.setLast_position(new Point(getPosition().getX(),getPosition().getY()));
                            this.setPosition(new Point(stop.getX(),stop.getY()));
                            Cell last_cell = cells[getLast_position().getX()][getLast_position().getY()];

                            Cell current_cell = cells[getPosition().getX()][getPosition().getY()];

                            last_cell.setEntity(new Empty());

                            current_cell.setEntity(this);

                            if(current_cell.getType().toString() == this.getType().toString()){
                                this.setEnergyPoints(30);
                            }
                            else{
                                this.energyPoints -= stop.getSteps();
                            }
                            this.moved = true;
                            return stop;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * The random movement method
     * @param map
     */
    public void direction(Map map){
        int m = map.getCells().length - 1;
        int n = map.getCells()[0].length - 1;

        Random random = new Random();
        // Generate a random number from 0 to 7
        int random_direction = random.nextInt(8);
        //All 8 directions : E, N, W, S, NE, NW, SE, SW
        Point[] all_directions = { new Point(0,1), new Point(1,0), new Point(0,-1),
                                   new Point(-1,0), new Point(1,1), new Point(1,-1),
                                   new Point(-1,1), new Point(-1,-1)};
        Point direction = all_directions[random_direction];

        // if out of bounds, change direction
        for(int i=0; i < 2; i++){
            if(getPosition().getX() == m && direction.getX() == 1){
                direction.setX(0);
            }
            else if(getPosition().getY() == n && direction.getY() == 1){
                direction.setY(0);
            }
            else if(getPosition().getX() == 0 && direction.getX() == -1){
                direction.setX(1);
            }
            else if(getPosition().getY() == 0 && direction.getY() == -1){
                direction.setY(1);
            }
        }

        boolean check_energy_points = this.energyPoints > 0;
        boolean check_empty_cell = map.getCells()[getPosition().getX() + direction.getX()][getPosition().getY() + direction.getY()].getEntity().getType() == EMPTY;
        if(check_empty_cell && check_energy_points ) {
            setLast_position(new Point(getPosition().getX(),getPosition().getY()));
            getPosition().setX(getPosition().getX()  + direction.getX());
            getPosition().setY(getPosition().getY()  + direction.getY());
            int x = getPosition().getX();
            int y = getPosition().getY();

            Cell last_cell = map.getCells()[getLast_position().getX()][getLast_position().getY()];
            Cell current_cell = map.getCells()[getPosition().getX()][getPosition().getY()];
            last_cell.setEntity(new Empty());
            current_cell.setEntity(this);
            if(current_cell.getType().toString() == this.getType().toString()){
                this.setEnergyPoints(30);
            }
            else{
                this.energyPoints -= 1;
            }

            this.moved = true;
        }
        else {
            System.out.println(this.getFigure() +": You cannot make move past an obstacle");
            this.moved = false;
        }
    }

}
