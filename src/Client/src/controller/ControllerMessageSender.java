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
                messagerie.getConversations().get(fenetre.getSelectedTab()).send(new Message(messagerie.getUtilisateur().getLogin(), fenetre.getTextToSend()));
                fenetre.clearMessageField();
        }
    }
}
