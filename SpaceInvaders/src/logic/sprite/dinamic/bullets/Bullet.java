package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;


public abstract class Bullet extends Sprite{

    static float VERTICAL_OFFSET = 0.07f;

    Bullet(Coordinate coordinate) {
        super(coordinate, Dimensions.BULLET_WIDTH, Dimensions.BULLET_HEIGHT, Target.BULLET);
    }

    public abstract void move(int delta);
}
