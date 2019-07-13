package logic.player;

import logic.environment.manager.file.FileModifier;
import logic.environment.manager.menu.Customization;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class Player {
    private String name;
    private int highScore;
    private SpaceShip spaceShip;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.highScore = 0;
    }

    /**
     * Check di eventuale nuovo highscore.
     *
     */
    public boolean checkHighscore(){
        if(highScore < spaceShip.getCurrentScore()){
            highScore = getSpaceShip().getCurrentScore();
            return true;
        }
        return false;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public SpaceShipBullet getSpaceShipBullet(){
        return spaceShip.getShipBullet();
    }

}
