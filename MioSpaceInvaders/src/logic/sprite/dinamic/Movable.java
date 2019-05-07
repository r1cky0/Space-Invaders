package logic.sprite.dinamic;

import logic.sprite.Coordinate;

public interface Movable {

    Coordinate moveLeft();
    Coordinate moveRight();
    Coordinate moveUp();
    Coordinate moveDown();
}
