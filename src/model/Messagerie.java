package model;

import java.util.ArrayList;

/**
 * Created by aminotto on 29/11/16.
 */
public class Messagerie {

    private ArrayList<Conversation> conversations;

    public Messagerie() {
        conversations = new ArrayList<Conversation>();
        waitForConnectionOnPort(2042);
    }

    private void waitForConnectionOnPort(int port) {
        new Thread(new PortListener(port, this)).start();
    }

    public void connectTo(String ip, int port) {
        Conversation conversation = new Conversation();
        conversations.add(conversation);
        conversation.connectTo(ip, port);
    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }


    public void leave() {
        System.exit(0);
    }
}
