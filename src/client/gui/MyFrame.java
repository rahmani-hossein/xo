package client.gui;

import client.Controller;
import client.ResourceManager;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {

    private final String NETWORK_PANEL="networkPanel";
    private CardLayout cardLayout;
    private NetworkPanel networkPanel =new NetworkPanel();
    private Object lock= new Object();


    private JPanel mainpanel;


    private void createPanel() {
        try {

            cardLayout = new CardLayout();
            mainpanel = new JPanel(cardLayout);
            setContentPane(mainpanel);
            Controller.getInstance().getMyFrame().getMainpanel().add(networkPanel,NETWORK_PANEL);

//            mainpanel.add(collection,COLLECTION_PANEL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPanel(String panelName) {
        cardLayout.show(mainpanel, panelName);
    }

    public MyFrame(){
        super("TicTacToe");// esme kharegi:)))
        Controller.getInstance().setMyFrame(this);
        setSize(Controller.getInstance().getResourceManager().getClientConstants().getPanelWidth(), Controller.getInstance().getResourceManager().getClientConstants().getPanelHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        createPanel();
        setVisible(true);
    }

//getter&setter

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    public JPanel getMainpanel() {
        return mainpanel;
    }

    public void setMainpanel(JPanel mainpanel) {
        this.mainpanel = mainpanel;
    }

    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    public void setNetworkPanel(NetworkPanel networkPanel) {
        this.networkPanel = networkPanel;
    }

    public Object getLock() {
        return lock;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }
}
