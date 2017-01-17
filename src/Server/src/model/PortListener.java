package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    private Server server;

    public PortListener(int port, Server server) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.server=server;
    }

    @Override
    public void run() {
        while (true) {
            try {
                socket = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                Utilisateur utilisateur = (Utilisateur) in.readObject();
                utilisateur.setIpAdrr(socket.getRemoteSocketAddress().toString().split(":")[0].substring(1)); // récupère l'adresse du client via le socket

                if(!server.isConnected(utilisateur)) {
                    server.addUser(utilisateur);
                    new Thread(new Sender(out, server, utilisateur)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}