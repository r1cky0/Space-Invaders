package gui.music;

import logic.environment.manager.file.ReadXmlFile;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;

public class AudioPlayer {
    private Music gameMusic;
    private Music gameOverMusic;
    private Music menuMusic;
    private Sound shotEffect;
    private Sound explosionEffect;

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
    }

    /**
     * I seguenti metodi servono per attivare la riproduzione dei file audio in modo ripetuto (metodo loop)
     * o singolo (metodo play)
     */
    public void game(){ gameMusic.loop(1.0f,50.0f); }

    public void gameOver(){ gameOverMusic.loop(1.0f,50.0f); }

    public void menu(){ menuMusic.loop(1.0f,50.0f); }

    public void explosion(){ explosionEffect.play(1.0f, 50.0f); }

    public void shot(){ shotEffect.play(1.0f, 50.0f); }
}
