package logic.elements;

import logic.Player;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Ship implements CollisionElement {

    protected Image image;
    private float x;
    private float y;
    private float size;
    private GameContainer container;
    private static final float PROP_SIZE = 0.1f;
    private static final float PROP_MOVE = 0.005f;
    private Shape shape;
    private int life;
    //private Player player;

    public Ship(GameContainer container) throws SlickException{
        this.container = container;
        size = container.getHeight()*PROP_SIZE;
        x = (container.getWidth()/2f)-size/2f;
        y = 9*(container.getHeight()/10f);
        image = new Image("res/Laser_Cannon.jpg");
        shape = new Rectangle(x, y, size, size);
        //this.player = player;
        this.life = 3;
    }

    @Override
    public void update(GameContainer container, int delta) {

    }

    @Override
    public void render(GameContainer container, Graphics g) {
        image.draw(x,y,size,size);
    }

    public void move(MovingDirections md){
        if (md == MovingDirections.RIGHT && x+size+(container.getWidth()*PROP_MOVE) < container.getWidth()){
            x += container.getWidth()*PROP_MOVE;
        }
        else if(md == MovingDirections.LEFT && x-(container.getWidth()*PROP_MOVE) >0){
            x-= container.getWidth()*PROP_MOVE;
        }
    }

    public Shape getShape() {
        return shape;
    }

    public float getX() {
        return x + size/3f;
    }

    public float getY() {
        return y;
    }

    public boolean collides(Shape s) {
        return shape.intersects(s);
    }

    public int getLife() {
        return life;
    }

    public void decreseLife() {
        life--;
    }

    public float getSize() {
        return size;
    }
}
