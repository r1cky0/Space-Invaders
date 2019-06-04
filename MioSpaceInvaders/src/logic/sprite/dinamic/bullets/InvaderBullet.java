package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class InvaderBullet extends Bullet {

    public InvaderBullet(Coordinate coordinate, double size) {
        super(coordinate, size);
    }

    public void move(int delta) {
        super.setY(super.getY() + verticalOffset*delta);
    }
}
