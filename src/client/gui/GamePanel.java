package client.gui;

import client.ClientConstants;
import client.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {

    private JButton exit;
    private ClientConstants clientConstants = Controller.getInstance().getResourceManager().getClientConstants();

    public GamePanel(){
        setLayout(null);
        initExit();
    }

    private void initExit(){
        exit=new JButton("exit");
        exit.setBounds(100,clientConstants.getPanelWidth(),70,50);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().exitGame();
            }
        });
        add(exit);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
