package model;

import java.util.ArrayList;

/**
 * Created by aminotto on 04/12/16.
 */
public class Conversation {

    private ArrayList<Message> messages;
    private ArrayList<Message> toSendMessages;
    private String receiverName; //socké comme ça pour le moment mais faire une classe utilisateur ?

    public Conversation(){
        messages = new ArrayList<Message>();
        toSendMessages = new ArrayList<Message>();
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public synchronized ArrayList<Message> getToSendMessages() {
        while (toSendMessages.isEmpty()) {
            try {
                wait();
                System.out.print("ok");
            }
            catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return toSendMessages;
    }


    public void connectTo(String ip, int port) {
        new Thread(new ConnectionTo(ip, port, this)).start();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public synchronized void send(Message message) {
        toSendMessages.add(message);
        notify();
    }

    public String readMessages() {
        String result = "";
        for(int i=0; i<messages.size(); i++) {
            result += messages.get(i).toString() + "\n";
        }
        return result;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
