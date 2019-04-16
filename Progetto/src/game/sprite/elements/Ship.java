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

    /**
     * La nave fa fuoco creando un Bullet poco sopra di sé
     */
    public void Shoot(){
        Coordinate coordinate1 = this.getCoordinate();
        coordinate1.setX(this.getCoordinate().getX());
        coordinate1.setY(this.getCoordinate().getY()-5);
        Bullet bullet = new Bullet (coordinate1);
    }
}
