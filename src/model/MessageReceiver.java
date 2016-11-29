package model;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReceiver implements Runnable {

    private ObjectInputStream in;
    private Messagerie messagerie;

    public MessageReceiver(ObjectInputStream in, Messagerie messagerie) {
        this.in=in;
        this.messagerie=messagerie;
    }

    @Override
    public void run() {
        while (true) {
            try {
                messagerie.addMessage((Message) in.readObject());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
