package logic.elements;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import states.SinglePlayerState;

public class Bullet implements CollisionElement{
    //private Image bullet;
    private float x;
    private float y;
    private Shape shape;
    private GameContainer container;
    private static final float PROP_SPEED = 0.002f;
    private boolean end;

    public Bullet(GameContainer container,float x, float y) throws SlickException {
        this.container = container;
        //bullet = new Image("res/Laser.png");
        this.x = x;
        this.y = y;
        this.end = false;
        this.shape = new Rectangle(x,y,5,5);
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (y > 0) {
            y -= 0.01*container.getHeight()*PROP_SPEED;
        }
        else{
            end = true;
        }
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        //graphics.drawImage(bullet, x, y);
        graphics.setColor(Color.green);
        graphics.fill(shape);
    }

    @Override
    public boolean collides(Shape s) {
        return shape.intersects(s);
    }

    public boolean getEnd() {
        return end;
    }

}
