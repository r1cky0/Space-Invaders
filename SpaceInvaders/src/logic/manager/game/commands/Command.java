package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.sprite.dinamic.SpaceShip;

public abstract class Command {

    public abstract void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta);
}
