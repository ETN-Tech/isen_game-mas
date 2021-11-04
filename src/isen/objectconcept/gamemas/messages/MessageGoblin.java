package isen.objectconcept.gamemas.messages;

import isen.objectconcept.gamemas.abstracts.Message;
import isen.objectconcept.gamemas.enums.MessageType;

public class MessageGoblin extends Message {

    public MessageGoblin(String message) {
        super(message, MessageType.GOBLIN);
    }
}
