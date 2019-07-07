package logic.player;

import logic.environment.manager.file.FileModifier;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class Player {
    private String name;
    private int highScore;
    private SpaceShip spaceShip;
    private String shipPath;

    public Player(String name, SpaceShip spaceShip,String shipPath) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.highScore = 0;
        this.shipPath = shipPath;
    }

    /**
     * Check di eventuale nuovo highscore.
     * Se il player batte il proprio highscore, questo viene aggiornato nel file
     */
    public boolean checkHighscore(){
        if(highScore < spaceShip.getCurrentScore()){
            highScore = getSpaceShip().getCurrentScore();
            FileModifier.modifyFile(name, highScore, shipPath);
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

    public String getShipPath(){return shipPath;}

    public SpaceShipBullet getSpaceShipBullet(){
        return spaceShip.getShipBullet();
    }

}
