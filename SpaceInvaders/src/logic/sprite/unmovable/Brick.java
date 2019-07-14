package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import main.Dimensions;

public class Brick extends Sprite {
    private int life;

    public Brick(Coordinate coordinate) {
        super(coordinate, Dimensions.BRICK_WIDTH, Dimensions.BRICK_HEIGHT);
        life = 4;
    }

    public void setLife(int life){
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    void decreaseLife() {
        life -= 1;
    }

}
