package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import main.Dimension;
import logic.thread.ThreadUpdate;
import org.lwjgl.Sys;
import org.newdawn.slick.util.pathfinding.navmesh.Space;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Multiplayer{
    private FieldManager fieldManager;
    private Team team;
    private GameStates gameStates;

    private ThreadInvader threadInvader;
    private boolean newThread;
    private ThreadUpdate threadUpdate;

    private static int DELTA = 1;

    public Multiplayer(){
        team = new Team();
        newThread = false;
        gameStates = GameStates.WAITING;
    }

    public Player init(int ID, String[] name){
        Coordinate coordinate = new Coordinate((Dimension.MAX_WIDTH / 2 - Dimension.SHIP_WIDTH / 2),
                (Dimension.MAX_HEIGHT - Dimension.SHIP_HEIGHT));
        SpaceShip defaultShip = new SpaceShip(coordinate, Dimension.SHIP_WIDTH, Dimension.MAX_HEIGHT);
        Player player = new Player(name[0], defaultShip);
        team.addPlayer(ID, player);
        return player;
    }

    private void update() {
        threadUpdate = new ThreadUpdate(this);
        threadUpdate.start();
    }

    public void threadInvaderManager(){
        if (!newThread) {
            threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
            threadInvader.start();
            newThread = true;
        }
        if (fieldManager.isNewLevel()) {
            threadInvader.stop();
            team.incrementLife();
            fieldManager.setNewLevel(false);
            newThread = false;
        }
    }

    public void startGame(){
        fieldManager = new FieldManager();
        newThread = false;
        gameStates = GameStates.START;
        update();
    }

    public void stopGame(){
        threadInvader.stop();
        threadUpdate.stop();
        gameStates = GameStates.WAITING;
        team.clear();
    }

    public void setGameStates(GameStates gameStates){
        this.gameStates = gameStates;
    }

    public String getInfos(){

        String infos = gameStates.toString() + "\n";

        for(Invader invader : fieldManager.getInvaders()){
            infos += invader.getX() + "_" + invader.getY() + "\t";
        }
        infos += "\n";

        for(InvaderBullet invaderBullet : fieldManager.getInvaderBullets()){
            infos += invaderBullet.getX() + "_" + invaderBullet.getY() + "\t";
        }
        infos += "\n";

        for(Bunker bunker : fieldManager.getBunkers()){
            for(Brick brick : bunker.getBricks()){
                infos += brick.getX() + "_" + brick.getY() + "_" + brick.getLife() + "\t";
            }
        }
        infos += "\n";

        for(Integer ID : getPlayers().keySet()){
            infos += ID + "_" + getSpaceShip(ID).getX() + "_" + getSpaceShip(ID).getLife() + "_";
            if(getSpaceShipBullet(ID) != null) {
                infos += getSpaceShipBullet(ID).getX() + "_" + getSpaceShipBullet(ID).getY() + "\t";
            }
            else {
                infos += " " + "_" + " " +"\t";
            }
        }
        infos += "\n";

        infos += team.getTeamCurrentScore();

        return infos;
    }

    public GameStates getGameStates(){
        return gameStates;
    }

    public FieldManager getFieldManager(){
        return fieldManager;
    }

    public Team getTeam(){
        return team;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers(){
        return team.getPlayers();
    }

    public SpaceShip getSpaceShip(int ID){
        return getPlayers().get(ID).getSpaceShip();
    }

    public SpaceShipBullet getSpaceShipBullet(int ID){
        return getSpaceShip(ID).getShipBullet();
    }

    public int getDelta(){
        return DELTA;
    }
}
