package logic.sprite.dinamic;

import logic.sprite.Coordinate;

public class SpaceShip extends AbstractMovable {

    private int life, currentscore;

    public SpaceShip(Coordinate coordinate, double size) {
        super(coordinate, size);
        life = 3;
        currentscore = 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCurrentscore(){
        return currentscore;
    }

    public void setCurrentscore(int currentscore){
        this.currentscore = currentscore;
    }

    public String toString() {
        return "{" + "life=" + life + '}' + super.toString();
    }

    public Coordinate moveUp() {
        return super.getCoordinate();
    }

    public Coordinate moveDown() {
        return super.getCoordinate();
    }

    public int decreaseLife(int value) {
        life -= 1;
        return life;
    }

    public int incrementLife(int value) {
        life += value;
        return life;
    }

    public int incrementCurrentScore(int value){
        currentscore += value;
        return currentscore;
    }
}
