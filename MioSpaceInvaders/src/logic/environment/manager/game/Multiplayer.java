package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import network.server.Commands;
import network.server.GameStates;

public class Multiplayer {
    //DIMENSION
    private final double maxWidth = 1000;
    private final double maxHeight = 800;
    private final int delta = 30;

    private FieldManager fieldManager;
    private Team team;

    private ThreadInvader threadInvader;
    public boolean newThread;

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

    private void loop() {
        Thread thread = new Thread(() -> {
            for (Bullet bullet : fieldManager.getInvaderBullets()) {
                bullet.move(delta);
            }
            for (Player player : team.getPlayers()) {
                if (player.getSpaceShip().getShipBullet() != null) {
                    player.getSpaceShip().getShipBullet().move(delta);
                    fieldManager.checkSpaceShipShotCollision(player.getSpaceShip());
                }
                fieldManager.checkInvaderShotCollision(player.getSpaceShip());
            }
            threadManager();
            checkGameState();
            try {
                Thread.sleep(delta);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private GameStates checkGameState(){
        if (fieldManager.isGameOver()) {
            threadInvader.stop();

            if (team.checkHighscore()) {
                return GameStates.NEWHIGHSCORE;
            }
            return GameStates.GAMEOVER;
        }
        return null;
    }

    private void threadManager(){

        if (!newThread) {
            threadInvader = new ThreadInvader(fieldManager.getDifficulty(), fieldManager);
            threadInvader.start();
            newThread = true;
        }
        if (fieldManager.isNewLevel()) {
            threadInvader.stop();
            fieldManager.setNewLevel(false);
            newThread = false;
        }
    }

    public void startGame(){
        fieldManager = new FieldManager(maxWidth, maxHeight);
        loop();
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
}
