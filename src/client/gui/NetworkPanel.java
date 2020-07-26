package client.gui;

import client.Client;
import client.ClientConstants;
import client.Controller;
import model.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class NetworkPanel extends JPanel {

    private ClientConstants clientConstants = Controller.getInstance().getResourceManager().getClientConstants();
    private BufferedImage background;
    private JButton connect;
    private JTextField ipField;
    private JTextField portField;
    private JButton exit;
    private int portId=clientConstants.getPort();// baiad hatman ghabl az vasl shodan port ro bedonim.

    public NetworkPanel() {
        setSize(clientConstants.getPanelWidth(),clientConstants.getPanelHeight());
        setLayout(null);
        createIpField();
        createPortField();
        createExit();
        createConnect();
        initBackground();
        setBackground(Color.BLACK);

    }

    private void initBackground() {
        background = Controller.getInstance().getResourceManager().getImage("skelet");
    }

    private void createIpField() {
        ipField = new JTextField(2*clientConstants.getNetworkPanelSizey());
        ipField.setBounds(clientConstants.getNetworkPanelMinx() + 3 * (clientConstants.getNetworkPanelMinx() / 10), clientConstants.getNetworkPanelMinY(), 2*clientConstants.getNetworkPanelSizey(), clientConstants.getNetworkPanelSizey());
        add(ipField);
    }

    private void createPortField() {
        portField = new JTextField(2*clientConstants.getNetworkPanelSizey());
        portField.setBounds(clientConstants.getNetworkPanelMinx() + 3 * (clientConstants.getNetworkPanelMinx() / 10), clientConstants.getNetworkPanelMinY() + 2 * clientConstants.getNetworkPanelSizey(), 2*clientConstants.getNetworkPanelSizey(), clientConstants.getNetworkPanelSizey());
        add(portField);
    }

    private void createExit() {
        exit = new JButton("exit");
        exit.setBounds(clientConstants.getPanelWidth()-clientConstants.getNetworkPanelMinx(),3*clientConstants.getNetworkPanelSizey(),clientConstants.getNetworkPanelMinx(),clientConstants.getNetworkPanelSizey());
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(exit);
    }

    private void createConnect() {
        connect = new JButton("connect");
        connect.setBounds(clientConstants.getNetworkPanelMinx() + (clientConstants.getNetworkPanelMinx() / 5),clientConstants.getNetworkPanelMinY() + 5 * clientConstants.getNetworkPanelSizey(),14*clientConstants.getNetworkPanelMinx()/10,3*clientConstants.getNetworkPanelMinx()/10);
        connect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (portField.getText()!=null&&portField.getText().length()>=1){
                    if (Integer.parseInt(portField.getText())==portId){
                       createClient();
                      createLoginPanel();
                    }
                    else {
                        JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(),"your port is invalid","wrongPort",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(),"your port doesnt exist","noPort",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(connect);
    }

    private void createClient(){
        Client client = null;
        try {
            client = new Client(portId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.start();
        Controller.getInstance().setClient(client);


    }
    private void createLoginPanel(){
        LoginPanel loginPanel=new LoginPanel();
        Controller.getInstance().getMyFrame().add(loginPanel,"login");
        Controller.getInstance().setLoginPanel(loginPanel);
        Controller.getInstance().getMyFrame().setPanel("login");
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawString("ip",clientConstants.getNetworkPanelMinx(), clientConstants.getNetworkPanelMinY());
        g.drawString("port",clientConstants.getNetworkPanelMinx(), clientConstants.getNetworkPanelMinY() + 2 * clientConstants.getNetworkPanelSizey());
    }


}
