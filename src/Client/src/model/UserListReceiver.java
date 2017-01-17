package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserListReceiver implements Runnable {

    private ObjectInputStream in;
    private Messagerie messagerie;

    public UserListReceiver(ObjectInputStream in, Messagerie messagerie) {
        this.in=in;
        this.messagerie=messagerie;
    }

    @Override
    public void run() {
        while (true) {
            try {
                messagerie.setDestinataires((ArrayList<Utilisateur>) in.readObject()); // On récupère la liste d'utilisateurs connectés émise par le serveur pour mettre à jour la notre
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
