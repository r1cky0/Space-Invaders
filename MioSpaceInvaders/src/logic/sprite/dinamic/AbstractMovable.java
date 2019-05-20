package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public abstract class AbstractMovable extends Sprite implements Movable{

    private final static double MOVE_OFFSET = 30;

    public AbstractMovable(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public void moveLeft() {
        super.setX(super.getX() - MOVE_OFFSET);
    }

    public void moveRight() {
        super.setX(super.getX() + MOVE_OFFSET);
    }

    public void moveUp() {
        super.setY(super.getY() - MOVE_OFFSET);
    }

    public void moveDown() {
        super.setY(super.getY() + MOVE_OFFSET);
    }
}
