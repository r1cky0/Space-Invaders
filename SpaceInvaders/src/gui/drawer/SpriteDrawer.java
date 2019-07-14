package gui.drawer;

import logic.manager.file.ReadXmlFile;
import logic.sprite.Sprite;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;
import main.SpaceInvaders;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;

public class SpriteDrawer {
    private ReadXmlFile readerXmlFile;
    private Image invaderImage;
    private Image invaderBonusImage;
    private Image bulletImage;
    private Image shipImage;
    private ArrayList<Image> brickImages;

    public SpriteDrawer() {
        readerXmlFile = new ReadXmlFile();
        brickImages = new ArrayList<>();
        try {
            invaderImage = new Image(readerXmlFile.read("defaultInvader"));
            invaderBonusImage = new Image(readerXmlFile.read("bonusInvader"));
            bulletImage = new Image(readerXmlFile.read("defaultBullet"));
            shipImage = new Image(readerXmlFile.read("ship0"));
            for (int i = 0; i < 4; i++) {
                brickImages.add(new Image(readerXmlFile.read("brick" + i)));
            }

        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void addShipImage(String shipType) throws SlickException {
        shipImage = new Image(readerXmlFile.read(shipType));
    }

    private void draw(Image image, Sprite sprite){
        float x = sprite.getX() * SpaceInvaders.SCALE_X;
        float y = sprite.getY() * SpaceInvaders.SCALE_Y;
        float width = sprite.getWidth() * SpaceInvaders.SCALE_X;
        float height = sprite.getHeight() * SpaceInvaders.SCALE_Y;
        image.draw(x, y, width, height);
    }

    public void render(Invader invader){
        draw(invaderImage, invader);
    }

    public void render(BonusInvader bonusInvader){
        draw(invaderBonusImage, bonusInvader);
    }

    public void render(Bullet bullet){
        draw(bulletImage, bullet);
    }

    public void render(Brick brick){
        if(brick.getLife() > 0) {
            draw(brickImages.get(4 - brick.getLife()), brick);
        }
    }

    public void render(SpaceShip spaceShip) {
        draw(shipImage, spaceShip);
    }

}
