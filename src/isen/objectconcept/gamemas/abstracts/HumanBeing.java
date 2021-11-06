package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;

public abstract class HumanBeing extends Entity {

    protected Direction[] availableDirections;
    protected ArrayList<Message> messages = new ArrayList<>();

    protected HumanBeing(EntityType type, String figure, Direction[] availableDirections) {
        super(type, figure);
        this.availableDirections = availableDirections;
    }

    public Direction[] getAvailableDirections() {
        return availableDirections;
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
