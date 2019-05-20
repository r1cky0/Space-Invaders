package logic.player;

import logic.sprite.dinamic.SpaceShip;

public class Player implements Comparable{

    private String name;
    private String password;
    private boolean loggedIn;
    private boolean firstLogin;
    private int credit, highScore;
    private SpaceShip spaceShip;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.credit = 0;
        this.highScore = 0;
        this.password = "changeme";
        loggedIn = false;
        firstLogin = true;
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

    public void setHighScore(int high_score) {
        this.highScore = high_score;
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

    /**
     * Controllo attivazione account giocatore e credenziali corrette
     *
     * @param password: password utente
     */
    public boolean login(String password){

        if((this.password.equals(password)) && (!firstLogin)){
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

    public void setPassword(String pass){

        if((firstLogin) && (password.equals("changeme"))){
            this.password = pass;
            firstLogin=false;
        }
    }

    public boolean isLoggedIn(){
        return loggedIn;
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
