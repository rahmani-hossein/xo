package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Board;
import model.GameState;
import model.Request;

public class Game {

    private ObjectMapper objectMapper=new ObjectMapper();
    private ClientHandler clientHandler1;
    private ClientHandler clientHandler2;
    private GameServer gameServer;
     private GameState number1=null;
private GameState number2=null;
private int token1=0;
private int token2=0;
    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2, Request num2,Request unhandled, GameServer gameServer) {
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
        this.gameServer = gameServer;

       init(num2,unhandled);
       sendStart();
    }

    private void init(Request num2,Request unhandled){
         number2= getGameState(num2.getBody());
         number1 =getGameState(unhandled.getBody());
        if (number2 != null) {
            if (number1 != null) {
                number2.setEnemy(number1.getFriend());
                number1.setEnemy(number2.getFriend());
                Board board=new Board();
                number1.setBoard(board);
                number2.setBoard(board);
                number1.setTurn(true);
                number2.setTurn(false);
            }
        }
        token1=unhandled.getToken();
        token2=num2.getToken();
    }

    public void sendStart(){


        Request request1=new Request(token1,"play",null,getStringValueOfGameState(number1));
        request1.setResult(true);
        Request request2= new Request(token2,"play",null,getStringValueOfGameState(number2));
        request2.setResult(true);
        sendToGame(request1,request2);
    }

    public void sendToGame(Request request1,Request request2){
        clientHandler1.send(convertRequest(request1));
        clientHandler2.send(convertRequest(request2));
    }


    private GameState getGameState(String gameStateString){
        try {
            return objectMapper.readValue(gameStateString,GameState.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String convertRequest(Request request) {
        try {
            String message = objectMapper.writeValueAsString(request);
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getStringValueOfGameState(GameState gameState){
        try {
            String message=objectMapper.writeValueAsString(gameState);
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ClientHandler getClientHandler1() {
        return clientHandler1;
    }

    public void setClientHandler1(ClientHandler clientHandler1) {
        this.clientHandler1 = clientHandler1;
    }

    public ClientHandler getClientHandler2() {
        return clientHandler2;
    }

    public void setClientHandler2(ClientHandler clientHandler2) {
        this.clientHandler2 = clientHandler2;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }
}
