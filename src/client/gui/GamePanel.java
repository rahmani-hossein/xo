package client.gui;

import client.ClientConstants;
import client.Controller;
import model.GameState;
import model.Piece;
import model.Request;
import util.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GamePanel extends JPanel implements MouseListener {

    private JButton exit;
    private ClientConstants clientConstants = Controller.getInstance().getResourceManager().getClientConstants();
    private Picture[][] pictures = new Picture[7][7];
    private GameState gameState = Controller.getInstance().getGameState();

    public GamePanel() {
        setLayout(null);
        initExit();
        addMouseListener(this);
    }

    private void initExit() {
        exit = new JButton("exit");
        exit.setBounds(500, clientConstants.getPanelWidth() + 60, 70, 50);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.getInstance().exitGame();
            }
        });
        add(exit);
    }

    private void initPictures() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (Controller.getInstance().getGameState().getBoard().getPieces()[i + 3][j + 3] != null) {
                    pictures[i][j] = new Picture(Controller.getInstance().getGameState().getBoard().getPieces()[i + 3][j + 3]);
                } else {
                    pictures[i][j] = new Picture(i * 100, 60+j * 100);
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        initPictures();
        for (int i = 0; i < pictures[0].length; i++) {
            for (int j = 0; j < pictures[1].length; j++) {
                if (pictures[i][j].getName() != null) {
                    pictures[i][j].paint(g);
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            g.drawLine((i + 1) * 100, 60, (i + 1) * 100, clientConstants.getPanelWidth() + 60);
        }
        for (int j = 0; j < 7; j++) {
            g.drawLine(0, (j + 1) * 100 + 60, clientConstants.getPanelWidth(), (j + 1) * 100 + 60);
        }

        g.drawString("opponent piece: "+Controller.getInstance().getGameState().getEnemyPieceName(),20,20);
        g.drawString("opponent name: "+Controller.getInstance().getGameState().getEnemy().getUsername(),300,20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Controller.getInstance().getGameState().isTurn() || Controller.getInstance().getGameState().getGameover() >= 1) {
            for (int i = 0; i < pictures[0].length; i++) {
                for (int j = 0; j < pictures[1].length; j++) {
                    if ((pictures[i][j].getX() <= e.getX()) && (pictures[i][j].getX() + pictures[i][j].getSizeX() >= e.getX()) && (pictures[i][j].getY() <= e.getY()) && (pictures[i][j].getSizeY() + pictures[i][j].getY() >= e.getY())) {
                        if (pictures[i][j].getName() != null) {
                            JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(), "it is not allowed move", "forbidden move", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Controller.getInstance().getGameState().getBoard().getPieces()[i + 3][j + 3]=new Piece( Controller.getInstance().getGameState().getFriendPieceName(),new Location(pictures[i][j].getX(), pictures[i][j].getY()));
                            Controller.getInstance().getGameState().setTurn(false);
                            //request move
                            String gameStateJson = Controller.getInstance().getStringValueOfGameState(Controller.getInstance().getGameState());
                            Request request = new Request(Controller.getInstance().getClient().getToken(), "move", null, gameStateJson);
                            Controller.getInstance().getClient().getSender().send(request);
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            if (gameState.getGameover() >= 1) {
                JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(), "game was finished .", "turn", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(), "it is not your turn .", "turn", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
