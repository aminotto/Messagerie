package model;

import java.io.IOException;
import java.io.ObjectInputStream;

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
                conversation.addMessage((Message) in.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
