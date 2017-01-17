package model;

import security.RSA;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PublicKey;

public class PortListener implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    private Messagerie messagerie;

    public PortListener(int port, Messagerie messagerie) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.messagerie=messagerie;
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept(); // On attend d'être contacté par un correspondant
                Conversation conversation = new Conversation();
                conversation.addMessage(new Message("System", "Un utilisateur vient de se connecter"));

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                try {

                    conversation.setReceiver((Utilisateur)in.readObject()); // On récupère les infos envoyées par notre correspondant
                    //on envoie notre cle publique
                    out.writeObject(messagerie.getUtilisateur().getPublicKey()); // On envoie notre public key pour qu'il nous envoie la cle RSA
                    out.flush();
                    //on recup la cle rsa codee et on la decode
                    SecretKey secretKey = RSA.decryptAESKey((byte[]) in.readObject(),messagerie.getUtilisateur().getPrivateKey());
                    conversation.setAesKey(secretKey); //on set la cle AES dans la conversation

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                messagerie.addConversation(conversation);

                new Thread(new MessageReceiver(in, conversation)).start();
                new Thread(new MessageSender(out, conversation)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}