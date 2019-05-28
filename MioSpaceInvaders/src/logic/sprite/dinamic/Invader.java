package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    private double verticalOffset = 2;
    private double horizontalOffset = 0.08;

    public Invader(Coordinate coordinate, double size, int value) {
        super(coordinate, size);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void moveLeft(int delta) {
        super.setX(super.getX() - horizontalOffset*delta);
    }

    public void moveRight(int delta) {
        super.setX(super.getX() + horizontalOffset*delta);
    }

    public void moveDown(int delta) {
        super.setY(super.getY() + verticalOffset*delta);
    }

}
