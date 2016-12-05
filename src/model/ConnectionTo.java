package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTo implements Runnable {

    private String ip;
    private int port;
    private Socket socket;
    private Conversation conversation;

    public ConnectionTo(String ip, int port, Conversation conversation) {
        this.ip=ip;
        this.port=port;
        this.conversation=conversation;
    }

    @Override
    public void run() {

        try {
            socket = new Socket(ip, port);
            conversation.setReceiverName(socket.getRemoteSocketAddress().toString());
            conversation.addMessage(new Message("System", "Connexion réussie !"));

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            new Thread(new MessageReceiver(in, conversation)).start();
            new Thread(new MessageSender(out, conversation)).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
