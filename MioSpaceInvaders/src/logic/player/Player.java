package logic.player;

import logic.sprite.dinamic.SpaceShip;

public class Player {

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
        this.password = "changeme";
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

    public int incrementCredit(int c) {
        credit += c;
        return credit;
    }

    public int decrementCredit(int c) {
        credit -= c;
        return credit;
    }

    /**
     * Controllo attivazione account giocatore e credenziali corrette
     *
     * @param password: password utente
     */
    public boolean login(String password){
        if(this.password.equals(password) && !firstLogin){
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

    public void setPassword(String newpass){
        if(firstLogin && password.equals("changeme")){
            this.password = newpass;
            firstLogin=false;
        }
    }

    public boolean isLoggedIn(){
        return loggedIn;
    }

}
