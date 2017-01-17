package model;

import java.io.Serializable;
import java.security.PublicKey;

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

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte=texte;
    }
}