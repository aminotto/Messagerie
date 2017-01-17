package model;

//import security.RSA;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1; // TODO : Comprendre pourquoi on est oblige de renseigner ce serial pour ne pas avoir de probleme
    private String login;
    private String ipAdrr;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /*public Utilisateur(String login) {
        this.login = login;
        KeyPair kp = RSA.generateKeyPair();

        this.publicKey = kp.getPublic();
        this.privateKey = kp.getPrivate();
    }*/

    public Utilisateur(Utilisateur u){
        this.login = u.getLogin();
        this.ipAdrr = u.getIpAdrr();
        this.publicKey = u.getPublicKey();
        this.privateKey = null;
    }

    public String getLogin() {
        return login;
    }

    public String getIpAdrr() {
        return ipAdrr;
    }

    public void setIpAdrr(String ipAdrr) {
        this.ipAdrr = ipAdrr;
    }

    public PublicKey getPublicKey(){
        return this.publicKey;
    }
    @Override
    public String toString() {
        return login;
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }
}
