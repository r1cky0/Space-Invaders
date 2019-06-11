package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public abstract class Bullet extends Sprite implements Movable{

    public double verticalOffset = 3;

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public abstract void move();
}
