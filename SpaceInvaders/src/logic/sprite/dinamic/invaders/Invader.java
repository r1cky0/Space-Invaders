package logic.sprite.dinamic.invaders;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class Invader extends Sprite {

    private int value;
    public static float HORIZONTAL_OFFSET = 2;

    public Invader(Coordinate coordinate, float width, float height, int value) {
        super(coordinate, width, height);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void moveDown() {
        float VERTICAL_OFFSET = 8;
        super.setY(super.getY() + VERTICAL_OFFSET);
    }

    public void moveLeft() {
        super.setX(super.getX() - HORIZONTAL_OFFSET);
    }

    public void moveRight() {
        super.setX(super.getX() + HORIZONTAL_OFFSET);
    }

}
