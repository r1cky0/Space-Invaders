package logic.sprite.dinamic;

import logic.sprite.Coordinate;
import logic.sprite.Sprite;

public class SpaceShip extends Sprite {

    private int life, currentScore;
    private final double HORIZONTAL_OFFSET = 7;

    public SpaceShip(Coordinate coordinate, double size) {
        super(coordinate, size);
        life = 3;
        currentScore = 0;
    }

    public int getLife() {
        return life;
    }

    public int getCurrentScore(){
        return currentScore;
    }

    public void moveLeft() {
        super.setX(super.getX() - HORIZONTAL_OFFSET);
    }

    public void moveRight() {
        super.setX(super.getX() + HORIZONTAL_OFFSET);
    }

    public void decreaseLife() {
        life = life - 1;
    }

    public void incrementLife() {
        if(life<3) {
            life += 1;
        }
    }

    public void incrementCurrentScore(int value){
        currentScore += value;
    }
}
