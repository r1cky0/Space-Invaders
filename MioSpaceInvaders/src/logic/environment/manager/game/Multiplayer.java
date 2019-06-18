package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.environment.manager.game.Commands;
import logic.environment.manager.game.GameStates;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import network.server.thread.ThreadUpdate;

public class Multiplayer {

    //DIMENSION
    private final double maxWidth = 1000;
    private final double maxHeight = 800;
    private final int delta = 1;

    private FieldManager fieldManager;
    private Team team;

    private ThreadInvader threadInvader;
    private boolean newThread;

    private ThreadUpdate threadUpdate;

    public Multiplayer(){
        team = new Team();
        newThread = false;
    }

    public void init(String[] name){
        double shipSize = maxWidth / 20;
        Coordinate coordinate = new Coordinate((maxWidth / 2 - shipSize / 2), (maxHeight - shipSize));
        SpaceShip defaultShip = new SpaceShip(coordinate, shipSize);

        team.addPlayer(new Player(name[0], defaultShip));
    }

    public void execCommand(String[] infos){
        try {
            Integer.parseInt(infos[0]);
        }catch (NumberFormatException err){
            return;
        }
        Player player = team.getPlayers().get(Integer.parseInt(infos[0]));
        switch (Commands.valueOf(infos[1])) {
            case MOVE_LEFT:
                fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.LEFT, delta);
                break;
            case MOVE_RIGHT:
                fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.RIGHT, delta);
                break;
            case SHOT:
                fieldManager.shipShot(player.getSpaceShip());
                break;
            default:
                break;
        }
    }

    public int checkPlayers(String[] infos){
        try {
            Integer.parseInt(infos[0]);
        }catch (NumberFormatException err){
            return -1;
        }
        Player player = team.getPlayers().get(Integer.parseInt(infos[0]));
        if(Commands.valueOf(infos[1]) == Commands.EXIT){
            int id = team.getPlayers().indexOf(player);
            team.removePlayer(player);
            return id;
        }
        return -1;
    }

    private void update() {
        threadUpdate = new ThreadUpdate(this);
        threadUpdate.start();
    }

    public GameStates checkGameState(){
        if (fieldManager.isGameOver()) {
            threadInvader.stop();
            threadUpdate.stop();

            if (team.checkHighscore()) {
                return GameStates.NEWHIGHSCORE;
            }
            return GameStates.GAMEOVER;
        }
        return null;
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
        fieldManager = new FieldManager(maxWidth, maxHeight);
        update();
    }

    public String getInfos(){
        String infos = "";

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

        for(Player player : team.getPlayers()){
            infos += player.getSpaceShip().getX() + "_" + player.getSpaceShip().getY() + "_" +
                    player.getSpaceShip().getLife() + "\t";
        }
        infos += "\n";

        for(Player player : team.getPlayers()){
            if(player.getSpaceShipBullet() != null){
                infos += player.getSpaceShipBullet().getX() + "_" + player.getSpaceShipBullet().getY() + "\t";
            }
        }
        infos += "\n";

        infos += team.getTeamCurrentScore() + "\n";

        return infos;
    }

    public FieldManager getFieldManager(){
        return fieldManager;
    }

    public Team getTeam(){
        return team;
    }

    public int getDelta(){
        return delta;
    }
}
