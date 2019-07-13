package gui.elements;

import logic.sprite.Sprite;
import main.SpaceInvaders;
import org.newdawn.slick.Image;

public abstract class GraphicSprite implements Drawable{

    private Image image;
    private float width;
    private float height;

    GraphicSprite(Image image, float width, float height){
        this.image = image;
        this.width = width * SpaceInvaders.SCALE_X;
        this.height = height * SpaceInvaders.SCALE_Y;
    }

    GraphicSprite(float width, float height){
        this.width = width * SpaceInvaders.SCALE_X;
        this.height = height * SpaceInvaders.SCALE_Y;
    }

    public void render(Sprite sprite){
        image.draw(sprite.getX()*SpaceInvaders.SCALE_X,sprite.getY()*SpaceInvaders.SCALE_Y, width, height);
    }

    public void render(float x, float y){
        image.draw(x*SpaceInvaders.SCALE_X,y*SpaceInvaders.SCALE_Y, width, height);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
