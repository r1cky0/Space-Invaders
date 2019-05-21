package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public abstract class AbstractMovable extends Sprite implements Movable{

    private final static double VERTICAL_OFFSET = 30;
    private final static double HORIZONTAL_OFFSET = 7;

    public AbstractMovable(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public void moveLeft() {
        super.setX(super.getX() - HORIZONTAL_OFFSET);
    }

    public void moveRight() {
        super.setX(super.getX() + HORIZONTAL_OFFSET);
    }

    public void moveUp() {
        super.setY(super.getY() - VERTICAL_OFFSET);
    }

    public void moveDown() {
        super.setY(super.getY() + VERTICAL_OFFSET);
    }
}
