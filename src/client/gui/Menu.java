package client.gui;

import client.ClientConstants;
import client.Controller;
import model.Request;
import model.Score;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Menu extends JPanel {

    private ClientConstants clientConstants = Controller.getInstance().getResourceManager().getClientConstants();
    private JTable jTable;
    private JScrollPane scrollPane;
    private JButton exit;
    private JButton play;
    private JButton refresh;
    private ArrayList<Score> list = new ArrayList<>();

    public Menu() {
        setLayout(null);
        setBackground(Color.black);
        initTable();
        initExit();
        initPlay();
        initRefresh();
    }

    private void initTable() {
        sendMessage();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list = Controller.getInstance().getScoreBoard();
        System.out.println(list.get(0).getOnline());

        Collections.sort(list);
        String columon[] = {"userName:", "status:", "score:"};
        DefaultTableModel defaultTableModel = new DefaultTableModel(columon, 0);

        for (int i = 0; i < list.size(); i++) {
            Object[] objects = {"  " + list.get(i).getUserName(), "  " + list.get(i).getOnline(), "  " + list.get(i).getScore()};
            defaultTableModel.addRow(objects);
        }

        jTable = new JTable(defaultTableModel);
        jTable.setFont(new Font("Serif", Font.BOLD, 20));
        jTable.setRowHeight(40);
        jTable.setPreferredSize(new Dimension(clientConstants.getSizeBoard(), clientConstants.getSizeBoard()));
        initScroll();

    }

    private void initScroll() {
        scrollPane = new JScrollPane(jTable);
        scrollPane.setWheelScrollingEnabled(true);
        scrollPane.setPreferredSize(new Dimension(clientConstants.getSizeBoard(), clientConstants.getSizeBoard()));
        scrollPane.setBounds(clientConstants.getPanelWidth() - clientConstants.getSizeBoard(), clientConstants.getNetworkPanelMinx(), clientConstants.getSizeBoard(), clientConstants.getSizeBoard());
        add(scrollPane);
    }

    private void initPlay() {
        play = new JButton("play");
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String jsonGameState = Controller.getInstance().getStringValueOfGameState(Controller.getInstance().getGameState());
                Request playreq = new Request(Controller.getInstance().getClient().getToken(), "play", null, jsonGameState);
                Controller.getInstance().getClient().getSender().send(playreq);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        play.setBounds(100, 500, 100, 60);
        add(play);
    }

    private void initExit() {
        exit = new JButton("exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().exitGame();
            }
        });
        exit.setBounds(500, 500, 100, 60);
        add(exit);
    }

    private void initRefresh() {
        refresh = new JButton("refresh");
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initTable();
            }
        });
        refresh.setBounds(300, 500, 100, 60);
        add(refresh);
    }

    private void sendMessage() {
        Request request = new Request(Controller.getInstance().getClient().getToken(), "scoreboard", new ArrayList<>(), "");
        Controller.getInstance().getClient().getSender().send(request);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawString("username: " + Controller.getInstance().getGameState().getFriend().getUsername(), 10, 10);
        g.drawString("password: " + Controller.getInstance().getGameState().getFriend().getPassword(), 10, 10 + 40);
        g.drawString("score: " + Controller.getInstance().getGameState().getFriend().getNote(), 10, 10 + 80);
        g.drawString("state: " + Controller.getInstance().getGameState().getFriend().getState(), 10, 10 + 120);
        g.drawString("wins: " + Controller.getInstance().getGameState().getFriend().getWins(), 10, 10 + 160);
        g.drawString("losts:" + Controller.getInstance().getGameState().getFriend().getLosts(), 10, 10 + 200);

    }


    public ArrayList<Score> getList() {
        return list;
    }

    public void setList(ArrayList<Score> list) {
        this.list = list;
    }
}
