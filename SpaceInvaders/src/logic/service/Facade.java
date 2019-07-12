package logic.service;


import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Facade {
    private Shape shape;

    public Facade(Coordinate coordinate, float width, float height) {
        shape = new Rectangle(coordinate.getX(), coordinate.getY(), width, height);
    }

    /**
     * Sfrutta la funzione di slick collides per rilevare collisioni tra shape diverse
     *
     * @param shape La forma con cui fare il check della collisione
     * @return Ritorna un booleano settato a true se collisione avvenuta
     */
    public boolean collides(Shape shape){
        return this.shape.intersects(shape);
    }

    public void setCoordinate(Coordinate coordinate){
        shape.setLocation(coordinate.getX(), coordinate.getY());
    }

    public Shape getShape(){
        return shape;
    }

}