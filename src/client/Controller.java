package client;

import client.gui.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.GameState;
import model.Request;
import model.Score;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Controller {

    private static Controller instance =new Controller();
    private MyFrame myFrame;
    private NetworkPanel networkPanel;
    private LoginPanel loginPanel;
    private GamePanel gamePanel;
    private Menu menu;
    private Client client;
    private GameState gameState;
    private ResourceManager resourceManager;
    private ObjectMapper objectMapper;
    private ArrayList<Score> scoreBoard=null;
    private Semaphore semaphore=new Semaphore(1);
    private boolean haveOpponent=false;
    public  static Controller getInstance(){
        return instance;
    }

    private Controller (){
        resourceManager=new ResourceManager();
        objectMapper=new ObjectMapper();
    }



    public String getStringValueOfGameState(GameState gameState){
        try {
            String message=objectMapper.writeValueAsString(gameState);
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void exitGame(){
        int action= JOptionPane.showConfirmDialog(this.getMyFrame(),"do you really want to exit?","Exit Title",JOptionPane.OK_CANCEL_OPTION);
        if (action==JOptionPane.OK_OPTION) {
                ArrayList<String> parameters= new ArrayList<>();
            String playerString= null;
            try {
                playerString = objectMapper.writeValueAsString(gameState.getFriend());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            Request request= new Request(client.getToken(),"logout",parameters,playerString);
                client.getSender().send(request);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public void finish(){
        System.exit(0);
    }


    public Semaphore getSemaphore() {
        return semaphore;
    }

    public void setSemaphore(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    public void setResourceManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public MyFrame getMyFrame() {
        return myFrame;
    }

    public void setMyFrame(MyFrame myFrame) {
        this.myFrame = myFrame;
    }

    public NetworkPanel getNetworkPanel() {
        return networkPanel;
    }

    public void setNetworkPanel(NetworkPanel networkPanel) {
        this.networkPanel = networkPanel;
    }

    public ArrayList<Score> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(ArrayList<Score> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public boolean isHaveOpponent() {
        return haveOpponent;
    }

    public void setHaveOpponent(boolean haveOpponent) {
        this.haveOpponent = haveOpponent;
    }
}
