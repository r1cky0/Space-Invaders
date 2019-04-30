package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class Bullet extends Sprite implements Movable {

    public Bullet(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Coordinate moveLeft(double x) {
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveRight(double x) {
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveUp(double y) {
        super.setY(super.getY() - y);
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveDown(double y) {
        super.setY(super.getY() + y);
        return super.getCoordinate();
    }
}
