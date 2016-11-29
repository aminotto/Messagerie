package model;

import java.util.ArrayList;

/**
 * Created by aminotto on 29/11/16.
 */
public class Messagerie {

    private ArrayList<Message> messages;
    private ArrayList<Message> toSendMessages;

    public Messagerie() {
        messages = new ArrayList<Message>();
        toSendMessages = new ArrayList<Message>();
        waitForConnectionOnPort(2042);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public ArrayList<Message> getToSendMessages() {
        return toSendMessages;
    }

    private void waitForConnectionOnPort(int port) {
        new Thread(new PortListener(port, this)).start();
    }

    public void connectTo(String ip, int port) {
        new Thread(new ConnectionTo(ip, port, this)).start();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void send(Message message) {
        toSendMessages.add(message);
    }

    public String readMessages() {
        String result = "";
        for(int i=0; i<messages.size(); i++) {
            result += messages.get(i).toString() + "\n";
        }
        return result;
    }
}
