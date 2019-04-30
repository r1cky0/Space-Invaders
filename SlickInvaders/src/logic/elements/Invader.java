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
    private MovingDirections md = MovingDirections.RIGHT;
    private boolean moveDown = false;

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
        LateralMove(delta);
        MoveDown(delta);
        shape.setX(x);
        shape.setY(y);
    }

    public void LateralMove(int delta){
        if(md == MovingDirections.RIGHT){
                x+=size*PROP_SPEED*delta;
        }
        if(md == MovingDirections.LEFT){
                x-=size*PROP_SPEED*delta;
        }
    }

    public void MoveDown(int delta){
        if(moveDown == true){
            y+=size*100*PROP_SPEED*delta;
            moveDown = false;
        }
    }

    public Shape getShape() {
        return shape;
    }

    public int getValue() {
        return value;
    }

    public float getX(){return x + size/3f;}

    public void setX(float x){this.x += x;}

    public float getY(){return this.y;}

    public void sety(float y){this.y += y;}

    public float getSize(){return this.size;}

    public void setMd(MovingDirections md){this.md = md;}

    public void setMoveDown(boolean moveDown){this.moveDown = moveDown;}

}
