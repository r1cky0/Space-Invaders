package logic.environment.manager.game;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.field.MovingDirections;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import logic.thread.ThreadInvader;
import main.Dimensions;
import logic.thread.ThreadUpdate;

public class Multiplayer {
    private FieldManager fieldManager;
    private Team team;
    private GameStates gameStates;

    private ThreadInvader threadInvader;
    private boolean newThread;
    private ThreadUpdate threadUpdate;

    private static int DELTA = 10;

    public Multiplayer(){
        team = new Team();
        newThread = false;
        gameStates = GameStates.WAITING;
    }

    public void init(int ID, String[] name){
        double shipSize = Dimensions.MAX_WIDTH / 20;
        Coordinate coordinate = new Coordinate((Dimensions.MAX_WIDTH / 2 - shipSize / 2), (Dimensions.MAX_HEIGHT - shipSize));
        SpaceShip defaultShip = new SpaceShip(coordinate, shipSize);
        team.addPlayer(ID, new Player(name[0], defaultShip));
    }

    public int execCommand(String[] infos){
        int ID;
        try {
             ID = Integer.parseInt(infos[0]);
        }catch (NumberFormatException err){
            return -1;
        }
        Player player = team.getPlayers().get(ID);
        switch (Commands.valueOf(infos[1])) {
            case MOVE_LEFT:
                fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.LEFT, DELTA);
                break;
            case MOVE_RIGHT:
                fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.RIGHT, DELTA);
                break;
            case SHOT:
                fieldManager.shipShot(player.getSpaceShip());
                break;
            case EXIT:
                team.removePlayer(ID);
                return ID;
        }
        return -1;
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

        for(Player player : team.getPlayers().values()){
            infos += player.getSpaceShip().getX() + "_" + player.getSpaceShip().getY() + "_" +
                    player.getSpaceShip().getLife() + "\t";
        }
        infos += "\n";

        for(Player player : team.getPlayers().values()){
            if(player.getSpaceShipBullet() != null){
                infos += player.getSpaceShipBullet().getX() + "_" + player.getSpaceShipBullet().getY() + "\t";
            }
        }
        infos += "\n";

        infos += team.getTeamCurrentScore() + "\n";

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

    public int getDelta(){
        return DELTA;
    }
}
