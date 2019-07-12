package logic.sprite.dinamic.invaders;

import logic.sprite.Coordinate;

public class BonusInvader extends Invader {

    public BonusInvader(Coordinate coordinate, float width, float height, int value){
        super(coordinate, width, height, value);
    }

    /**
     * A differenza degli altri invaders, quello bonus si sposta solo orizzontalmente
     * @param delta tempo di aggionramento della grafica
     */
    public void moveLeft(int delta){
        float HORIZONTAL_OFFSET = 0.02f;
        super.setX(super.getX() - HORIZONTAL_OFFSET * delta);
    }

}
