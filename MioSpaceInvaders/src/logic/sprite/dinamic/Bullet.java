package logic.sprite.dinamic;

import logic.sprite.Coordinate;

public class Bullet extends AbstractMovable{

    public double VERTICAL_OFFSET = 15;

    public Bullet(Coordinate coordinate, double size) {
        super(coordinate,size);
    }

    public String toString() {
        return super.toString();
    }

    public void moveLeft() {}

    public void moveRight() {}

    @Override
    public void moveUp(){
        super.setY(super.getY() - VERTICAL_OFFSET);
    }

    public boolean endReached(){
        if(super.getY() <= 0){
            return true;
        }
        return false;
    }

}
