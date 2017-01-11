package model;

import java.io.Serializable;

public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1; // TODO : Comprendre pourquoi on est obliger de renseigner ce serial pour ne pas avoir de problème
    private String login;
    private String ipAdrr;

    public Utilisateur(String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return login;
    }
}
