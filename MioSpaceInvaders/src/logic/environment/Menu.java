package logic.environment;

import logic.FileManager.AddAccount;
import logic.FileManager.Login;
import logic.FileManager.SaveCustomization;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;

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

        // solo per non avere nullPointerExc, poi tanto dopo il logIn viene sovrascritta con quella da file
        customization = new Customization("res/images/SpaceShip0.png");

        ranking = new Ranking();

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

    public void saveCustomization(String name, String shipTypePath) {  // salva la current ship nel file
        SaveCustomization.saveCustomization(name, shipTypePath);
    }

    /**
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il field e il nuovo utente puó
     * giocare senza effettuare nuovamente accesso
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     */
    public boolean newAccount(String name, String password){

        if(AddAccount.newAccount(name,password)){
            this.player = new Player(name,defaultShip);

            customization = new Customization("res/images/SpaceShip0.png");

            field = new Field(player, maxWidth, maxHeight);
            return true;
        }
        return false;
    }

    public boolean logIn(String name, String password){

        String [] components = Login.login(name, password);

        if(components != null){
            player = new Player(name, defaultShip);
            player.setHighScore(Integer.parseInt(components[2]));
            customization = new Customization(components[3]);

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
