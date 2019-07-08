package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.file.ReadXmlFile;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import main.Dimensions;
import logic.thread.ThreadUpdate;

import java.util.concurrent.ConcurrentHashMap;

public class Multiplayer{
    private FieldManager fieldManager;
    private Team team;
    private States states;

    private ThreadInvader threadInvader;
    private boolean threadRunning;
    private ThreadUpdate threadUpdate;

    private static int DELTA = 1;

    public Multiplayer(){
        team = new Team();
        threadRunning = false;
    }

    public Player init(int ID, String[] name){
        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH / 2 - Dimensions.SHIP_WIDTH / 2),
                (Dimensions.MAX_HEIGHT - Dimensions.SHIP_HEIGHT));
        SpaceShip defaultShip = new SpaceShip(coordinate, Dimensions.SHIP_WIDTH, Dimensions.MAX_HEIGHT);
        Player player = new Player(name[0], defaultShip);
        team.addPlayer(ID, player);
        return player;
    }

    private void update() {
        threadUpdate = new ThreadUpdate(this);
        threadUpdate.start();
    }

    public void threadInvaderManager(){
        if (!threadRunning) {
            threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
            threadInvader.start();
            threadRunning = true;
        }
        if (fieldManager.isNewLevel()) {
            threadInvader.stop();
            team.incrementLife();
            fieldManager.setNewLevel(false);
            threadRunning = false;
        }
    }

    public void startGame(){
        fieldManager = new FieldManager();
        threadRunning = false;
        states = States.START;
        update();
    }

    public void stopGame() {
        if(threadRunning) {
            threadInvader.stop();
            threadUpdate.stop();
        }
        team.clear();
    }

    public void setStates(States states){
        this.states = states;
    }

    public String getInfos(){

        String infos = states.toString() + "\n";

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
            if(getSpaceShip(ID).isShipShot()) {
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
