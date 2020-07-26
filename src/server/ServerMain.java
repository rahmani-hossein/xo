package server;

import java.io.IOException;

public class ServerMain {

    public static void main(String[] args) {
        ServerConstants serverConstants = new ServerConstants();
        GameServer gameServer = new GameServer(serverConstants.getPort());
        gameServer.setServerConstants(serverConstants);
        gameServer.start();
    }
}
