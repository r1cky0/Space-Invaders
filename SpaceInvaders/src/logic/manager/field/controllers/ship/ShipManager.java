package logic.manager.field.controllers.ship;

import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;

/**
 * Classe per la gestione della ship.
 */
public class ShipManager {

    /**
     * Metodo per il movimento della ship.
     * Controlla che non superi le dimensioni del campo di gioco.
     *
     * @param md direzione
     * @param delta velocità
     */
    public void move(SpaceShip spaceShip, MovingDirections md, int delta){
        if(((spaceShip.getX() + Dimensions.SHIP_WIDTH) < Dimensions.MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight(delta);
        }
        if((spaceShip.getX() > Dimensions.MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft(delta);
        }
    }

    /**
     * Metodo di sparo della space ship
     *
     * @param spaceShip: ship che effettua lo sparo.
     */
    public void shot(SpaceShip spaceShip){
        if(!spaceShip.isShipShot()) {
            Coordinate coordinate = new Coordinate(spaceShip.getShape().getCenterX() - Dimensions.BULLET_WIDTH /2,
                    spaceShip.getY());
            spaceShip.setShipBullet(coordinate);
            spaceShip.setShipShot(true);
        }
    }

}
