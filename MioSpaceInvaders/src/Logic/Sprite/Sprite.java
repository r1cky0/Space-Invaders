package Logic.Sprite;

import Logic.Coordinate;

public abstract class Sprite {

    private Coordinate coordinate;
    // image

    public Sprite(Coordinate coordinate) {
        this.coordinate = coordinate;
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

    public void setX(double x) {
        coordinate.setX(x);
    }

    public void setY(double y) {
        coordinate.setY(y);
    }
}
