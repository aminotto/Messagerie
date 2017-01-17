package model;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String texte;

    public Message(String sender, String texte) {
        this.sender=sender;
        this.texte=texte;
    }

    @Override
    public String toString() {
        return sender + " : " + texte;
    }
}