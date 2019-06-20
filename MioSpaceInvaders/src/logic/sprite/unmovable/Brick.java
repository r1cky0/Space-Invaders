package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Brick extends Sprite {
    private int life;

    public Brick(Coordinate coordinate, double width, double height) {
        super(coordinate, width, height);
        life = 4;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life -= 1;
    }

}
