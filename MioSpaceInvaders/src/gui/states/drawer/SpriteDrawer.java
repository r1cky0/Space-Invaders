package gui.states.drawer;

import main.SpaceInvaders;
import org.newdawn.slick.Image;

public class SpriteDrawer implements Drawable {

    public void render(Image image, float x, float y, float width, float height){
        image.draw((x*SpaceInvaders.SCALE_X),(y*SpaceInvaders.SCALE_Y), (width*SpaceInvaders.SCALE_X),
                (height*SpaceInvaders.SCALE_Y));
    }
}
