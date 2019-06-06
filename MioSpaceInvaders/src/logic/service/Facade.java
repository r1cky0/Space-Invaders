package logic.service;


import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Facade {

    private Shape shape;

    public Facade(Coordinate coordinate, double size) {
        shape = new Rectangle((float)coordinate.getX(), (float) coordinate.getY(), (float) size, (float) size);
    }

    /**
     * Sfrutta la funzione di slick collides per rilevare collisioni tra shape diverse
     * @param shape La forma con cui fare il check della collisione
     * @return Ritorna un booleano settato a true se collisione avvenuta
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
     * @param image Immagine
     */
    public void render(Image image){
        image.draw(shape.getX(),shape.getY(), shape.getWidth(), shape.getHeight());
    }

}