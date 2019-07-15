package logic.sprite.unmovable;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.Target;
import main.Dimensions;

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

    public int getLife() {
        return life;
    }

    void decreaseLife() {
        life -= 1;
        updateTarget();
    }

    private void updateTarget(){
        if(life>0) {
            String target = "BRICK" + (4 - life);
            super.setTarget(Target.valueOf(target));
        }
    }

}
