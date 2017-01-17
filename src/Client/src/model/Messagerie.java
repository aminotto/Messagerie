package model;

import security.RSA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;

public class Messagerie {

    private ArrayList<Conversation> conversations;
    private ArrayList<Utilisateur> destinataires;
    private Utilisateur utilisateur;

    public Messagerie() {
        conversations = new ArrayList<Conversation>();
        destinataires = new ArrayList<Utilisateur>();
        waitForConnectionOnPort(2042);
    }

    private void waitForConnectionOnPort(int port) {
        new Thread(new PortListener(port, this)).start();
    }

    public void connectTo(Utilisateur destinataire) {
        try {
            Socket socket = new Socket(destinataire.getIpAdrr(), 2042);
            System.out.println(destinataire);
            Conversation conversation = new Conversation(destinataire);
            conversation.addMessage(new Message("System", "Connexion réussie !"));
            conversations.add(conversation);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(new Utilisateur(this.utilisateur)); // On envoie à notre destinataires nos informations
            out.flush();

            PublicKey publicKeyDestinataire = null;
            try {
                publicKeyDestinataire = (PublicKey) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(publicKeyDestinataire == null){
                System.exit(-1);
            }

            out.writeObject(RSA.encryptSecretKey(conversation.getAesKey(),publicKeyDestinataire));

            new Thread(new MessageReceiver(in, conversation)).start();
            new Thread(new MessageSender(out, conversation)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToServer(String ip) {
        try {
            Socket socket = new Socket(ip, 2043);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            out.writeObject(new Utilisateur(this.utilisateur)); // On envoie au serveur nos informations pour qu'il les enregistre

            new Thread(new UserListReceiver(in, this)).start(); // Ce thread sert à recevoir la liste des utilisateurs connectés

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addConversation(Conversation conversation) {
        conversations.add(conversation);
    }

    public ArrayList<Conversation> getConversations() {
        return conversations;
    }

    public ArrayList<Utilisateur> getDestinataires() {
        return destinataires;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setDestinataires(ArrayList<Utilisateur> utilisateurs) {
        this.destinataires = utilisateurs;
    }

    public void leave() {
        System.exit(0);
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
