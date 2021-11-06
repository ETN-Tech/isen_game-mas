package isen.objectconcept.gamemas.abstracts;

import isen.objectconcept.gamemas.enums.EntityType;

public abstract class Entity {
    protected EntityType type;
    protected String figure;

    public Entity(EntityType type, String figure) {
        this.type = type;
        this.figure = figure;
    }

    public EntityType getType() {
        return type;
    }

    public String getFigure() {
        return figure;
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
        } else {
            // enemy, fight
            fightWith(otherEntity);
        }
    }

    public void fightWith(Entity entity) {

    }

    public void exchangeMessageWith(Entity entity) {

    }
}
