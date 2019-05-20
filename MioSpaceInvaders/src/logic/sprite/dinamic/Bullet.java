package logic.sprite.dinamic;

import logic.sprite.Coordinate;

public class Bullet extends AbstractMovable{

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate,size);
    }

    public String toString() {
        return super.toString();
    }

    public void moveLeft() {}

    public void moveRight() {}

}
