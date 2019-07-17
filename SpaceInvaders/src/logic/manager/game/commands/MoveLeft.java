package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.manager.field.MovingDirections;
import logic.sprite.dinamic.SpaceShip;

/**
 * Classe che rappresenta il comando di movimento a sinistra.
 */
public class MoveLeft extends Command {

    /**
     * Metodo che esegue lo spostamento a sinistra della ship.
     *
     * @param fieldManager gestore campo di gioco
     * @param spaceShip ship che si muove
     * @param delta velocità
     */
    public void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta){
        fieldManager.shipMovement(spaceShip, MovingDirections.LEFT, delta);
    }

}
