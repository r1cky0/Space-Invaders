package game.sprite.elements;

import game.environment.Coordinate;
import game.player.Player;
import game.sprite.Sprite;

public class Ship extends Sprite {

    private String name;
    private int life;
    private Player player;

    public Ship(String name, Coordinate coordinate, Player player){
        super(coordinate);
        this.name = name;
        life = 3;
        this.player=player;
    }

    public int getLife() {
        return life;
    }

    public void decreseLife() {
        life--;
    }
}
