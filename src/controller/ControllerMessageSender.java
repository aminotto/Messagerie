package controller;

import model.Message;
import model.Messagerie;
import view.Fenetre;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerMessageSender extends Controller implements ActionListener {

    public ControllerMessageSender(Messagerie messagerie, Fenetre fenetre) {
        super(messagerie, fenetre);
        fenetre.setControllerMessageSender(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        switch (actionEvent.getActionCommand()) {

            case "send":
                messagerie.send(new Message("login", fenetre.getTextToSend()));
                fenetre.clearMessageField();
        }
    }
}
