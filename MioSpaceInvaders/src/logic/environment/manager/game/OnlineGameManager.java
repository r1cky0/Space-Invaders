package logic.environment.manager.game;

import logic.player.Player;
import logic.player.Team;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.SpaceShipBullet;

public class OnlineGameManager extends GameManager{

    private Team team;

    public OnlineGameManager(Team team, double maxWidth, double maxHeight){
        super(maxWidth, maxHeight);
        this.team = team;
    }

    /**
     * Check di eventuale nuovo highscore di squadra
     */
    public void gameOver(){

        if(team.getTeamHighScore() < team.getTeamCurrentScore()){
            team.setTeamHighScore(team.getTeamCurrentScore());
            setNewLevel(true);
        }else {
            setGameOver(true);
        }
    }

    public void shipMovement(int id, MovingDirections md, int delta){
        shipMovement(getSpaceShip(id), md, delta);
    }

    public void shipShot(int id){
        shipShot(getSpaceShip(id));
    }

    public void checkInvaderShotCollision(){
        for(Player player : team.getPlayers()) {
            checkInvaderShotCollision(player.getSpaceShip());
        }
    }

    public void checkSpaceShipShotCollision(int id){
        checkSpaceShipShotCollision(getSpaceShip(id));
    }

    public SpaceShip getSpaceShip(int id){
        return team.getPlayers().get(id).getSpaceShip();
    }

    public SpaceShipBullet getSpaceShipBullet(int id){
        return team.getPlayers().get(id).getSpaceShip().getShipBullet();
    }

}
