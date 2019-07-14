package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class InvaderBullet extends Bullet{

    public InvaderBullet(Coordinate coordinate) {
        super(coordinate);
    }

    public void move(int delta) {
        super.setY(super.getY() + VERTICAL_OFFSET*delta);
    }
}
