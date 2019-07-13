package gui.elements;

import main.Dimensions;
import org.newdawn.slick.Image;

public class GraphicBullet extends GraphicSprite {

    public GraphicBullet(Image image){
        super(image, Dimensions.BULLET_WIDTH, Dimensions.BULLET_HEIGHT);
    }
}
