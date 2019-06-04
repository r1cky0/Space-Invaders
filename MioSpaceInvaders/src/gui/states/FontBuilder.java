package gui.states;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

public class FontBuilder {
    private Font fontTitle;
    private UnicodeFont uniFontTitle;

    public FontBuilder(){
        try{
        fontTitle = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**Metodo che esegue le operazioni di creazione font comuni a tutti gli stati
     *
     * @param size Dimensioni da apportare al font (ricordare di inserirle dinamiche alla finestra)
     * @return
     */
    public UnicodeFont Build(float size){
        try {
            fontTitle = this.fontTitle.deriveFont(Font.BOLD, size);
            uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();

        }catch (Exception e) {
                e.printStackTrace();
            }
        return uniFontTitle;
    }
}
