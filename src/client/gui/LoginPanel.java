package client.gui;

import client.ClientConstants;
import client.Controller;
import model.Request;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LoginPanel extends JPanel {

    private ClientConstants clientConstants = Controller.getInstance().getResourceManager().getClientConstants();
    private BufferedImage background;
    private JButton login;
    private JButton create;
    private JTextField username;
    private JTextField password;
    private JButton exit;

    public LoginPanel() {
        setSize(clientConstants.getPanelWidth(), clientConstants.getPanelHeight());
        setBackground(Color.BLACK);
        setLayout(null);
        initBackground();
        createAccount();
        createExit();
        createLogin();
        createPassword();
        createUsername();
    }


    private void createExit() {
        exit = new JButton("exit");
        exit.setBounds(clientConstants.getPanelWidth() - clientConstants.getNetworkPanelMinx(), 3 * clientConstants.getNetworkPanelSizey(), clientConstants.getNetworkPanelMinx() , clientConstants.getNetworkPanelSizey());
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().getClient().setToken(0);
                System.exit(0);
            }
        });
        add(exit);
    }

    private void createLogin() {
        login = new JButton("login");
        login.setBounds(clientConstants.getNetworkPanelMinx() + (clientConstants.getNetworkPanelMinx() / 5), clientConstants.getNetworkPanelMinY() + 5 * clientConstants.getNetworkPanelSizey() + 4 * clientConstants.getNetworkPanelMinx() / 10, 7 * clientConstants.getNetworkPanelMinx() / 10, 3 * clientConstants.getNetworkPanelMinx() / 10);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (username.getText() != null && username.getText().length() >= 1 && password.getText() != null && password.getText().length() >= 1) {
                    ArrayList<String> parameters = new ArrayList<>();
                    parameters.add(username.getText());
                    parameters.add(password.getText());
                    parameters.add("true");
                    Request request = new Request(Controller.getInstance().getClient().getToken(), "login", parameters, "  ");
                    Controller.getInstance().getClient().getSender().send(request);
                    Menu menu= new Menu();
                    Controller.getInstance().setMenu(menu);
                    Controller.getInstance().getMyFrame().add(menu,"menu");
                    Controller.getInstance().getMyFrame().setPanel("menu");
                } else {
                    JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(), "there isnt a username or password", "loginError", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(login);
    }

    private void createUsername() {
        username = new JTextField(2*clientConstants.getNetworkPanelSizey());
        username.setBounds(clientConstants.getNetworkPanelMinx() + 3 * (clientConstants.getNetworkPanelMinx() / 10), clientConstants.getNetworkPanelMinY(),2* clientConstants.getNetworkPanelSizey(), clientConstants.getNetworkPanelSizey());
        add(username);
    }

    private void createPassword() {
        password = new JTextField(2*clientConstants.getNetworkPanelSizey());
        password.setBounds(clientConstants.getNetworkPanelMinx() + 3 * (clientConstants.getNetworkPanelMinx() / 10), clientConstants.getNetworkPanelMinY() + 2 * clientConstants.getNetworkPanelSizey(),2* clientConstants.getNetworkPanelSizey(), clientConstants.getNetworkPanelSizey());
        add(password);
    }

    private void createAccount() {
        create = new JButton("create");
        create.setBounds(clientConstants.getNetworkPanelMinx() + (clientConstants.getNetworkPanelMinx() / 5), clientConstants.getNetworkPanelMinY() + 5 * clientConstants.getNetworkPanelSizey(), 14 * clientConstants.getNetworkPanelMinx() / 10, 3 * clientConstants.getNetworkPanelMinx() / 10);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (username.getText() != null && username.getText().length() >= 1 && password.getText() != null && password.getText().length() >= 1) {
                    ArrayList<String> parameters = new ArrayList<>();
                    parameters.add(username.getText());
                    parameters.add(password.getText());
                    parameters.add("false");
                    Request request = new Request(Controller.getInstance().getClient().getToken(), "login", parameters, "");
                    Controller.getInstance().getClient().getSender().send(request);
                    Menu menu= new Menu();
                    Controller.getInstance().setMenu(menu);
                    Controller.getInstance().getMyFrame().add(menu,"menu");
                    Controller.getInstance().getMyFrame().setPanel("menu");
                } else {
                    JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(), "there isnt a username or password", "loginError", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(create);
    }


    private void initBackground() {
        background = Controller.getInstance().getResourceManager().getImage("gunsNroses");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawString("username:", clientConstants.getNetworkPanelMinx(), clientConstants.getNetworkPanelMinY());
        g.drawString("password", clientConstants.getNetworkPanelMinx(), clientConstants.getNetworkPanelMinY() + 2 * clientConstants.getNetworkPanelSizey());
    }
}
