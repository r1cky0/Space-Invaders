package game.player;

public class Player {

    private String name;
    private int credit, highscore;


    public Player(String name) {
        this.name = name;
        credit = 0;
        highscore = 0;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public int getHighscore() {
        return highscore;
    }

    public void changeCredit(int c) {
        credit+=c;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public String toString(){
        return this.name + ":\nCrediti totali: "+ this.credit+"\n";
    }
}
