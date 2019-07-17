package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.sprite.dinamic.SpaceShip;

public class Shot extends Command {

    public void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta){
        fieldManager.shipShot(spaceShip);
    }

}
