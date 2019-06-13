package network.server;

import logic.environment.manager.field.FieldManager;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;


public class Multiplayer {
    //DIMENSION
    private double maxHeight = 800;
    private double maxWidth = 1000;

    private FieldManager fieldManager;
    private Team team;

    public Multiplayer(){
        team = new Team();
    }

    //RIcordarci gestione dell' attesa di tutti i giocatori
    public void execute(String[] infos){
        Player player = team.getPlayers().get(Integer.parseInt(infos[0]));
        switch (Commands.valueOf(infos[1])){
            case MOVE_LEFT:
                //fieldManager.shipMovement(player.getSpaceShip(), MovingDirections.LEFT);
                break;
            case MOVE_RIGHT:
                //fieldManager.shipMovement(player.getSpaceShip(),MovingDirections.RIGHT);
                break;
            case SHOT:
                fieldManager.shipShot(player.getSpaceShip());
                break;
        }
    }

    public void init(String[] name){
        double shipSize = maxWidth / 20;
        Coordinate coordinate = new Coordinate((maxWidth / 2 - shipSize / 2), (maxHeight - shipSize));
        SpaceShip defaultShip = new SpaceShip(coordinate, shipSize);

        team.addPlayer(new Player(name[0], defaultShip));
    }

    public void startGame(){
        fieldManager = new FieldManager(maxWidth, maxHeight);
    }
}
