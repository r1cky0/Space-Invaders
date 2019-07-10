package gui.states;

import gui.music.AudioPlayer;
import logic.environment.manager.file.ReadXmlFile;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.*;

public abstract class BasicInvaderState extends BasicGameState {

    protected AudioPlayer audioplayer;
    protected ReadXmlFile readerXmlFile;
    protected Menu menu;

    public BasicInvaderState(){
        audioplayer = new AudioPlayer();
        readerXmlFile = new ReadXmlFile();
    }

    /**
     * Metodo che esegue le operazioni di creazione font comuni a tutti gli stati
     *
     * @param size Dimensioni da apportare al font (ricordare di inserirle dinamiche alla finestra)
     */
    public UnicodeFont build(float size) {
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontTitle = fontTitle.deriveFont(Font.BOLD, size);
            UnicodeFont uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
            return uniFontTitle;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
