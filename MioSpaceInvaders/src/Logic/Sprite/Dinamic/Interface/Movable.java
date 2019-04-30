package Logic.Sprite.Dinamic.Interface;

import Logic.Coordinate;

public interface Movable {

    Coordinate moveLeft(double x);
    Coordinate moveRight(double x);
    Coordinate moveUp(double y);
    Coordinate moveDown(double y);
}
