package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Brick extends Sprite {
    private int life;

    public Brick(Coordinate coordinate, double size) {
        super(coordinate, size);
        life = 4;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life -= 1;
    }

}
