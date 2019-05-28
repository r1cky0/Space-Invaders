package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;


public class Bullet extends Sprite{

    public double verticalOffset = 0.8;

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public String toString() {
        return super.toString();
    }

    public void moveUp(int delta) {
        super.setY(super.getY() - verticalOffset*delta);
    }

    public void moveDown(int delta) {
        super.setY(super.getY() + verticalOffset*delta);
    }


}
