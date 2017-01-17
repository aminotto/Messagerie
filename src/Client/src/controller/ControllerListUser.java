package controller;

import model.Messagerie;
import view.Fenetre;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ControllerListUser extends Controller implements MouseListener {

    public ControllerListUser(Messagerie messagerie, Fenetre fenetre) {
        super(messagerie, fenetre);
        fenetre.setControllerListUser(this);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) { // en cas de clique souris dans la liste
        JList list = (JList)mouseEvent.getSource();
        if (mouseEvent.getClickCount() == 2) { // Si on double clique
            int i = list.locationToIndex(mouseEvent.getPoint()); // On récupère l'index de l'entrée sélectionnée
            messagerie.connectTo(messagerie.getDestinataires().get(i)); // Et on se connecte au destinataire correspondant
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
