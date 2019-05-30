package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    private double verticalOffset = 80;
    private double horizontalOffset = 15;

    public Invader(Coordinate coordinate, double size, int value) {
        super(coordinate, size);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void moveDown() {
        super.setY(super.getY() + verticalOffset);
    }

    public void moveLeft() {
        super.setX(super.getX() - horizontalOffset);
    }

    public void moveRight() {
        super.setX(super.getX() + horizontalOffset);
    }

}
