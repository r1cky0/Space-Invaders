package game.sprite.elements;

import game.environment.Coordinate;
import game.sprite.Sprite;

public class Invader extends Sprite {

    //Value é il punteggio che si guadagna uccidendo l'alieno
    private int value;

    public Invader(Coordinate coordinate, int value){
        super(coordinate);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
