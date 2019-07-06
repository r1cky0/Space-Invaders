package gui.music;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class AudioPlayer {
    private Music gameMusic;
    private Music gameOverMusic;
    private Music menuMusic;
    private Music shotEffect;
    private Music explosionEffect;

    public AudioPlayer() {
        try {
            this.gameMusic = new Music("res/Sounds/gameMusic.ogg");
            this.gameOverMusic = new Music("res/Sounds/gameOverMusic.ogg");
            this.menuMusic = new Music("res/Sounds/menuMusic.ogg");
            this.shotEffect = new Music("res/Sounds/shotEffect.ogg");
            this.explosionEffect = new Music("res/Sounds/explosionEffect.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void gameMusic(){
        gameMusic.play(1.0f,1.0f);
    }

    public void gameOverMusic(){
        gameOverMusic.play(1.0f,1.0f);
    }

    public void menuMusic(){
        menuMusic.loop(1.0f,1.0f);
    }

    public void explosionMusic(){
        explosionEffect.loop(1.0f, 1.0f);
    }
}
