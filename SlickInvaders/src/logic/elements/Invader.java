package logic.elements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class Invader implements CollisionElement{
    private Shape shape;
    private int value;

    public Invader (int value) {
        this.value = value;
    }

    public Shape getShape() {
        return shape;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void update(GameContainer container, int delta) {
    }

    @Override
    public void render(GameContainer container, Graphics g) {

    }
}
