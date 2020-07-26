package client;

import model.Request;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {

    private int token;
    private Socket socket;
    private ClientReciever receiver;
    private Sender sender;
    private String serverIP = "localhost";

    public Client(int serverPort) throws IOException {
        socket = new Socket(serverIP, serverPort);
        System.out.println("Connected to Server at: " + serverIP + ":" + serverPort);
    }

    @Override
    public void run() {
        try {
            InputStream socketInputStream = socket.getInputStream();
            PrintStream socketPrinter = new PrintStream(socket.getOutputStream());

            receiver = new ClientReciever(socketInputStream, System.out, this);
            sender = new Sender(socketPrinter);

            receiver.start();
            recieveToken();

            while (isStillAlive()) {
                try {
                    Thread.sleep(100);


                } catch (InterruptedException ignore) {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        receiver.interrupt();
    }

    private void recieveToken() {
        Request request = new Request(0, "token", new ArrayList<>(), "");
        Controller.getInstance().getClient().getSender().send(request);
    }

    private boolean isStillAlive() {
        return socket.isConnected();
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientReciever getReceiver() {
        return receiver;
    }

    public void setReceiver(ClientReciever receiver) {
        this.receiver = receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}
