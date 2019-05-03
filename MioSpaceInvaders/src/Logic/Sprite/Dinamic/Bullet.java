package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class Bullet extends AbstractMovable{

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate,size);
    }

    public String toString() {
        return super.toString();
    }

    public Coordinate moveLeft() {
        return super.getCoordinate();
    }

    public Coordinate moveRight() {
        return super.getCoordinate();
    }

}
