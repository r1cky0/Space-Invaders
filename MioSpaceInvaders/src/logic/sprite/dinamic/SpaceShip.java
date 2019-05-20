package logic.sprite.dinamic;

import logic.sprite.Coordinate;

public class SpaceShip extends AbstractMovable {

    private int life, currentScore;

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

    public void moveUp(){}

    public void moveDown() {}

    public void decreaseLife() {
        life -= 1;
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
