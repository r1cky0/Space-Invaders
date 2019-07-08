package logic.sprite;

import logic.service.Facade;

public class Sprite extends Facade {
    private Coordinate coordinate;

    public Sprite(Coordinate coordinate, float width, float height) {
        super(coordinate,width, height);
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        super.setCoordinate(coordinate);
        this.coordinate = coordinate;
    }

    public float getX() {
        return coordinate.getX();
    }

    public float getY() {
        return coordinate.getY();
    }

    public void setX(float x) {
        coordinate.setX(x);
        super.setCoordinate(coordinate);
    }

    public void setY(float y) {
        coordinate.setY(y);
        super.setCoordinate(coordinate);
    }

    public boolean collides(Sprite sprite) {
        return super.collides(sprite.getShape());
    }

}
