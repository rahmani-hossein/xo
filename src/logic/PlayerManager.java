package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.GameState;
import model.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayerManager {

    private ObjectMapper objectMapper = new ObjectMapper();
    private SecureRandom secureRandom = new SecureRandom();
    private GameState currentGameState=null;

    public PlayerManager() {

    }


    public int createToken() {
        return secureRandom.nextInt();
    }


    public boolean login(String username, String password, boolean isAccount) {
        if (isAccount) {
            String user = username;
            String pass = password;
            String st = String.format("resources/user/userJson/%s.json", user + pass);
            File file = new File(st);

            if (file.exists()) {
                String account = String.format("resources/user/userText/%s.txt", user + pass);
                try {
                    FileWriter fileWriter = new FileWriter(account, true);
                    fileWriter.write("signed in at:" + time() + "\n");
                    fileWriter.flush();
                    fileWriter.close();

                    Player player = objectMapper.readValue(file, Player.class);
                    player.setState("online");
                    GameState gameState =new GameState();
                    gameState.setFriend(player);
                    currentGameState=gameState;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            } else {
                System.out.println("you dont have account please try again and  build an account ");
                return false;
            }
        } else {// account nadareh
            Player player=initialPlayer(username, password);
            player.setState("online");
            GameState gameState =new GameState();
            gameState.setFriend(player);
            currentGameState=gameState;
            fileHandler(player);
            setList(player);
            return true;
        }
    }

    public Player initialPlayer(String username, String password) {
        Player player = new Player(username, password, 0, 0, 0);
        return player;
    }
    private void setList(Player player){
        try {
            FileWriter fileWriter =new FileWriter("resources/user/player.txt",true);
            fileWriter.write(player.getUsername()+player.getPassword()+"\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileHandler(Player player) {
        String st1 = String.format("resources/user/userText/%s.txt", player.getUsername() + player.getPassword());
        String st2 = String.format("resources/user/userJson/%s.json", player.getUsername() + player.getPassword());
        String st3 = "resources/user/player.txt";


        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(st1, true);

            fileWriter.flush();
            fileWriter.write("username: " + player.getUsername() + "\n");
            fileWriter.write("Created_at: " + time() + "\n");
            fileWriter.write("password: " + player.getPassword() + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fileWriter1 = null;
        try {
            fileWriter1 = new FileWriter(st2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter fileWriter2=null;

        try {
            objectMapper.writeValue(fileWriter1, player);

            fileWriter1.close();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String time() {
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        return ft.format(date);
    }

    public void exit(Player player) throws IOException {
        player.setState("offline");
        ObjectMapper objectMapper = new ObjectMapper();
        String account = String.format("resources/user/userJson/%s.json", player.getUsername() + player.getPassword());
        FileWriter fileWriter = new FileWriter(account, false);
        objectMapper.writeValue(fileWriter, player);
        fileWriter.close();
        String account2 = String.format("resources/user/userText/%s.txt", player.getUsername() + player.getPassword());
        FileWriter file2 = new FileWriter(account2, true);
        file2.write("signed up at:" + time());
        file2.flush();
        file2.close();

    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setCurrentGameState(GameState currentGameState) {
        this.currentGameState = currentGameState;
    }
}
