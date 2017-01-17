package model;

import security.AES;
import security.RSA;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

public class Conversation {

    private ArrayList<Message> messages;
    private ArrayList<Message> toSendMessages;
    private Utilisateur receiver;
    private SecretKey aesKey;

    public Conversation() {
        messages = new ArrayList<Message>();
        toSendMessages = new ArrayList<Message>();
    }

    public Conversation(Utilisateur utilisateur){
        this();
        this.receiver = utilisateur;
        this.aesKey=AES.generateKey();
    }

    public Utilisateur getReceiver() {
        return receiver;
    }

    public void setReceiver(Utilisateur receiver) {
        this.receiver = receiver;
    }

    public synchronized ArrayList<Message> getToSendMessages() {
        while (toSendMessages.isEmpty()) {
            try {
                wait();
            }
            catch(InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        return toSendMessages;
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

    public byte[] getCryptedAesKey() {
       return RSA.encryptSecretKey(this.aesKey, receiver.getPublicKey());
    }

    public void setAesKey(SecretKey sKey) {
        this.aesKey=sKey;
    }

    public SecretKey getAesKey() {
        return aesKey;
    }
}
