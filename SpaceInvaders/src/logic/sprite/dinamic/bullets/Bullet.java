package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public abstract class Bullet extends Sprite{

    public static float VERTICAL_OFFSET = 0.07f;

    public Bullet(Coordinate coordinate, float width, float height) {
        super(coordinate, width, height);
    }

    public abstract void move(int delta);
}
