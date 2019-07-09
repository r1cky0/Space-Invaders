package gui.states.multi;

import logic.environment.manager.field.MovingDirections;
import logic.sprite.dinamic.SpaceShip;
import main.Dimensions;

public class ShipManager {

    private SpaceShip spaceShip;

    public ShipManager(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
    }

    public void shipMovement(MovingDirections md, int delta){

        if(((spaceShip.getX() + Dimensions.SHIP_WIDTH) < Dimensions.MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight(delta);
        }

        if((spaceShip.getX() > Dimensions.MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft(delta);
        }

    }

    public float getX(){
        return spaceShip.getX();
    }

}