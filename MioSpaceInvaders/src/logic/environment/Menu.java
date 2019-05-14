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

    public Menu(double maxHeight, double maxWidth){
        ranking = new Ranking();
        customization = new Customization();
        players = new HashMap<>();

        this.maxHeight = maxHeight;
        this.maxWidth = maxWidth;
        this.shipSize = maxWidth/20;

        Coordinate coordinate = new Coordinate(20,180);
        defaultShip = new SpaceShip(coordinate,shipSize);
    }

    public void addPlayer(String name, String password){
        Player newPlayer;

        if(players.containsKey(name)){
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
    public Field startGame(){
        if(player != null){
            return field = new Field(player, maxHeight, maxWidth);
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }

}
