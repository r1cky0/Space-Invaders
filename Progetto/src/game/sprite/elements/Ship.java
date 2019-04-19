package game.sprite.elements;

import game.environment.Coordinate;
import game.sprite.Sprite;

public class Ship extends Sprite {

    private String name;
    private int life;

    public Ship(String name, Coordinate coordinate){
        super(coordinate);
        this.name = name;
        life = 3;
    }

}
