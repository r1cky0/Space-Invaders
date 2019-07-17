package logic.sprite.dinamic.bullets;

import logic.sprite.Coordinate;

/**
 * Classe che rappresenta il proiettile sparato dalla ship.
 */
public class SpaceShipBullet extends Bullet{

    public SpaceShipBullet(Coordinate coordinate) {
        super(coordinate);
    }

    /**
     * Metodo per il movimento verso l'alto del proiettile.
     *
     * @param delta velocità
     */
    public void move(int delta) {
        super.setY(super.getY() - VERTICAL_OFFSET*delta);
    }

}
