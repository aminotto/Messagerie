package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTo implements Runnable {

    private String ip;
    private int port;
    private Socket socket;
    private Messagerie messagerie;

    public ConnectionTo(String ip, int port, Messagerie messagerie) {
        this.ip=ip;
        this.port=port;
        this.messagerie=messagerie;
    }

    @Override
    public void run() {

        try {
            socket = new Socket(ip, port);

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
