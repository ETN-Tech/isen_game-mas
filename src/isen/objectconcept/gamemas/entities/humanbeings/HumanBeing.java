package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.Game;
import isen.objectconcept.gamemas.entities.Entity;
import isen.objectconcept.gamemas.messages.Message;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;

public abstract class HumanBeing extends Entity {

    protected ArrayList<Direction> forwardDirections = new ArrayList<>();
    protected ArrayList<Direction> backwardDirections = new ArrayList<>();
    protected ArrayList<Message> messages = new ArrayList<>();
    protected int energyPoints = Game.getMaxEnergyPoints();

    protected HumanBeing(EntityType type, String figure, ArrayList<Direction> backwardDirections, Message message) {
        super(type, figure);
        // generate forwardDirections from backwardDirections
        for (Direction direction: Direction.values()) {
            if (direction != backwardDirections.get(1)) {
                forwardDirections.add(direction);
            }
        }
        this.backwardDirections.addAll(backwardDirections);
        this.messages.add(message);
    }

    public ArrayList<Direction> getForwardDirections() {
        return forwardDirections;
    }

    public ArrayList<Direction> getBackwardDirections() {
        return backwardDirections;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public void decreaseEnergyPoints() {
        energyPoints--;
    }

    /* ----- MESSAGES MANAGEMENT ----- */
    public void addMessage(Message message) {
        messages.add(message);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
    }
    public Message removeMessage(int i) {
        return messages.remove(i);
    }
}