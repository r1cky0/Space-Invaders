package logic.manager.game.commands;

import logic.manager.field.FieldManager;
import logic.sprite.dinamic.SpaceShip;

/**
 * Classe che rappresenta il comando di sparo.
 */
public class Shot implements Command {

    /**
     * Metodo che esegue lo sparo della ship.
     *
     * @param fieldManager gestore campo di gioco
     * @param spaceShip ship che spara
     * @param delta velocità
     */
    public void exe(FieldManager fieldManager, SpaceShip spaceShip, int delta){
        fieldManager.shipShot(spaceShip);
    }

}
