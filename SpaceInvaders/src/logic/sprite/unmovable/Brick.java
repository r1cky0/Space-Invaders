package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;

/**
 * Classe che rappresenta un 'mattoncino' di cui sono costituiti i bunker.
 * Ogni mattoncino ha una vita che si decrementa tutte le volte che viene colpito.
 */
public class Brick extends Sprite {
    private int life;

    public Brick(Coordinate coordinate) {
        super(coordinate, Dimensions.BRICK_WIDTH, Dimensions.BRICK_HEIGHT, Target.BRICK0);
        life = 4;
    }

    public void setLife(int life){
        this.life = life;
        updateTarget();
    }

    /**
     * Decremento vita del brick.
     */
    public void decreaseLife() {
        life -= 1;
        updateTarget();
    }

    /**
     * Set del nuovo target del brick dopo decremento vita.
     */
    private void updateTarget(){
        if(life>0) {
            String target = "BRICK" + (4 - life);
            super.setTarget(Target.valueOf(target));
        }
    }

    public int getLife() {
        return life;
    }

}
