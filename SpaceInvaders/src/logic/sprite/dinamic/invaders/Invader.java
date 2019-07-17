package logic.sprite.dinamic.invaders;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;

/**
 * Classe che rappresenta gli invader.
 * Contiene il valore che si somma allo score quando viene colpito.
 */
public class Invader extends Sprite {

    private final int value = 10;
    public static float HORIZONTAL_OFFSET = 2;

    public Invader(Coordinate coordinate) {
        super(coordinate, Dimensions.INVADER_WIDTH, Dimensions.INVADER_HEIGHT, Target.INVADER);
    }

    /**
     * Metodo per il movimento in basso dell'alieno.
     *
     */
    public void moveDown() {
        float VERTICAL_OFFSET = 8;
        super.setY(super.getY() + VERTICAL_OFFSET);
    }

    /**
     * Metodo per il movimento a sinistra dell'alieno.
     *
     */
    public void moveLeft() {
        super.setX(super.getX() - HORIZONTAL_OFFSET);
    }

    /**
     * Metodo per il movimento a destra dell'alieno.
     *
     */
    public void moveRight() {
        super.setX(super.getX() + HORIZONTAL_OFFSET);
    }

    public int getValue() {
        return value;
    }

}
