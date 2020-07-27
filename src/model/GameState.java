package model;

public class GameState {

    private Player friend;
    private Player enemy;
    private boolean turn;
    private Board board =new Board();
    private String friendPieceName;
    private String enemyPieceName;
    private int  gameover;

    public int getGameover() {
        return gameover;
    }

    public void setGameover(int gameover) {
        this.gameover = gameover;
    }

    public Player getFriend() {
        return friend;
    }

    public void setFriend(Player friend) {
        this.friend = friend;
    }

    public Player getEnemy() {
        return enemy;
    }

    public void setEnemy(Player enemy) {
        this.enemy = enemy;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getFriendPieceName() {
        return friendPieceName;
    }

    public void setFriendPieceName(String friendPieceName) {
        this.friendPieceName = friendPieceName;
    }

    public String getEnemyPieceName() {
        return enemyPieceName;
    }

    public void setEnemyPieceName(String enemyPieceName) {
        this.enemyPieceName = enemyPieceName;
    }
}
