package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class SpaceShip extends Sprite implements Movable {

    private int life, currentscore;

    public SpaceShip(Coordinate coordinate) {
        super(coordinate);
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

    @Override
    public String toString() {
        return "{" + "life=" + life + '}' + super.toString();
    }

    @Override
    public Coordinate moveLeft(double x) {
        super.setX(super.getX() - x);
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveRight(double x) {
        super.setX(super.getX() + x);
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveUp(double y) {
        return super.getCoordinate();
    }

    @Override
    public Coordinate moveDown(double y) {
        return super.getCoordinate();
    }

    public int decreaseLife(int value) {
        life -= value;
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
