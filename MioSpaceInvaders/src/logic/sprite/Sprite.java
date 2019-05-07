package logic.sprite;

import service.facade.Facade;

public abstract class Sprite extends Facade {

    private Coordinate coordinate;
    private double size;

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
        super.setCoordinate(coordinate);
    }

    public void setY(double y) {
        coordinate.setY(y);
        super.setCoordinate(coordinate);
    }

    public boolean collides(Sprite sprite) {
        return super.collides(sprite.getShape());
    }

}
