package logic.environment.manager.menu;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.file.ReadXmlFile;
import logic.environment.manager.game.SinglePlayer;
import logic.environment.manager.file.AddAccount;
import logic.environment.manager.file.Login;
import logic.environment.manager.file.FileModifier;
import logic.environment.manager.game.States;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;

import java.io.IOException;

public class Menu {
    private Ranking ranking;
    private Customization customization;
    private FieldManager fieldManager;
    private SinglePlayer singlePlayer;
    private SpaceShip defaultShip;
    private Player player;
    private FileModifier fileModifier;
    private ReadXmlFile readXmlFile;
    private AddAccount addAccount;
    private Login login;

    public Menu(){
        customization = new Customization();
        ranking = new Ranking();

        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH/2 - Dimensions.SHIP_WIDTH /2),
                (Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH));
        defaultShip = new SpaceShip(coordinate, Dimensions.SHIP_WIDTH, Dimensions.SHIP_HEIGHT);

        fileModifier = new FileModifier();
        readXmlFile = new ReadXmlFile();
        addAccount = new AddAccount();
        login = new Login();
    }

    public void createRanking(){
        try {
            ranking.createRanking();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomization(String name, String shipType) {
        fileModifier.modifyFile(name,player.getHighScore(), shipType);
    }

    /**
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il fieldManager e il nuovo utente puó
     * giocare senza effettuare nuovamente accesso
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     */
    public boolean newAccount(String name, String password){
        if(addAccount.newAccount(name,password)){
            this.player = new Player(name,defaultShip);

            customization.setCurrentShip("ship0");

            fieldManager = new FieldManager();
            singlePlayer = new SinglePlayer(player, fieldManager);
            return true;
        }
        return false;
    }

    public boolean logIn(String name, String password){
        String [] components = login.tryLogin(name, password);

        if(components != null){
            player = new Player(name, defaultShip);
            player.setHighScore(Integer.parseInt(components[2]));

            customization.setCurrentShip(components[3]);

            fieldManager = new FieldManager();
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
        fieldManager = new FieldManager();
        singlePlayer = new SinglePlayer(player, fieldManager);
    }

    public States checkGameState(){
        return singlePlayer.checkGameState(fileModifier,customization);
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

    public ReadXmlFile getReadXmlFile(){return readXmlFile;}
}
