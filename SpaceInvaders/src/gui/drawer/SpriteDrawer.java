package gui.drawer;

import logic.manager.file.ReadXmlFile;
import logic.sprite.Sprite;
import logic.sprite.Target;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;
import main.SpaceInvaders;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import java.util.ArrayList;
import java.util.HashMap;

public class SpriteDrawer {
    private ReadXmlFile readerXmlFile;
    private HashMap<Target, Image> images;

    public SpriteDrawer() {
        readerXmlFile = new ReadXmlFile();
        images = new HashMap<>();
        try {
            images.put(Target.INVADER, new Image(readerXmlFile.read("defaultInvader")));
            images.put(Target.BONUS_INVADER, new Image(readerXmlFile.read("bonusInvader")));
            images.put(Target.BULLET, new Image(readerXmlFile.read("defaultBullet")));
            images.put(Target.SHIP, new Image(readerXmlFile.read("ship0")));
            String target = "BRICK";
            for (int i = 0; i < 4; i++) {
                images.put(Target.valueOf(target + i), new Image(readerXmlFile.read("brick" + i)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void addShipImage(String shipType) throws SlickException {
        images.replace(Target.SHIP, new Image(readerXmlFile.read(shipType)));
    }

    public void render(Sprite sprite){
        float x = sprite.getX() * SpaceInvaders.SCALE_X;
        float y = sprite.getY() * SpaceInvaders.SCALE_Y;
        float width = sprite.getWidth() * SpaceInvaders.SCALE_X;
        float height = sprite.getHeight() * SpaceInvaders.SCALE_Y;
        images.get(sprite.getTarget()).draw(x, y, width, height);
    }

}
