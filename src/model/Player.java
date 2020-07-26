package model;

public class Player {

    private String username;
    private String password;
    private int wins;
    private int losts;
    private int note;
    private String state="offline";


    public Player(String username, String password, int wins, int losts, int note) {
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.losts = losts;
        this.note = note;
    }

    public Player() {

    }

    //getter&setter

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosts() {
        return losts;
    }

    public void setLosts(int losts) {
        this.losts = losts;
    }
}
