package controller;

import model.Messagerie;
import view.Fenetre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerTimer extends Controller implements ActionListener {

    public ControllerTimer(Messagerie messagerie, Fenetre fenetre) {
        super(messagerie, fenetre);
        Timer timer = new Timer(50,this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        fenetre.repaint();
    }
}
