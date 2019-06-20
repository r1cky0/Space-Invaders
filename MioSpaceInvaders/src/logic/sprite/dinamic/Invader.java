package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    private static double VERTICAL_OFFSET = 8;
    public static double HORIZONTAL_OFFSET = 2;

    public Invader(Coordinate coordinate, double width, double height, int value) {
        super(coordinate, width, height);
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
