package Logic.Player;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Bullet;
import Logic.Sprite.Dinamic.SpaceShip;

import java.util.ArrayList;

public class Player {

    // In field controllare che su un pc ci sia un solo utente attivo
    private String name;
    private String password;
    private boolean loggedIn;
    private boolean firstLogin;
    private int credit, highscore;
    private SpaceShip spaceShip;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.credit = 0;
        this.highscore = 0;
        this.password = "Change me";
        loggedIn = false;
        firstLogin = true;
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

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", highscore=" + highscore;
    }

    public int incrementCredit(int c) {
        credit += c;
        return credit;
    }

    public int decrementCredit(int c) {
        credit -= c;
        return credit;
    }

    /**
     * Non controlliamo solamente che la password sia corretta, ma che "l'account" sia stato
     * attivato, ovvero sia stato fatto un primo login con set della password
     * @param password
     * @return
     */
    public boolean login(String password){
        if(this.password == password && firstLogin == false){
            loggedIn = true;
            return true;
        }
        else{
            loggedIn = false;
            return false;
        }
    }

    public void logout(){
        loggedIn = false;
    }

    public boolean setPassword(String newpass){
        if(firstLogin==true && password=="changeme"){
            this.password = newpass;
            firstLogin=false;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isLoggedIn(){
        if(loggedIn == true){
            return true;
        }
        else{
            return false;
        }
    }

}
