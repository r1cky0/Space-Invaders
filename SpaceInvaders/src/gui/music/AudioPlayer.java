package gui.music;

import logic.environment.manager.file.ReadXmlFile;
import org.newdawn.slick.Music;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;

public class AudioPlayer {
    private Music musicGame;
    private Music musicGameOver;
    private Music musicMenu;
    private Sound effectShot;
    private Sound effectExplosion;

    private ReadXmlFile readXmlFile;

    public AudioPlayer() {

        this.readXmlFile = new ReadXmlFile();
        try {
            this.musicGame = new Music(readXmlFile.read("musicGame"));
            this.musicGameOver = new Music(readXmlFile.read("musicGameOver"));
            this.musicMenu = new Music(readXmlFile.read("musicMenu"));
            this.effectShot = new Sound(readXmlFile.read("effectShot"));
            this.effectExplosion = new Sound(readXmlFile.read("effectExplosion"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void game(){ musicGame.play(1.0f,50.0f); }

    public void gameOver(){ musicGameOver.play(1.0f,50.0f); }

    public void menu(){ musicMenu.loop(1.0f,50.0f); }

    public void explosion(){ effectExplosion.play(1.0f, 50.0f); }

    public void shot(){ effectShot.play(1.0f, 50.0f); }
}
