package gui.states.drawer;

import main.SpaceInvaders;
import org.newdawn.slick.Image;

public class SpriteDrawer implements Drawable {

    public void render(Image image, double x, double y, double width, double height){
        image.draw((float) (x*SpaceInvaders.SCALE_X),(float) (y*SpaceInvaders.SCALE_Y),
                (float) (width*SpaceInvaders.SCALE_X), (float) (height*SpaceInvaders.SCALE_Y));
    }
}
