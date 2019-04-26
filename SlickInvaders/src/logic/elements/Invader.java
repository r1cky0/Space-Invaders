package logic.elements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Invader implements CollisionElement{
    private Shape shape;
    private int value;
    private Image image;
    private float x;
    private float y;
    private float size;

    private GameContainer container;
    private static final float PROP_SPEED = 0.0008f;

    public Invader(GameContainer container,float x, float y, float size, int value, String path) throws SlickException {
        this.container = container;
        image = new Image(path);
        this.x = x;
        this.y = y;
        this.size = size;
        this.shape = new Rectangle(x, y, size, size);
        this.value = value;
    }

    @Override
    public void render(GameContainer container, Graphics g) {
        image.draw(x,y,size,size);
    }

    @Override
    public void update(GameContainer container, int delta) {
    }

    public Shape getShape() {
        return shape;
    }

    public int getValue() {
        return value;
    }

    public float getX(){return x + size/3f;}

    public float getY(){return this.y;}

    public float getSize(){return this.size;}

}
