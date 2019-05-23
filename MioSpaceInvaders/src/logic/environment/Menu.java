package logic.environment;

import logic.sprite.Coordinate;
import logic.player.Player;
import logic.sprite.dinamic.SpaceShip;

import java.util.HashMap;

public class Menu {

    private Ranking ranking;
    private Customization customization;
    private Field field;
    private double maxHeight;
    private double maxWidth;
    private double shipSize;
    private HashMap<String, Player> players;
    private SpaceShip defaultShip;
    private Player player;

    public Menu(double maxWidth, double maxHeight){
        ranking = new Ranking();
        customization = new Customization();
        players = new HashMap<>();

        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.shipSize = maxWidth/20;

        Coordinate coordinate = new Coordinate((maxWidth/2 - shipSize/2),(maxHeight - shipSize));
        System.err.println("X: "+coordinate.getX());
        System.err.println("Y :"+coordinate.getY());
        defaultShip = new SpaceShip(coordinate,shipSize);
    }

    public boolean newAccount(String name, String password){
        Player newPlayer;

        if(players.containsKey(name)){
            //***************
            System.err.println("Player giá esistente");
            //***************
            return false;
        }
        else{
            newPlayer = new Player(name,defaultShip);
            newPlayer.setPassword(password);
            players.put(name,newPlayer);
            return true;
        }
    }

    public boolean logIn(String name, String password){
        Player player = players.get(name);
        if(player == null){
            System.err.println("Nome utente errato");
            return false;
        }
        if(player.login(password)){
            this.player = player;
            return true;
        }
        else{
            System.err.println("Password errata");
            return false;
        }
    }

    //Probabilmente ci sará da fare un' eccezione
    public void startGame(){
        if(player != null){
            player.getSpaceShip().setCurrentScore();
            field = new Field(player, maxWidth, maxHeight);
            field.startGame();
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
