package service;


import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Facade {

    private Shape shape;
    private final int DURATION = 1000;

    public Facade(Coordinate coordinate, double size) {
        shape = new Rectangle((float)coordinate.getX(), (float) coordinate.getY(), (float) size, (float) size);
    }

    /**
     * Sfrutta la funzione di slick collides per rilevare collisioni tra shape diverse
     * @param shape
     * @return
     */
    public boolean collides(Shape shape){
        return this.shape.intersects(shape);
    }

    public void setCoordinate(Coordinate coordinate){
        shape.setLocation((float)coordinate.getX(),(float)coordinate.getY());
    }

    public Shape getShape(){
        return shape;
    }

    /**
     * Renderizza lo sprite scelto andando a ricavare l' immagine dal path fornito e settando le dimensioni prelevandole
     * dalla shape corrispondente
     * @param path
     * @throws SlickException
     */
    public void render (String path) throws SlickException{
        Image image = new Image(path);
        image.draw(shape.getX(),shape.getY(), shape.getWidth(), shape.getHeight());
    }

    public void render (Animation animation) throws SlickException{
        animation.draw(shape.getX(),shape.getY(), shape.getWidth(), shape.getHeight());
    }
}