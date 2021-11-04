package isen.objectconcept.gamemas.messages;

import isen.objectconcept.gamemas.abstracts.Message;
import isen.objectconcept.gamemas.enums.MessageType;

public class MessageHuman extends Message {

    public MessageHuman(String message) {
        super(message, MessageType.HUMAN);
    }
}
