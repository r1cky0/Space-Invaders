package logic.environment;

import logic.FileManager.AddAccount;
import logic.FileManager.Login;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import org.newdawn.slick.util.Log;

import java.io.IOException;

public class Menu {

    //DIMENSION
    private double maxHeight;
    private double maxWidth;

    private Ranking ranking;

    private Customization customization;
    private Field field;
    private SpaceShip defaultShip;
    private Player player;

    public Menu(double maxWidth, double maxHeight){
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        double shipSize = maxWidth / 20;

        ranking = new Ranking();
        customization = new Customization();

        Coordinate coordinate = new Coordinate((maxWidth/2 - shipSize /2),(maxHeight - shipSize));
        defaultShip = new SpaceShip(coordinate, shipSize);
    }

    public void createRanking(){
        try {
            ranking.createRanking();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il field e il nuovo utente puó
     * giocare senza effettuare nuovamente accesso
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     * @throws IOException Eccezione di sistema per acquisizione input da tastiera
     */
    public boolean newAccount(String name, String password) throws IOException {

        if(AddAccount.newAccount(name,password)){
            this.player = new Player(name,defaultShip);
            field = new Field(player, maxWidth, maxHeight);
            return true;
        }
        return false;
    }

    public boolean logIn(String name, String password)throws IOException{

        if(Login.login(name,password)){
            this.player = new Player(name,defaultShip);
            field = new Field(player, maxWidth, maxHeight);
            return true;
        }
        else{
            return false;
        }
    }

    public void logOut(){
        this.player = null;
    }

    /**
     * Funzione necessaria per reinizializzare il sistema dopo un gameOver
     */
    public void restartGame() {
        field = new Field(player, maxWidth, maxHeight);
    }

    //A programma sistemato togliere questo getter e fare che startGame restituisca il field
    public Field getField() {
        return field;
    }

    public Player getPlayer() {
        return player;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public Customization getCustomization() {
        return customization;
    }
}
