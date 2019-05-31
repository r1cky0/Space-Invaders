package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    private static double VERTICAL_OFFSET = 80;
    public static double HORIZONTAL_OFFSET = 20;

    public Invader(Coordinate coordinate, double size, int value) {
        super(coordinate, size);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void moveDown() {
        super.setY(super.getY() + VERTICAL_OFFSET);
    }

    public void moveLeft() {
        super.setX(super.getX() - HORIZONTAL_OFFSET);
    }

    public void moveRight() {
        super.setX(super.getX() + HORIZONTAL_OFFSET);
    }

}
