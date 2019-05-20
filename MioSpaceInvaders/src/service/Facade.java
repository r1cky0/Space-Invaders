package service;


import logic.sprite.Coordinate;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Facade {

    private Shape shape;

    public Facade(Coordinate coordinate, double size) {
        shape = new Rectangle((float)coordinate.getX(), (float) coordinate.getY(), (float) size, (float) size);
    }

    public boolean collides(Shape shape){
        return this.shape.intersects(shape);
    }

    public void setCoordinate(Coordinate coordinate){
        shape.setLocation((float)coordinate.getX(),(float)coordinate.getY());
    }

    public Shape getShape(){
        return shape;
    }

    public void render (String path) throws SlickException{
        Image image = new Image(path);
        image.draw(shape.getX(),shape.getY(), shape.getWidth(), shape.getHeight());
    }

}