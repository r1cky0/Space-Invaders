package logic.elements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

public class Bullet implements CollisionElement{
    private Image bullet;
    private float x;
    private float y;
    private GameContainer container;
    private static final float PROP_SPEED = 0.05f;
    private boolean end = false;

    public Bullet(GameContainer container,float x, float y) throws SlickException {
        this.container = container;
        bullet = new Image("res/Laser.png");
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(GameContainer container, int delta) {
        if (y > 0) {
            y -= container.getHeight()*PROP_SPEED;
        }
        else{
            end = true;
        }
    }

    @Override
    public void render(GameContainer container, Graphics graphics) {
        graphics.drawImage(bullet, x, y);
    }

    @Override
    public boolean collides(Shape s) {
        return false;
    }

    public boolean getEnd(){return end;}

}
