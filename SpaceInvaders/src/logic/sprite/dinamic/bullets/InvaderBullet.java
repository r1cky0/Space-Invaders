package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

/**
 * Classe che rappresenta il proiettile sparato dagli invader.
 */
public class InvaderBullet extends Bullet{

    public InvaderBullet(Coordinate coordinate) {
        super(coordinate);
    }

    /**
     * Metodo per il movimento verso il basso del proiettile.
     *
     * @param delta velocità
     */
    public void move(int delta) {
        super.setY(super.getY() + VERTICAL_OFFSET*delta);
    }
}
