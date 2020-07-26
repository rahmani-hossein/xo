package client;

import client.gui.GamePanel;
import client.gui.Menu;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.GameState;
import model.Request;
import model.Score;

import javax.swing.*;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ClientReciever extends Thread {

     private InputStream inputStream;
   private PrintStream printStream;
   private Client client;
   private ObjectMapper objectMapper= new ObjectMapper();

    ClientReciever(InputStream inputStream, PrintStream printStream, Client client) {
        this.inputStream = inputStream;
        this.printStream = printStream;
        this.client = client;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(inputStream);

        while (!isInterrupted()) {

            String message = scanner.nextLine();

            try {
                Request request = objectMapper.readValue(message,Request.class);
                executeRequest(request);
                System.out.println("Received: " + message);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
          //  printStream.println(message);

        }
    }

    public void executeRequest(Request request) {
        if (request.isResult()) {
            switch (request.getName()) {
                case "token":
                    client.setToken(request.getToken());
                    System.out.println("we execute successfully token request");
                    break;
                case "login":
                    try {
                        GameState gameState = objectMapper.readValue(request.getBody(), GameState.class);
                        Controller.getInstance().setGameState(gameState);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                case "logout":
                    Controller.getInstance().finish();
                    System.exit(0);
                    break;
                case "scoreboard":
                    try {
                        System.out.println(request.getBody());
                        ArrayList<Score> scoreBoard=new ArrayList<>();
                              scoreBoard=  objectMapper.readValue(request.getBody(),new TypeReference<ArrayList<Score>>(){} );
                       Controller.getInstance().setScoreBoard(scoreBoard);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    break;
                case "waiting":
                    Controller.getInstance().setHaveOpponent(true);
                    JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(),"wait for enother client ","play waiting",JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "play":
                    GameState gameState=getObject(request.getBody());
                    if (gameState!=null){
                        Controller.getInstance().setGameState(gameState);
                       // Controller.getInstance().getMyFrame().getLock().notify();
                        GamePanel gamePanel = new GamePanel();
                        Controller.getInstance().setGamePanel(gamePanel);
                        Controller.getInstance().getMyFrame().getMainpanel().add(gamePanel, "gamepanel");
                        Controller.getInstance().getMyFrame().setPanel("gamepanel");
                    }
                    else {
                        JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(),"error getting gamestate","networkError",JOptionPane.ERROR_MESSAGE);
                    }
                    break;
            }
        }
        else {
            JOptionPane.showMessageDialog(Controller.getInstance().getMyFrame(),"error of recieved request result is false","result",JOptionPane.ERROR_MESSAGE);
        }
    }

private GameState getObject(String jsonValue){
    try {
        return objectMapper.readValue(jsonValue,GameState.class);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
    }
    return null;
}


    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
