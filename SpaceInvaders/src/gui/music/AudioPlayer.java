package gui.music;

import logic.manager.file.ReadXmlFile;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;

/**
 * Classe che si occupa di caricare gli audio e i suoni del gioco dalle risorse.
 */
public class AudioPlayer {
    private Music gameMusic;
    private Music gameOverMusic;
    private Music menuMusic;
    private Sound shotEffect;
    private Sound explosionEffect;
    private float pitch;
    private float volume;

    public AudioPlayer() {
        ReadXmlFile readXmlFile = new ReadXmlFile();
        try {
            gameMusic = new Music(readXmlFile.read("gameMusic"));
            gameOverMusic = new Music(readXmlFile.read("gameOverMusic"));
            menuMusic = new Music(readXmlFile.read("menuMusic"));
            shotEffect = new Sound(readXmlFile.read("shotEffect"));
            explosionEffect = new Sound(readXmlFile.read("explosionEffect"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
        pitch = 1.0f;
        volume = 50.0f;
    }

    /**
     * Riproduzione del file audio della partita in modo ripetuto.
     */
    public void game(){
        gameMusic.loop(pitch, volume);
    }

    /**
     * Riproduzione del file audio del game over in modo ripetuto.
     */
    public void gameOver(){
        gameOverMusic.loop(pitch, volume);
    }

    /**
     * Riproduzione del file audio dell menu in modo ripetuto.
     */
    public void menu(){
        menuMusic.loop(pitch, volume);
    }

    /**
     * Riproduzione del suono di esplosione quando la ship viene colpita.
     */
    public void explosion(){
        explosionEffect.play(pitch, volume);
    }

    /**
     * Riproduzione del suono di sparo della ship.
     */
    public void shot(){
        shotEffect.play(pitch, volume);
    }
}
