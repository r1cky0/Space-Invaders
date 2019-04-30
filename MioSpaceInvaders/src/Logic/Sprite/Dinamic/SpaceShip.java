package Logic.Sprite.Dinamic;

import Logic.Coordinate;
import Logic.Sprite.Dinamic.Interface.Movable;
import Logic.Sprite.Sprite;

public class SpaceShip extends Sprite implements Movable {

    private int life;

    public SpaceShip(Coordinate coordinate) {
        super(coordinate);
        life = 3;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
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

    public int decreaseLife(int l) {
        life -= l;
        return life;
    }

    public int incrementLife(int l) {
        life += l;
        return life;
    }
}
