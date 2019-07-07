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
            this.gameMusic = new Music("res/sounds/menu.ogg");
            //this.gameOverMusic = new Music("res/Sounds/gameOverMusic.ogg");
            this.menuMusic = new Music("res/sounds/explosion.ogg");
            //this.shotEffect = new Music("res/sounds/shot.ogg");
            //this.explosionEffect = new Music("res/Sounds/explosionEffect.ogg");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public void game(){
        gameMusic.play(1.0f,1.0f);
    }

    public void gameOver(){
        gameOverMusic.play(1.0f,1.0f);
    }

    public void menu(){
        menuMusic.loop(1.0f,1.0f);
    }

    public void explosion(){
        explosionEffect.play(1.0f, 1.0f);
    }

//    public void shot(){
//        shotEffect.play(1.0f, 1.0f);
//    }
}
