package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public class Bullet extends Sprite{

    public double VERTICAL_OFFSET = 10;

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public String toString() {
        return super.toString();
    }

    public void moveUp() {
        super.setY(super.getY() - VERTICAL_OFFSET);
    }

    public void moveDown() {
        super.setY(super.getY() + VERTICAL_OFFSET);
    }


}
