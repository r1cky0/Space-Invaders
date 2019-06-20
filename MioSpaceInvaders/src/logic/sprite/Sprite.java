package logic.sprite;

import logic.service.Facade;

public class Sprite extends Facade {
    private Coordinate coordinate;
    private double width;
    private double height;

    public Sprite(Coordinate coordinate, double width, double height) {
        super(coordinate,width, height);
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
        this.coordinate = coordinate;
    }

    public double getX() {
        return coordinate.getX();
    }

    public double getY() {
        return coordinate.getY();
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
