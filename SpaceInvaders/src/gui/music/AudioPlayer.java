package gui.music;

import logic.manager.file.ReadXmlFile;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;

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
     * Riproduzione dei file audio in modo ripetuto (metodo loop) o singolo (metodo play)
     */
    public void game(){
        gameMusic.loop(pitch, volume);
    }

    public void gameOver(){
        gameOverMusic.loop(pitch, volume);
    }

    public void menu(){
        menuMusic.loop(pitch, volume);
    }

    public void explosion(){
        explosionEffect.play(pitch, volume);
    }

    public void shot(){
        shotEffect.play(pitch, volume);
    }
}
