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
                Conversation conversation = new Conversation();
                conversation.setReceiverName(socket.getRemoteSocketAddress().toString());
                conversation.addMessage(new Message("System", "Un utilisateur vient de se connecter"));
                messagerie.addConversation(conversation);

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
}