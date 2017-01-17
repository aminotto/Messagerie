package model;

import java.util.ArrayList;

public class Server {

    private ArrayList<Utilisateur> utilisateurs;

    public Server() {
        utilisateurs = new ArrayList<Utilisateur>();
        /*try {
            JOptionPane.showMessageDialog(null, InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }*/
        waitForConnectionOnPort(2043);
    }

    public ArrayList<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void addUser(Utilisateur utilisateur) {
        utilisateurs.add(utilisateur);
    }

    public void removeUser(Utilisateur utilisateur) {
        utilisateurs.remove(utilisateur);
    }

    public boolean isConnected(Utilisateur utilisateur) {
        for(Utilisateur user : utilisateurs) {
            if(utilisateur.getIpAdrr().equals(user.getIpAdrr()) || utilisateur.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    private void waitForConnectionOnPort(int port) {
        new Thread(new PortListener(port, this)).start();
    }

}
