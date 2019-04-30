package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class Invader extends Sprite implements Movable {

    private int value;

    public Invader(Coordinate coordinate, int value) {
        super(coordinate);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" + "value=" + value + '}' + super.toString();
    }

    @Override
    public Coordinate moveLeft(double x) {
        super.setX(super.getX() - x);
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveRight(double x) {
        super.setX(super.getX() + x);
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
