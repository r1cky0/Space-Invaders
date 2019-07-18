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

/**
 * Classe che si occupa di disegnare gli sprite.
 * Contiene una mappa con Target e immagine associata.
 */
public class SpriteDrawer implements Drawable{
    private ReadXmlFile readerXmlFile;
    private HashMap<Target, Image> images;

    public SpriteDrawer() {
        readerXmlFile = new ReadXmlFile();
        images = new HashMap<>();
        try {
            images.put(Target.INVADER, new Image(readerXmlFile.read("defaultInvader0")));
            images.put(Target.BONUS_INVADER, new Image(readerXmlFile.read("bonusInvader")));
            images.put(Target.BULLET, new Image(readerXmlFile.read("defaultBullet")));
            images.put(Target.SHIP, new Image(readerXmlFile.read("ship0")));
            images.put(Target.SHIPMULTI, new Image(readerXmlFile.read("shipMulti")));
            String target = "BRICK";
            for (int i = 0; i < 4; i++) {
                images.put(Target.valueOf(target + i), new Image(readerXmlFile.read("brick" + i)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    /**
     * Funzione per l'aggiunta dell'immagine personalizzata della ship del giocatore.
     *
     * @param shipType tag ship
     */
    public void addShipImage(String shipType) throws SlickException {
        images.replace(Target.SHIP, new Image(readerXmlFile.read(shipType)));
    }

    /**
     * Funzione di renderizzazione che in base allo sprite disegna l'immagine associata.
     *
     * @param sprite sprite da renderizzare
     */
    public void render(Sprite sprite){
        float x = sprite.getX() * SpaceInvaders.SCALE_X;
        float y = sprite.getY() * SpaceInvaders.SCALE_Y;
        float width = sprite.getWidth() * SpaceInvaders.SCALE_X;
        float height = sprite.getHeight() * SpaceInvaders.SCALE_Y;
        images.get(sprite.getTarget()).draw(x, y, width, height);
    }

}
