package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class SpaceShipBullet extends Bullet implements Movable{

    public SpaceShipBullet(Coordinate coordinate, float width, float height) {
        super(coordinate, width, height);
    }

    public void move(int delta) {
        super.setY(super.getY() - VERTICAL_OFFSET*delta);
    }

}
