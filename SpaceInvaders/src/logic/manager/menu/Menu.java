package logic.manager.menu;

import logic.manager.file.FileModifier;
import logic.manager.game.single.SinglePlayer;
import logic.manager.file.AddAccount;
import logic.manager.file.Login;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;

/**
 * Classe che rappresenta il menu di gioco.
 *
 */
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
        Coordinate coordinate = new Coordinate(0,0);
        defaultShip = new SpaceShip(coordinate);
        defaultShip.init();
    }

    /**
     * Funzione di creazione di un nuovo account con creazione di nuovo utente.
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

    /**
     * Funzione per il login.
     * Se il login va a buon fine setta highscore e currentship da lettura file.
     *
     * @param name Nickname del giocatore
     * @param password Password del giocatore
     * @return Segnala se login é andato a buon fine
     */
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

    /**
     * Funzione che salva le modifiche sul file.
     */
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
