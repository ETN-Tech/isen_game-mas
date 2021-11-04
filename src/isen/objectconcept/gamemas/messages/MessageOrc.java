package isen.objectconcept.gamemas.messages;

import isen.objectconcept.gamemas.abstracts.Message;
import isen.objectconcept.gamemas.enums.MessageType;

public class MessageOrc extends Message {

    public MessageOrc(String message) {
        super(message, MessageType.ORC);
    }
}
