package isen.objectconcept.gamemas.entities.humanbeings;

import isen.objectconcept.gamemas.Game;
import isen.objectconcept.gamemas.entities.Entity;
import isen.objectconcept.gamemas.entities.humanbeings.masters.Master;
import isen.objectconcept.gamemas.messages.Message;
import isen.objectconcept.gamemas.enums.Direction;
import isen.objectconcept.gamemas.enums.EntityType;

import java.util.ArrayList;
import java.util.Random;

public abstract class HumanBeing extends Entity {

    private final Random random = new Random();

    protected ArrayList<Direction> forwardDirections = new ArrayList<>();
    protected ArrayList<Direction> backwardDirections = new ArrayList<>();
    protected ArrayList<Message> messages = new ArrayList<>();
    protected Message baseMessage;
    protected int energyPoints = Game.getMaxEnergyPoints();

    protected HumanBeing(EntityType type, String figure, ArrayList<Direction> backwardDirections, Message baseMessage) {
        super(type, figure);
        // generate forwardDirections from backwardDirections
        for (Direction direction: Direction.values()) {
            if (direction != backwardDirections.get(1)) {
                forwardDirections.add(direction);
            }
        }
        this.backwardDirections.addAll(backwardDirections);
        this.baseMessage = baseMessage;
    }

    /* ---- GETTERS & SETTERS ----- */
    public ArrayList<Direction> getForwardDirections() {
        return forwardDirections;
    }

    public ArrayList<Direction> getBackwardDirections() {
        return backwardDirections;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public Message getBaseMessage() {
        return baseMessage;
    }

    public int getEnergyPoints() {
        return energyPoints;
    }

    public void decreaseEnergyPoints() {
        energyPoints--;
    }

    /**
     * Perform actions when this entity meet the otherEntity
     * @param otherEntity entity to meet
     */
    public void meet(HumanBeing otherEntity) {
        EntityType ally;

        switch (type) {
            case ELF -> ally = EntityType.HUMAN;
            case GOBLIN -> ally = EntityType.ORC;
            case HUMAN -> ally = EntityType.ELF;
            case ORC -> ally = EntityType.GOBLIN;
            default -> throw new IllegalStateException("Unexpected value: " + type);
        }

        // check if ally
        if (otherEntity.getType() == ally) {
            // ally, exchange message
            exchangeMessageWith(otherEntity);
        }
        // check if same type and Master
        else if (otherEntity.getType() == type && otherEntity instanceof Master masterEntity) {
            // give messages to master
            otherEntity.addMessages(messages);

            // reset messages
            messages.removeAll(messages);
            baseMessage = masterEntity.generateMessage();

            // check victory
            if (otherEntity.getMessages().size() > Game.winMessagesNumber) {
                System.out.println(otherEntity);
                // VICTORY
                Game.setGameOver();
                System.out.println(otherEntity.getType() + " has won!");
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
            // enemy lose baseMessage
            messages.add(enemy.loseBaseMessage());
        } else {
            // this loses baseMessage
            enemy.addMessage(loseBaseMessage());
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
            ally.addMessage(messages.get(random.nextInt(msgSize)));
        }

        msgSize = ally.getMessages().size();
        if (msgSize > 0) {
            // ally share message with this
            messages.add(ally.getMessages().get(random.nextInt(msgSize)));
        }
    }


    /* ----- MESSAGES MANAGEMENT ----- */
    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addMessages(ArrayList<Message> messages) {
        this.messages.addAll(messages);
    }

    public Message loseBaseMessage() {
        Message saveBaseMessage = baseMessage;
        // remove baseMessage
        baseMessage = null;

        return saveBaseMessage;
    }

    @Override
    public String toString() {
        return "HumanBeing{" +
                "type=" + type +
                ", figure='" + figure + '\'' +
                ", forwardDirections=" + forwardDirections +
                ", backwardDirections=" + backwardDirections +
                ", messages=" + messages +
                ", baseMessage=" + baseMessage +
                ", energyPoints=" + energyPoints +
                '}';
    }
}
