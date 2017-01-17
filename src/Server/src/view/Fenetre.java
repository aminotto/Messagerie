package view;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Fenetre extends JFrame{

    public Fenetre(){
        this.setTitle("Serveur"); //On donne un titre à l'application
        this.setSize(700,500); //On donne une taille à notre fenêtre
        this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        this.setResizable(false); //On interdit la redimensionnement de la fenêtre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        this.setContentPane(buildContentPane());
        this.setVisible(true);
    }

    private Container buildContentPane(){
        Container panel = new Container();
        panel.setLayout(new FlowLayout());
        panel.setBackground(Color.white);

        String ip= "<html>";
        JLabel label;
        Box boite = Box.createVerticalBox();

        Enumeration e = null;
        try {
            e = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e1) {
            e1.printStackTrace();
        }

        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                label = new JLabel("<html>"+i.getHostAddress()+"</br></html>");
                boite.add(label);
            }
        }

        boite.add(Box.createVerticalGlue());
        setLocationRelativeTo(this.getParent());
        panel.add(boite);

        return panel;
    }
}