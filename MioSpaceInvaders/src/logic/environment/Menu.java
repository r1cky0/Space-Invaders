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

    public Menu(double maxHeight, double maxWidth, double shipSize){
        ranking = new Ranking();
        customization = new Customization();
        players = new HashMap<>();

        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.shipSize = shipSize;

        Coordinate coordinate = new Coordinate(20,180);
        SpaceShip spaceShip = new SpaceShip(coordinate,shipSize);
    }

    public void addPlayer(String name, String password){
        Player newPlayer;

        if(!players.containsKey(name)){
            System.err.println("Player giá esistente");
        }
        else{
            newPlayer = new Player(name,defaultShip);
            newPlayer.setPassword(password);
            players.put(name,newPlayer);
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
            field = new Field(player, maxHeight, maxWidth);
        }
    }

}
