package gui.drawer;

import main.SpaceInvaders;
import org.newdawn.slick.Image;

public class SpriteDrawer implements Drawable {

    /**
     * Metodo di renderizzazione degli sprite SENZA trasparenza manuale
     *
     * @param image immagine da renderizzare
     * @param x coordinata orizzontale dell' immagine
     * @param y coordinata verticale dell' immagine
     * @param width larghezza immagine
     * @param height altezza immagine
     */
    public void render(Image image, float x, float y, float width, float height){
        image.draw((x*SpaceInvaders.SCALE_X),(y*SpaceInvaders.SCALE_Y), (width*SpaceInvaders.SCALE_X),
                (height*SpaceInvaders.SCALE_Y));
    }

    /**
     * Metodo di renderizzazione degli sprite CON trasparenza manuale
     *
     * @param image immagine da renderizzare
     * @param x coordinata orizzontale dell' immagine
     * @param y coordinata verticale dell' immagine
     * @param width larghezza immagine
     * @param height altezza immagine
     * @param alpha livello d itrasparenza
     */
    public void render(Image image, float x, float y, float width, float height, float alpha){
        image.draw((x*SpaceInvaders.SCALE_X),(y*SpaceInvaders.SCALE_Y), (width*SpaceInvaders.SCALE_X),
                (height*SpaceInvaders.SCALE_Y));
        image.setAlpha(alpha);
    }
}
