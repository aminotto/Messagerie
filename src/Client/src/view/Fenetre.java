package view;

import controller.ControllerBarMenu;
import controller.ControllerListUser;
import controller.ControllerMessageSender;
import model.Messagerie;
import model.Utilisateur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.util.ArrayList;

public class Fenetre extends JFrame {

    private Messagerie messagerie;
    private JMenuBar menuBar;
    private JMenu fichier;
    private JMenuItem connexion, quitter;
    private JPanel global, listeUser, chat, listeMessage, sender;
    private JTextField jtf_message;
    private JTextArea jta_listeMessage;
    private JButton envoyer;
    private JScrollPane scrollMessage, scrollUser;
    private JTabbedPane onglets = new JTabbedPane(SwingConstants.TOP);
    private JList liste;
    private DefaultListModel modelList;

    public Fenetre(Messagerie messagerie) {
        this.messagerie = messagerie;
        init();
    }

    private void init() {
        global = new JPanel();
        global.setLayout(new BoxLayout(global, BoxLayout.X_AXIS));
        initMenuBar();
        initChat();
        initListeUser();
        setJMenuBar(menuBar);
        setContentPane(global);
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
        sender = new JPanel();
        sender.setPreferredSize(new Dimension(400, 50));
        jta_listeMessage = new JTextArea("En attente de message...");
        jta_listeMessage.setEditable(false);
        DefaultCaret caret = (DefaultCaret) jta_listeMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.UPDATE_WHEN_ON_EDT);
        scrollMessage = new JScrollPane(jta_listeMessage);
        scrollMessage.setPreferredSize(new Dimension(400, 200));
        scrollMessage.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listeMessage.add(scrollMessage);
        onglets.addTab("Messages", listeMessage);
        jtf_message = new JTextField();
        jtf_message.setPreferredSize(new Dimension(200, 20));
        envoyer = new JButton("Envoyer");
        envoyer.setActionCommand("send");
        sender.add(jtf_message);
        sender.add(envoyer);
        chat = new JPanel();
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.add(onglets);
        chat.add(sender);
        global.add(chat);
    }

    private void initListeUser() {
        listeUser = new JPanel();
        modelList = new DefaultListModel();
        liste = new JList(modelList);
        modelList.addElement("aucune connexion...");
        scrollUser = new JScrollPane(liste);
        scrollUser.setPreferredSize(new Dimension(150, 220));
        scrollUser.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        listeUser.add(scrollUser);
        listeUser.setBorder(new EmptyBorder(15, 0, 0, 0));
        global.add(listeUser);
    }

    public void setControllerBarMenu(ControllerBarMenu controllerBarMenu) {
        connexion.addActionListener(controllerBarMenu);
        quitter.addActionListener(controllerBarMenu);
    }

    public void setControllerMessageSender(ControllerMessageSender controllerMessageSender) {
        envoyer.addActionListener(controllerMessageSender);
    }

    public void setControllerListUser(ControllerListUser controllerListUser) {
        liste.addMouseListener(controllerListUser);
    }

    public String getTextToSend() {
        return jtf_message.getText();
    }

    public void clearMessageField() {
        jtf_message.setText("");
    }

    public int getSelectedTab() {
        return onglets.getSelectedIndex();
    }

    @Override
    public void repaint() {
        updateTab();
        updateUserList();
    }

    private void updateTab() {
        if(!messagerie.getConversations().isEmpty()) {

            int selectedTab = onglets.getSelectedIndex();
            onglets.removeAll();

            for (int i=0; i<messagerie.getConversations().size(); i++) {
                if(selectedTab==i) {
                    onglets.addTab(messagerie.getConversations().get(i).getReceiver().getLogin(), listeMessage);
                    onglets.setSelectedIndex(i);
                }else {
                    onglets.addTab(messagerie.getConversations().get(i).getReceiver().getLogin(), null);
                }
            }
            jta_listeMessage.setText(messagerie.getConversations().get(onglets.getSelectedIndex()).readMessages());
        }
    }

    public void updateUserList() { // Fait correspondre notre JListe avec notre liste de destinataire
        ArrayList<Utilisateur> destinataires = messagerie.getDestinataires();
        if(!destinataires.isEmpty()) {
            if(destinataires.size()>=modelList.size()) {
                for (int i = 0; i < destinataires.size(); i++) {
                    if (i >= modelList.size()) {
                        modelList.addElement(destinataires.get(i));
                    } else if (!modelList.getElementAt(i).equals(destinataires.get(i))) {
                        modelList.setElementAt(destinataires.get(i), i);
                    }
                }
            }
            else {
                for(int i=destinataires.size(); i<modelList.size(); i++) {
                    modelList.removeElementAt(i);
                }
            }
        }
    }
}
