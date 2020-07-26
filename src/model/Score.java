package model;

public class Score implements Comparable<Score> {
  private   String userName;
   private int score;
   private String online;

    public Score(String userName, int score, String online) {
        this.userName = userName;
        this.score = score;
        this.online = online;
    }
    public Score(){

    }

    @Override
    public int compareTo(Score o) {
        if (score>o.score){
            return -1;
        }
        else if (score<o.score){
            return 1;
        }
        else {
            return 0;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
