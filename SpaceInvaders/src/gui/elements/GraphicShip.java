package gui.elements;

import main.Dimensions;
import org.newdawn.slick.Image;

public class GraphicShip extends GraphicSprite {

    public GraphicShip(Image image){
        super(image, Dimensions.SHIP_WIDTH, Dimensions.SHIP_HEIGHT);
    }
}
