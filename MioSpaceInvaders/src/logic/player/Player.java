package logic.player;

import logic.FileManager.AddHighScore;
import logic.sprite.dinamic.SpaceShip;

import java.io.IOException;

public class Player implements Comparable{

    private String name;
    private int credit, highScore;
    private SpaceShip spaceShip;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.credit = 0;
        this.highScore = 0;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highscore) {
        this.highScore = highscore;
        try {
            AddHighScore.saveHighscore(name,highscore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    public void incrementCredit(int credit) {
        this.credit += credit;
    }

    /*ordina dal punteggio + grande al + piccolo*/
    @Override
    public int compareTo(Object o) {
        if (this.highScore > ((Player)o).highScore) {
            return -1;
        }
        if (this.highScore < ((Player)o).highScore) {
            return 1;
        }
        else return 0;
    }
}
