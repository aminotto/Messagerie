package controller;

import model.Messagerie;
import view.Fenetre;

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
                messagerie.connectTo("127.0.0.1", 2042);
                break;
        }
    }
}