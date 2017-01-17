package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Sender implements Runnable {

    private ObjectOutputStream out;
    private Server server;
    private Utilisateur utilisateur;

    public Sender(ObjectOutputStream out, Server server, Utilisateur utilisateur) {
        this.out=out;
        this.server=server;
        this.utilisateur=utilisateur;
    }

    @Override
    public void run() {
        boolean isConnected = true;
        while (isConnected) {
            try {
                out.writeObject(new ArrayList<Utilisateur>(server.getUtilisateurs()));
                out.flush();
                Thread.sleep(500); // pause de 500 ms pour ne pas bombarder le client, eput être mettre plus ?
            } catch (IOException e) {
                isConnected=false;
                server.removeUser(utilisateur); // supprime l'utilisateur de notre liste si la connexion est brisée
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}