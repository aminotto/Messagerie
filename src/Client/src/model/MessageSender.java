package model;

import security.AES;
import security.RSA;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class MessageSender implements Runnable {

    private ObjectOutputStream out;
    private Conversation conversation;

    public MessageSender(ObjectOutputStream out, Conversation conversation) {
        this.out=out;
        this.conversation=conversation;
    }

    @Override
    public void run() {
        while (true) {
            for(int i = 0; i<conversation.getToSendMessages().size(); i++) {
                try {
                    Message message = conversation.getToSendMessages().get(i);
                    System.out.println(message.getTexte());
                    message.setTexte(AES.encrypt(message.getTexte(),conversation.getAesKey()));
                    out.writeObject(message);
                    out.flush();
                    message.setTexte(AES.decrypt(message.getTexte(),conversation.getAesKey()));
                    conversation.addMessage(conversation.getToSendMessages().get(i));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conversation.getToSendMessages().clear();
        }
    }
}
