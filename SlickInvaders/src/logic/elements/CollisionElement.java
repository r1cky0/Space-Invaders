package logic.elements;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public interface CollisionElement {
    public void update(GameContainer container, int delta);
    public void render(GameContainer container, Graphics g);
}
