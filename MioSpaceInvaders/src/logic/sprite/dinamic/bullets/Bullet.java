package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public abstract class Bullet extends Sprite implements Movable{

    public static double VERTICAL_OFFSET = 0.07;

    public Bullet(Coordinate coordinate, double width, double height) {
        super(coordinate, width, height);
    }

    public abstract void move(int delta);
}
