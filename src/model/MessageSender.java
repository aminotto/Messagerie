package model;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessageSender implements Runnable {

    private ObjectOutputStream out;
    private Conversation conversation;

    public MessageSender(ObjectOutputStream out, Conversation conversation) {
        this.out=out;
        this.conversation=conversation;
    }

    @Override
    public void run() {
        while (true) { //Note : chercher à régler ce problème de pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!conversation.getToSendMessages().isEmpty()) {
                for(int i = 0; i<conversation.getToSendMessages().size(); i++) {
                    try {
                        out.writeObject(conversation.getToSendMessages().get(i));
                        out.flush();
                        conversation.addMessage(conversation.getToSendMessages().get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                conversation.getToSendMessages().clear();
            }
        }
    }
}
