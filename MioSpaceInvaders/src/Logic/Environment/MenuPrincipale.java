package Logic.Environment;

import Logic.Coordinate;
import Logic.Player.Player;
import Logic.Sprite.Dinamic.SpaceShip;

import java.util.HashMap;

public class MenuPrincipale {

    private Classifica classifica;
    private Personalizzazione personalizzazione;
    private Field field;
    private double max_height;
    private double max_width;
    private double shipSize;
    private HashMap<String, Player> players;
    private SpaceShip defaultShip;

    public MenuPrincipale(Player player,double max_height, double max_width, double shipSize){
        classifica = new Classifica();
        personalizzazione = new Personalizzazione();
        this.max_height = max_height;
        this.max_width = max_width;
        this.shipSize = shipSize;
        defaultShip = new SpaceShip(new Coordinate(max_width/2,9*max_height/10),shipSize);
    }

    public void addPlayer(String name, String password){
        Player newPlayer = null;
        if(!players.containsKey(name)){
            System.err.println("Player giá esistente");
            return;
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
            field = new Field(player,max_height,max_width);
            return true;
        }
        else{
            System.err.println("Password errata");
            return false;
        }
    }

}
