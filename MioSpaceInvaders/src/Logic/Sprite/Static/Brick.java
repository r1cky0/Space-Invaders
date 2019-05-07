package Logic.Sprite.Static;

import Logic.Coordinate;
import Logic.Sprite.Sprite;

public class Brick extends Sprite {

    private int life;

    public Brick(Coordinate coordinate, double size) {
        super(coordinate, size);
        life = 4;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int decreaseLife() {
        life -= 1;
        return life;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
