package isen.objectconcept.gamemas.messages;

import isen.objectconcept.gamemas.abstracts.Message;
import isen.objectconcept.gamemas.enums.MessageType;

public class MessageElf extends Message {

    public MessageElf(String message) {
        super(message, MessageType.ELF);
    }
}
