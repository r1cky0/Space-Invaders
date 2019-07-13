package gui.elements;

import logic.sprite.Sprite;
import logic.sprite.unmovable.Brick;
import main.Dimensions;
import main.SpaceInvaders;
import org.newdawn.slick.Image;

import java.util.ArrayList;

public class GraphicBrick extends GraphicSprite{

    private ArrayList<Image> images;

    public GraphicBrick(){
        super(Dimensions.BRICK_WIDTH, Dimensions.BRICK_HEIGHT);
        images = new ArrayList<>();
    }

    public void addImage(Image image){
        images.add(image);
    }

    public void render(Sprite sprite){
        Brick brick = (Brick) sprite;
        images.get(4 - brick.getLife()).draw(sprite.getX() * SpaceInvaders.SCALE_X,
                sprite.getY() * SpaceInvaders.SCALE_Y, super.getWidth(), super.getHeight());
     }

    public void render(int life, float x, float y){
        images.get(4 - life).draw(x * SpaceInvaders.SCALE_X,
                y * SpaceInvaders.SCALE_Y, super.getWidth(), super.getHeight());
    }

}
