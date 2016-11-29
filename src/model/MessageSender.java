package model;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageSender implements Runnable {

    private ObjectOutputStream out;
    private Messagerie messagerie;

    public MessageSender(ObjectOutputStream out, Messagerie messagerie) {
        this.out=out;
        this.messagerie=messagerie;
    }

    @Override
    public void run() {
        while (true) { //Note : chercher à régler ce problème de pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!messagerie.getToSendMessages().isEmpty()) {
                for(int i = 0; i<messagerie.getToSendMessages().size(); i++) {
                    try {
                        out.writeObject(messagerie.getToSendMessages().get(i));
                        out.flush();
                        messagerie.addMessage(messagerie.getToSendMessages().get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                messagerie.getToSendMessages().clear();
            }
        }
    }
}
