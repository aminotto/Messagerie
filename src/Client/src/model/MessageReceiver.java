package model;

import security.AES;
import security.RSA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.PrivateKey;

public class MessageReceiver implements Runnable {

    private ObjectInputStream in;
    private Conversation conversation;

    public MessageReceiver(ObjectInputStream in, Conversation conversation) {
        this.in=in;
        this.conversation=conversation;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message message = (Message) in.readObject();
                message.setTexte(AES.decrypt(message.getTexte(),conversation.getAesKey()));
                conversation.addMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
