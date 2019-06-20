package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

public class InvaderBullet extends Bullet implements Movable{

    public InvaderBullet(Coordinate coordinate, float width, float height) {
        super(coordinate, width, height);
    }

    public void move(int delta) {
        super.setY(super.getY() + VERTICAL_OFFSET*delta);
    }
}
