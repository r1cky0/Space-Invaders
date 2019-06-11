package logic.environment.manager.menu;

import gui.states.SinglePlayer;
import logic.environment.manager.file.AddAccount;
import logic.environment.manager.file.Login;
import logic.environment.manager.file.SaveCustomization;
import logic.environment.manager.game.OfflineGameManager;
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
    private OfflineGameManager offlineGameManager;
    private SinglePlayer singlePlayer;
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
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il offlineGameManager e il nuovo utente puó
     * giocare senza effettuare nuovamente accesso
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     */
    public boolean newAccount(String name, String password){

        if(AddAccount.newAccount(name,password)){
            this.player = new Player(name,defaultShip);

            customization = new Customization("res/images/SpaceShip0.png");

            offlineGameManager = new OfflineGameManager(maxWidth, maxHeight);
            singlePlayer = new SinglePlayer(player, offlineGameManager);
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

            offlineGameManager = new OfflineGameManager(maxWidth, maxHeight);
            singlePlayer = new SinglePlayer(player, offlineGameManager);
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
        offlineGameManager = new OfflineGameManager(maxWidth, maxHeight);
        singlePlayer = new SinglePlayer(player, offlineGameManager);
    }

    //A programma sistemato togliere questo getter e fare che startGame restituisca il offlineGameManager
    public OfflineGameManager getOfflineGameManager() {
        return offlineGameManager;
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
}
