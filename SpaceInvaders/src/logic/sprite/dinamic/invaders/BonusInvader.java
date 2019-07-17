package logic.sprite.dinamic.invaders;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;

/**
 * Classe che rappresenta l'alieno bonus.
 * Contiene il valore che si somma allo score quando viene colpito.
 */
public class BonusInvader extends Sprite {

    private final int value = 100;

    public BonusInvader(Coordinate coordinate){
        super(coordinate, Dimensions.BONUSINVADER_WIDTH, Dimensions.BONUSINVADER_HEIGHT, Target.BONUS_INVADER);
    }

    /**
     * A differenza degli altri invaders, quello bonus si sposta solo verso sinistra
     *
     * @param delta velocità
     */
    public void moveLeft(int delta){
        float HORIZONTAL_OFFSET = 0.02f;
        super.setX(super.getX() - HORIZONTAL_OFFSET * delta);
    }

    public int getValue() {
        return value;
    }

}
