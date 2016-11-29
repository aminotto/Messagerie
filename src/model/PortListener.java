package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
                socket = serverSocket.accept();
                messagerie.addMessage(new Message("System", "Un utilisateur s'est connecté"));

                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                new Thread(new MessageReceiver(in, messagerie)).start();
                new Thread(new MessageSender(out, messagerie)).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}