package gui.states;

import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

public abstract class BasicInvaderState extends BasicGameState {
    private Font fontTitle;
    private UnicodeFont uniFontTitle;

    /**
     * Metodo che esegue le operazioni di creazione font comuni a tutti gli stati
     *
     * @param size Dimensioni da apportare al font (ricordare di inserirle dinamiche alla finestra)
     */
    public UnicodeFont build(float size) {
        try {
            fontTitle = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontTitle = this.fontTitle.deriveFont(Font.BOLD, size);
            uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uniFontTitle;
    }
}
