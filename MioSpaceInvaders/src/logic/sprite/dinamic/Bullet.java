package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public class Bullet extends Sprite{

    public double verticalOffset = 10;

    public Bullet(Coordinate coordinate, double size, int delta) {
        super(coordinate, size);
        verticalOffset = verticalOffset*delta;
    }

    public String toString() {
        return super.toString();
    }

    public void moveUp() {
        super.setY(super.getY() - verticalOffset);
    }

    public void moveDown() {
        super.setY(super.getY() + verticalOffset);
    }


}
