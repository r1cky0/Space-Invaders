package logic.elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import states.SinglePlayerState;

public class Bullet implements CollisionElement{
    private Image image;
    private float x;
    private float y;
    private Shape shape;
    private float size;

    private GameContainer container;
    private static final float PROP_SPEED = 0.002f;
    private static final float PROP_SIZE = 0.03f;
    private static final float PROP_MOVE = 0.005f;
    private boolean end;

    public Bullet(GameContainer container,float x, float y) throws SlickException {
        this.container = container;
        image = new Image("res/Laser.png");
        this.x = x;
        this.y = y;
        this.end = false;
        size = container.getHeight()*PROP_SIZE;
        this.shape = new Rectangle(x,y,size,size);
    }

    @Override
    public void update(GameContainer container, int delta) {
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        image.draw(x,y,size,size);
        //graphics.drawImage(bullet, x, y);
        //graphics.setColor(Color.green);
        //graphics.fill(shape);
    }

    @Override
    public boolean collides(Shape s) {
        return shape.intersects(s);
    }

    public void move(MovingDirections md){
        if (md == MovingDirections.RIGHT && x+size+(container.getWidth()*PROP_MOVE) < container.getWidth()){
            x += container.getWidth()*PROP_MOVE;
        }
        else if(md == MovingDirections.LEFT && x-(container.getWidth()*PROP_MOVE) >0){
            x-= container.getWidth()*PROP_MOVE;
        }
    }

    public boolean getEnd() {
        return end;
    }

    public float getY() {
        return y;
    }

}
