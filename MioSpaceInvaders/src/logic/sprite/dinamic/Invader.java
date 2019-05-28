package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    private double verticalOffset = 40;
    private double horizontalOffset = 1;

    public Invader(Coordinate coordinate, double size, int value, int delta) {
        super(coordinate, size);
        this.value = value;
        horizontalOffset = horizontalOffset*delta;
        verticalOffset = verticalOffset*delta;
    }

    public int getValue() {
        return value;
    }

    public void moveLeft() {
        super.setX(super.getX() - horizontalOffset);
    }

    public void moveRight() {
        super.setX(super.getX() + horizontalOffset);
    }

    public void moveDown() {
        super.setY(super.getY() + verticalOffset);
    }

}
