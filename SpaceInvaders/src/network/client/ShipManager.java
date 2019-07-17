package network.client;

import logic.manager.field.MovingDirections;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;

/**
 * Classe che gestisce la ship e il suo movimento in locale durante multiplayer.
 */
public class ShipManager {

    private SpaceShip spaceShip;

    ShipManager(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
    }

    /**
     * Metodo per il movimento della ship.
     * Controlla che non superi le dimensioni del campo di gioco.
     *
     * @param md direzione
     * @param delta velocità
     */
    void shipMovement(MovingDirections md, int delta){
        if(((spaceShip.getX() + Dimensions.SHIP_WIDTH) < Dimensions.MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight(delta);
        }

        if((spaceShip.getX() > Dimensions.MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft(delta);
        }
    }

    public Coordinate getCoordinate(){
        return spaceShip.getCoordinate();
    }

    public float getX(){
        return spaceShip.getX();
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }
}
