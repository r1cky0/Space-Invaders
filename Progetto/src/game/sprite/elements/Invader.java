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


    /**
     * L'invasore fa fuoco creando un Bullet poco sotto di sé
     */
    public void Shoot(){
        Coordinate coordinate1 = this.getCoordinate();
        coordinate1.setX(this.getCoordinate().getX());
        coordinate1.setY(this.getCoordinate().getY()+5);
        Bullet bullet = new Bullet (coordinate1);
    }
}
