package logic.sprite;

import logic.service.Facade;
import org.newdawn.slick.geom.Shape;

public class Sprite {
    private Coordinate coordinate;
    private Facade facade;
    private float width;
    private float height;
    private Target target;

    public Sprite(Coordinate coordinate, float width, float height, Target target) {
        facade = new Facade(coordinate, width, height);
        this.coordinate = coordinate;
        this.width = width;
        this.height = height;
        this.target = target;
    }

    public boolean collides(Sprite sprite) {
        return facade.collides(sprite.getShape());
    }

    public void setCoordinate(Coordinate coordinate) {
        facade.setCoordinate(coordinate);
        this.coordinate = coordinate;
    }

    public void setX(float x) {
        coordinate.setX(x);
        facade.setCoordinate(coordinate);
    }

    public void setY(float y) {
        coordinate.setY(y);
        facade.setCoordinate(coordinate);
    }

    public void setTarget(Target target){
        this.target = target;
    }

    public Target getTarget(){
        return target;
    }

    public Facade getFacade(){
        return facade;
    }

    public Shape getShape(){
        return facade.getShape();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public float getX() {
        return coordinate.getX();
    }

    public float getY() {
        return coordinate.getY();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
