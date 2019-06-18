package logic.environment.manager.menu;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.game.SinglePlayer;
import logic.environment.manager.file.AddAccount;
import logic.environment.manager.file.Login;
import logic.environment.manager.file.SaveCustomization;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;

import java.io.IOException;

public class Menu {

    //DIMENSION
    private double maxWidth;
    private double maxHeight;

    private Ranking ranking;

    private Customization customization;
    private FieldManager fieldManager;
    private SinglePlayer singlePlayer;
    private SpaceShip defaultShip;
    private Player player;

    public Menu(double maxWidth, double maxHeight){
        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        double shipSize = maxWidth / 20;

        customization = new Customization();
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

    public void saveCustomization(String name, String shipType) {
        SaveCustomization.saveCustomization(name, shipType);
    }

    /**
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il fieldManager e il nuovo utente puó
     * giocare senza effettuare nuovamente accesso
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     */
    public boolean newAccount(String name, String password){

        if(AddAccount.newAccount(name,password)){
            this.player = new Player(name,defaultShip);

            customization.setCurrentShip("ship0");

            fieldManager = new FieldManager(maxWidth, maxHeight);
            singlePlayer = new SinglePlayer(player, fieldManager);
            return true;
        }
        return false;
    }

    public boolean logIn(String name, String password){

        String [] components = Login.login(name, password);

        if(components != null){
            player = new Player(name, defaultShip);
            player.setHighScore(Integer.parseInt(components[2]));

            customization.setCurrentShip(components[3]);

            fieldManager = new FieldManager(maxWidth, maxHeight);
            singlePlayer = new SinglePlayer(player, fieldManager);
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
     * Funzione necessaria per reinizializzare il sistema dopo un checkHighscore
     */
    public void restartGame() {
        fieldManager = new FieldManager(maxWidth, maxHeight);
        singlePlayer = new SinglePlayer(player, fieldManager);
    }

    public SinglePlayer getSinglePlayer(){return singlePlayer;}

    public Player getPlayer() {
        return player;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public Customization getCustomization() {
        return customization;
    }

    public double getMaxWidth(){
        return maxWidth;
    }
}
