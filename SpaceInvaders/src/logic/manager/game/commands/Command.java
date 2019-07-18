package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.sprite.dinamic.SpaceShip;

/**
 * Interfaccia generica dei comandi del giocatore.
 */
public interface Command {

    public abstract void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta);

}
