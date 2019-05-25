package logic.environment;

import logic.FileManager.AddPlayer;
import logic.FileManager.Login;
import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;

import java.io.IOException;
import java.util.HashMap;

public class Menu {

    private Ranking ranking;
    private Customization customization;
    private Field field;
    private double maxHeight;
    private double maxWidth;
    private double shipSize;
    private SpaceShip defaultShip;
    private Player player;

    public Menu(double maxWidth, double maxHeight){
        ranking = new Ranking();
        customization = new Customization();


        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.shipSize = maxWidth/20;

        Coordinate coordinate = new Coordinate((maxWidth/2 - shipSize/2),(maxHeight - shipSize));
        defaultShip = new SpaceShip(coordinate,shipSize);
    }

    public boolean newAccount(String name, String password) throws IOException {
        Player newPlayer;
        AddPlayer addPlayer = new AddPlayer();

        if(addPlayer.newPlayer(name,password)){
            return false;
        }
        else{
            newPlayer = new Player(name,defaultShip);
            this.player= newPlayer;
            return true;
        }
    }

    public boolean logIn(String name, String password)throws IOException{

        Player player;
        Login log = new Login();

        if(log.login(name,password)){
            this.player = new Player(name,defaultShip);
            return true;
        }
        else{
            return false;
        }
    }

    public void logOut(){
        this.player = null;
    }

    //Probabilmente ci sará da fare un' eccezione
    public void startGame(){
        if(player != null){
            field = new Field(player, maxWidth, maxHeight);
        }
    }

    //A programma sistemato togliere questo getter e fare che startGame restituisce il field
    public Field getField() {
        return field;
    }


    public Player getPlayer() {
        return player;
    }

    //TOGLIERE I SEGUENTI DUE METODI QUANDO FATTO LOGIN PERCHÉ INUTILE IN QUEL CASO. ORA FATTO PER PROVARE GIOCO
    public void setPlayer(String nome) {
        this.player = new Player(nome,defaultShip);
    }

    public void setField() {
        field = new Field(player, maxWidth, maxHeight);
    }

}
