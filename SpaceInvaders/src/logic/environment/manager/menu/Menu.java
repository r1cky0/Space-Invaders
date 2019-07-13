package logic.environment.manager.menu;

import logic.environment.manager.file.FileModifier;
import logic.environment.manager.game.SinglePlayer;
import logic.environment.manager.file.AddAccount;
import logic.environment.manager.file.Login;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;


public class Menu {
    private FileModifier fileModifier;
    private SinglePlayer singlePlayer;
    private Ranking ranking;
    private Customization customization;
    private SpaceShip defaultShip;
    private Player player;
    private AddAccount addAccount;
    private Login login;

    public Menu(){
        customization = new Customization();
        ranking = new Ranking();
        addAccount = new AddAccount();
        login = new Login();
        fileModifier = new FileModifier();

        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH/2 - Dimensions.SHIP_WIDTH /2),
                (Dimensions.MAX_HEIGHT - Dimensions.SHIP_WIDTH));
        defaultShip = new SpaceShip(coordinate, Dimensions.SHIP_WIDTH, Dimensions.SHIP_HEIGHT);
    }

    /**
     * Funzione di creazione di un nuovo account attuata la quale viene inizializzato il fieldManager e il nuovo utente
     * effettua automaticamente l' accesso
     *
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se aggiunta dell' acount é andata a buon fine
     */
    public boolean newAccount(String name, String password){
        if(addAccount.newAccount(name,password)){
            player = new Player(name, defaultShip);
            singlePlayer = new SinglePlayer(player);
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
            singlePlayer = new SinglePlayer(player);
            return true;
        }
        else{
            return false;
        }
    }

    public void saveToFile(){
        fileModifier.modifyFile(player.getName(), player.getHighScore(), customization.getCurrentShip());
    }

    public SinglePlayer getSinglePlayer(){
        return singlePlayer;
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
