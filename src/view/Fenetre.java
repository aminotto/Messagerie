package view;

import controller.ControllerBarMenu;
import controller.ControllerMessageSender;
import model.Message;
import model.Messagerie;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Fenetre extends JFrame {

    private Messagerie messagerie;
    private JMenuBar menuBar;
    private JMenu fichier;
    private JMenuItem connexion, quitter;
    private JPanel chat, listeMessage, sender;
    private JTextField jtf_message;
    private JTextArea jta_listeMessage;
    private JButton envoyer;
    private JScrollPane scroll;

    public Fenetre(Messagerie messagerie) {
        this.messagerie = messagerie;
        initMenuBar();
        initChat();
        setJMenuBar(menuBar);
        setContentPane(chat);
        pack();
        setTitle("Messagerie");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initMenuBar() {

        fichier = new JMenu("Fichier");
        connexion = new JMenuItem("Connexion");
        connexion.setActionCommand("connect");
        quitter = new JMenuItem("Quitter");
        quitter.setActionCommand("leave");
        fichier.add(connexion);
        fichier.add(quitter);
        menuBar = new JMenuBar();
        menuBar.add(fichier);
    }

    private void initChat() {

        listeMessage = new JPanel();
        listeMessage.setPreferredSize(new Dimension(500, 200));
        sender = new JPanel();
        sender.setPreferredSize(new Dimension(500, 50));
        jta_listeMessage = new JTextArea("En attente de message...");
        jta_listeMessage.setEditable(false);
        DefaultCaret caret = (DefaultCaret) jta_listeMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
        scroll = new JScrollPane(jta_listeMessage);
        scroll.setPreferredSize(new Dimension(480, 180));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listeMessage.add(scroll);
        jtf_message = new JTextField();
        jtf_message.setPreferredSize(new Dimension(300, 20));
        envoyer = new JButton("Envoyer");
        envoyer.setActionCommand("send");
        sender.add(jtf_message);
        sender.add(envoyer);
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.add(listeMessage);
        chat.add(sender);
    }

    public void setControllerBarMenu(ControllerBarMenu controllerBarMenu) {
        connexion.addActionListener(controllerBarMenu);
        quitter.addActionListener(controllerBarMenu);
    }

    public void setControllerMessageSender(ControllerMessageSender controllerMessageSender) {
        envoyer.addActionListener(controllerMessageSender);
    }

    public String getTextToSend() {
        return jtf_message.getText();
    }

    public void clearMessageField() {
        jtf_message.setText("");
    }

    @Override
    public void repaint() {
        jta_listeMessage.setText(messagerie.readMessages());
    }
}
