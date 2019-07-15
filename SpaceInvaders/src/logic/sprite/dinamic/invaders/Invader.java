package logic.sprite.dinamic.invaders;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;

public class Invader extends Sprite {

    private final int value = 10;
    public static float HORIZONTAL_OFFSET = 2;

    public Invader(Coordinate coordinate) {
        super(coordinate, Dimensions.INVADER_WIDTH, Dimensions.INVADER_HEIGHT, Target.INVADER);
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
