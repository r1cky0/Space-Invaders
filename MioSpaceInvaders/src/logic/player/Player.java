package logic.player;

import logic.environment.manager.file.AddHighScore;
import logic.sprite.dinamic.SpaceShip;

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
     * Se il player batte il proprio highscore, questo viene aggiornato nel file
     */
    public boolean checkHighscore(){
        if(highScore < spaceShip.getCurrentScore()){
            highScore = getSpaceShip().getCurrentScore();
            AddHighScore.saveHighscore(name, highScore);
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

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

}
