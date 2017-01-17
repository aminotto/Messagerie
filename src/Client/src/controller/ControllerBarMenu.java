package controller;

import model.Messagerie;
import model.Utilisateur;
import view.Fenetre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerBarMenu extends Controller implements ActionListener {

    public ControllerBarMenu(Messagerie messagerie, Fenetre fenetre) {
        super(messagerie, fenetre);
        fenetre.setControllerBarMenu(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        switch (actionEvent.getActionCommand()) {
            case "leave":
                messagerie.leave();
                break;
            case "connect":
                String login = JOptionPane.showInputDialog(null, "Login :", "Connexion", JOptionPane.QUESTION_MESSAGE); // demande le login du client
                messagerie.setUtilisateur(new Utilisateur(login));
                String ip = JOptionPane.showInputDialog(null, "IP du Serveur :", "Connexion", JOptionPane.QUESTION_MESSAGE); // demande l'ip au client
                messagerie.connectToServer(ip);
                break;
        }
    }
}