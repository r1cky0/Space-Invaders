package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public abstract class AbstractMovable extends Sprite implements Movable{
    private final static double MOVE_OFFSET = 1;

    public AbstractMovable(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public Coordinate moveLeft() {
        super.setX(super.getX() - MOVE_OFFSET);
        return super.getCoordinate();
    }

    public Coordinate moveRight() {
        super.setX(super.getX() + MOVE_OFFSET);
        return super.getCoordinate();
    }

    public Coordinate moveUp() {
        super.setY(super.getY() - MOVE_OFFSET);
        return super.getCoordinate();
    }

    public Coordinate moveDown() {
        super.setY(super.getY() + MOVE_OFFSET);
        return super.getCoordinate();
    }
}
