package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class SpaceShipBullet extends Bullet {

    public SpaceShipBullet(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public void move() {
        super.setY(super.getY() - verticalOffset);
    }
}
