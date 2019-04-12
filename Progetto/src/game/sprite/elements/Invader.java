package game.sprite.elements;

import game.environment.Coordinate;
import game.sprite.Sprite;

public class Invader extends Sprite {

    private int value;

    public Invader(Coordinate coordinate, int value){
        super(coordinate);
        this.value = value;
    }
}
