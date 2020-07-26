package server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import logic.PlayerManager;
import model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler extends Thread {

    private boolean alive=true;
    private Socket socket;
    private GameServer gameServer;
    private Game game;
    private String clientName;
    private boolean acceptPlay;
    private PrintStream printer;
    private ObjectMapper objectMapper= new ObjectMapper();
    private PlayerManager playerManager = new PlayerManager();


    ClientHandler(GameServer gameServer, Socket socket) {
        this.gameServer = gameServer;
        this.socket = socket;
        clientName = socket.getRemoteSocketAddress().toString();
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            printer = new PrintStream(socket.getOutputStream());

            while (alive) {
                String message = scanner.nextLine();
                Request request = getRequest(message);
                executeRequestServer(request);
                //  System.out.println("Client at " + socket.getRemoteSocketAddress().toString() + " sent " + message + ".");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkGame(Request request) {
        try {
            gameServer.getSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        gameServer.getAcceptForPlay().add(this);
        if (gameServer.getAcceptForPlay().size() == 2) {
            ClientHandler waited=gameServer.getAcceptForPlay().remove(0);
            request.setResult(true);
            Game game = new Game(waited, gameServer.getAcceptForPlay().remove(0),request,gameServer.getUnhandled(), gameServer);
            this.game = game;
            waited.setGame(game);
            gameServer.getAvailableGames().add(game);
        }
        if (gameServer.getAcceptForPlay().size()==1){

            Request request1= new Request(request.getToken(),"waiting",null,request.getBody());
            request1.setResult(true);
            gameServer.setUnhandled(request1);
            send(convertRequest(request1));
        }
        gameServer.getSemaphore().release();
    }

    private Request getRequest(String message) {
        try {
            return objectMapper.readValue(message, Request.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void send(String message) {
        printer.println(message);
    }


    private void executeRequestServer(Request request) {
        switch (request.getName()) {
            case "token":
                int token = playerManager.createToken();
                request.setToken(token);
                request.setResult(true);
                send(convertRequest(request));
                System.out.println("we send token");
                break;
            case "login":
                String username = request.getParameters().get(0);
                String password = request.getParameters().get(1);
                boolean isAccount = request.getParameters().get(2).equals("true");
                if (playerManager.login(username, password, isAccount)) {
                    GameState gameState = playerManager.getCurrentGameState();
                    if (!isAccount){
                    gameServer.getPlayers().add(gameState.getFriend());}
                    gameServer.changeState(gameState.getFriend());
                        try {
                        String gameStateString = objectMapper.writeValueAsString(gameState);
                        Request request2 = new Request(request.getToken(), "login", new ArrayList<>(), gameStateString);
                        request2.setResult(true);
                        send(convertRequest(request2));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("we login player");
                } else {
                    Request request2 = new Request(request.getToken(), "login", new ArrayList<>(), "");
                    request2.setResult(false);
                    send(convertRequest(request));
                }
                break;
            case "scoreboard":
                ArrayList<Score> scoreBoard = new ArrayList<>();
                for (int i = 0; i < gameServer.getPlayers().size(); i++) {
                    Score score = new Score(gameServer.getPlayers().get(i).getUsername(), gameServer.getPlayers().get(i).getNote(), gameServer.getPlayers().get(i).getState());
                    scoreBoard.add(score);
                }
                try {
                    String scoreBoardString = objectMapper.writeValueAsString(scoreBoard);
                    request.setBody(scoreBoardString);
                    request.setResult(true);
                    send(convertRequest(request));
                        System.out.println(scoreBoardString);
                    System.out.println("finish handeling list");
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                break;
            case "logout":
                try {
                    Player player= objectMapper.readValue(request.getBody(), Player.class);
                    gameServer.changeState(player);
                    playerManager.exit(player);
                    request.setResult(true);
                    send(convertRequest(request));
                    alive=false;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "play":
                acceptPlay=true;
                if (game != null) {

                }
                else {
                    checkGame(request);
                }
                break;
        }
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

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public PrintStream getPrinter() {
        return printer;
    }

    public void setPrinter(PrintStream printer) {
        this.printer = printer;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public boolean isAcceptPlay() {
        return acceptPlay;
    }

    public void setAcceptPlay(boolean acceptPlay) {
        this.acceptPlay = acceptPlay;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void setPlayerManager(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
