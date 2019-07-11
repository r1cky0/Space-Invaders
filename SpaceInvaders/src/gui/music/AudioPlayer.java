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

    private ReadXmlFile readXmlFile;

    public AudioPlayer() {

        this.readXmlFile = new ReadXmlFile();
        try {
            this.gameMusic = new Music(readXmlFile.read("gameMusic"));
            this.gameOverMusic = new Music(readXmlFile.read("gameOverMusic"));
            this.menuMusic = new Music(readXmlFile.read("menuMusic"));
            this.shotEffect = new Sound(readXmlFile.read("shotEffect"));
            this.explosionEffect = new Sound(readXmlFile.read("explosionEffect"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void game(){ gameMusic.loop(1.0f,50.0f); }

    public void gameOver(){ gameOverMusic.loop(1.0f,50.0f); }

    public void menu(){ menuMusic.loop(1.0f,50.0f); }

    public void explosion(){ explosionEffect.play(1.0f, 50.0f); }

    public void shot(){ shotEffect.play(1.0f, 50.0f); }
}
