package logic.environment.manager.field;

import logic.environment.manager.field.MovingDirections;
import logic.sprite.dinamic.SpaceShip;
import main.Dimension;

public class ShipManager {

    private SpaceShip spaceShip;

    public ShipManager(SpaceShip spaceShip){
        this.spaceShip = spaceShip;
    }

    public void shipMovement(MovingDirections md, int delta){

        if(((spaceShip.getX() + Dimension.SHIP_WIDTH) < Dimension.MAX_WIDTH) && (md == MovingDirections.RIGHT)){
            spaceShip.moveRight(delta);
        }

        if((spaceShip.getX() > Dimension.MIN_WIDTH) && (md == MovingDirections.LEFT)){
            spaceShip.moveLeft(delta);
        }

    }

    public float getX(){
        return spaceShip.getX();
    }

    public float getY(){
        return spaceShip.getY();
    }
}
