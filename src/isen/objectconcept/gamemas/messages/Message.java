package isen.objectconcept.gamemas.messages;

import isen.objectconcept.gamemas.enums.MessageType;

public abstract class Message {

    protected String message;
    protected MessageType type;

    public Message(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }
}
