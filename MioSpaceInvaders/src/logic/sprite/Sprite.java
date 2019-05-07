package logic.sprite;

import service.facade.Facade;

public abstract class Sprite extends Facade {

    private Coordinate coordinate;
    private double size;
    // image

    public Sprite(Coordinate coordinate, double size) {
        super(coordinate,size);
        this.coordinate = coordinate;
        this.size = size;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return coordinate.toString();
    }

    public double getX() {
        return coordinate.getX();
    }

    public double getY() {
        return coordinate.getY();
    }

    public double getSize(){
        return size;
    }

    public void setX(double x) {
        coordinate.setX(x);
    }

    public void setY(double y) {
        coordinate.setY(y);
    }

    public void setSize(double size){
        this.size = size;
    }

    public boolean collides(Sprite sprite) {
        return super.collides(sprite.getShape());
    }

}
