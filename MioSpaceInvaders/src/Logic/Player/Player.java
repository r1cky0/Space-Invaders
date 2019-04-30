package Logic.Player;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.SpaceShip;

import java.util.ArrayList;

public class Player {

    // mettere password a player e fare metodi log in log out, in field controllare che su un pc ci sia un solo utente attivo
    private String name;
    private int credit, highscore, lastscore;
    private SpaceShip spaceShip;
    private ArrayList<Bullet> bullets;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.credit = 0;
        this.highscore = 0;
        this.lastscore = 0;
        this.bullets = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getLastscore() {
        return lastscore;
    }

    public void setLastscore(int lastscore) {
        this.lastscore = lastscore;
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", credit=" + credit +
                ", highscore=" + highscore +
                ", lastscore=" + lastscore + '}' + spaceShip;
    }

    public int incrementCredit(int c) {
        credit += c;
        return credit;
    }

    public int decrementCredit(int c) {
        credit -= c;
        return credit;
    }

    public Coordinate moveLeft(double x) {
        return spaceShip.moveLeft(x);
    }

    public Coordinate moveRight(double x) {
        return spaceShip.moveRight(x);
    }

    public boolean shot() {
        Bullet b = new Bullet(spaceShip.getCoordinate());
        return bullets.add(b);
    }

    public void shotUp(double y) {
        for (Bullet b: bullets) {
            b.moveUp(y);
        }
    }

    public boolean removeShot(Coordinate coordinate) {
        ArrayList<Bullet> toBeRemoved = new ArrayList<>();
        for (Bullet b: bullets) {
            if (b.getCoordinate().equals(coordinate)) {
                toBeRemoved.add(b);
            }
        }
        return bullets.removeAll(toBeRemoved);
    }
}
