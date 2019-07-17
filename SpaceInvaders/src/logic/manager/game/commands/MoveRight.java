package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.manager.field.MovingDirections;
import logic.sprite.dinamic.SpaceShip;

public class MoveRight extends Command {

    public void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta){
        fieldManager.shipMovement(spaceShip, MovingDirections.RIGHT, delta);
    }
}
