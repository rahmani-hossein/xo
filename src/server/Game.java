package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.BoardManager;
import model.Board;
import model.GameState;
import model.Request;

import java.util.Random;

public class Game {

    private ObjectMapper objectMapper = new ObjectMapper();
    private ClientHandler clientHandler1;
    private ClientHandler clientHandler2;
    private GameServer gameServer;
    private GameState number1 = null;
    private GameState number2 = null;
    private int token1 = 0;
    private int token2 = 0;
    private BoardManager boardManager = new BoardManager();


    public Game(ClientHandler clientHandler1, ClientHandler clientHandler2, Request num2, Request unhandled, GameServer gameServer) {
        this.clientHandler1 = clientHandler1;
        this.clientHandler2 = clientHandler2;
        this.gameServer = gameServer;

        init(num2, unhandled);
        sendStart();
    }

    private void init(Request num2, Request unhandled) {
        number2 = getGameState(num2.getBody());
        number1 = getGameState(unhandled.getBody());
        if (number2 != null) {
            if (number1 != null) {
                number2.setEnemy(number1.getFriend());
                number1.setEnemy(number2.getFriend());
                Board board = new Board();
                number1.setBoard(board);
                number2.setBoard(board);
                number1.setTurn(true);
                number2.setTurn(false);
                number1.setGameover(0);
                number2.setGameover(0);
                setPieceName();
            }
        }
        token1 = unhandled.getToken();
        token2 = num2.getToken();
    }

    public void sendStart() {


        Request request1 = new Request(token1, "play", null, getStringValueOfGameState(number1));
        request1.setResult(true);
        Request request2 = new Request(token2, "play", null, getStringValueOfGameState(number2));
        request2.setResult(true);
        sendToGame(request1, request2);
    }

    public void executeMove(Request request) {
        if (request.getToken() == token1) {
            number1 = getGameState(request.getBody());
            updateGameStates(number1, number2);
        }
        if (request.getToken() == token2) {
            number2 = getGameState(request.getBody());
            updateGameStates(number2, number1);
        }
        check();
        sendMove();
    }

    private void updateGameStates(GameState gameState1, GameState gameState2) {
        gameState2.setFriend(gameState1.getEnemy());
        gameState2.setEnemy(gameState1.getFriend());
        gameState2.setBoard(gameState1.getBoard());
        gameState2.setTurn(!gameState1.isTurn());
    }

    public void sendMove() {
        Request request1 = new Request(token1, "move", null, getStringValueOfGameState(number1));
        Request request2 = new Request(token2, "move", null, getStringValueOfGameState(number2));
        request1.setResult(true);
        request2.setResult(true);
        sendToGame(request1, request2);
    }

    public void check() {
        if (boardManager.win(number1.getBoard(), number1.getFriendPieceName())) {
            number1.setGameover(1);
            number2.setGameover(2);
            number1.getFriend().setWins(number1.getFriend().getWins()+1);
            number1.getFriend().setNote(number1.getFriend().getNote()+3);
            number2.getFriend().setLosts(number2.getFriend().getLosts()+1);
        } else if (boardManager.win(number2.getBoard(), number2.getFriendPieceName())) {
            number1.setGameover(2);
            number2.setGameover(1);
            number2.getFriend().setWins(number2.getFriend().getWins()+1);
            number2.getFriend().setNote(number2.getFriend().getNote()+3);
            number1.getFriend().setLosts(number1.getFriend().getLosts()+1);
        } else if (boardManager.tie(number1.getBoard())) {
            number1.setGameover(3);
            number2.setGameover(3);
            number1.getFriend().setNote(number1.getFriend().getNote()+1);
            number2.getFriend().setNote(number2.getFriend().getNote()+1);
        } else {
            number1.setGameover(0);
            number2.setGameover(0);
        }
    }

    public void sendToGame(Request request1, Request request2) {
        clientHandler1.send(convertRequest(request1));
        clientHandler2.send(convertRequest(request2));
    }


    private GameState getGameState(String gameStateString) {
        try {
            return objectMapper.readValue(gameStateString, GameState.class);
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

    private String getStringValueOfGameState(GameState gameState) {
        try {
            String message = objectMapper.writeValueAsString(gameState);
            return message;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void setPieceName() {
        Random random = new Random(System.nanoTime());
        int i = random.nextInt(2);
        if (i == 0) {
            number1.setFriendPieceName("gun");
            number1.setEnemyPieceName("rose");
            number2.setEnemyPieceName("gun");
            number2.setFriendPieceName("rose");
        } else {
            number1.setFriendPieceName("rose");
            number1.setEnemyPieceName("gun");
            number2.setEnemyPieceName("rose");
            number2.setFriendPieceName("gun");
        }
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
