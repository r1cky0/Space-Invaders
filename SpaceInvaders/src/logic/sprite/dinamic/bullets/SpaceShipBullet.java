package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class SpaceShipBullet extends Bullet{

    public SpaceShipBullet(Coordinate coordinate) {
        super(coordinate);
    }

    public void move(int delta) {
        super.setY(super.getY() - VERTICAL_OFFSET*delta);
    }

}
