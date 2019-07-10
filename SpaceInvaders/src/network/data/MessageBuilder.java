package network.data;

import logic.environment.manager.game.Multiplayer;
import logic.environment.manager.game.States;
import logic.player.Player;
import logic.player.Team;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBuilder {

    private String[] stringBuilders;

    public MessageBuilder(){
        stringBuilders = new String[5];
        for(int i=0;i<5;i++){
            stringBuilders[i] = "";
        }
    }

    public void setGameStateInfos(States gameState){
        String gameStateInfos = gameState.toString() + "\n";
        stringBuilders[0] = gameStateInfos;
    }

    public void setInfos(Multiplayer multiplayer){
        setInvaderInfos(multiplayer.getFieldManager().getInvaders());
        setInvaderBulletInfos(multiplayer.getFieldManager().getInvaderBullets());
        setBunkerInfos(multiplayer.getFieldManager().getBunkers());
        setShipInfos(multiplayer.getPlayers(), multiplayer.getTeam());
    }

    private void setInvaderInfos(List<Invader> invaders){
        String invaderInfos = "";
        for(Invader invader : invaders) {
            invaderInfos += invader.getX() + "_" + invader.getY() + "\t";
        }
        invaderInfos += "\n";
        stringBuilders[1] = invaderInfos;
    }

    private void setInvaderBulletInfos(List<InvaderBullet> invaderBullets){
        String invaderBulletInfos = "";
        for(InvaderBullet invaderBullet : invaderBullets){
            invaderBulletInfos += invaderBullet.getX() + "_" + invaderBullet.getY() + "\t";
        }
        invaderBulletInfos += "\n";
        stringBuilders[2] = invaderBulletInfos;
    }

    private void setBunkerInfos(List<Bunker> bunkers){
        String bunkerInfos = "";
        for(Bunker bunker : bunkers) {
            for (Brick brick : bunker.getBricks()) {
                bunkerInfos += brick.getX() + "_" + brick.getY() + "_" + brick.getLife() + "\t";
            }
        }
        bunkerInfos += "\n";
        stringBuilders[3] = bunkerInfos;
    }

    private void setShipInfos(ConcurrentHashMap<Integer, Player> players, Team team){
        String shipInfos = "";
        for(Integer ID : players.keySet()){
            shipInfos += ID + "_" + getSpaceShip(players, ID).getX() + "_" + getSpaceShip(players, ID).getLife() + "_";

            if(getSpaceShip(players, ID).isShipShot()) {
                shipInfos += getSpaceShipBullet(players, ID).getX() + "_" + getSpaceShipBullet(players, ID).getY()+ "\t";
            }
            else {
                shipInfos += (" " + "_" + " " + "\t");
            }
        }
        shipInfos += "\n";
        shipInfos += team.getTeamCurrentScore();
        stringBuilders[4] = shipInfos;
    }

    private SpaceShip getSpaceShip(ConcurrentHashMap<Integer, Player> players, int ID){
        return players.get(ID).getSpaceShip();
    }

    private SpaceShipBullet getSpaceShipBullet(ConcurrentHashMap<Integer, Player> players, int ID){
        return getSpaceShip(players, ID).getShipBullet();
    }

    public String getInfos(){
        String infos = "";
        for(int i=0;i<5;i++){
            infos += stringBuilders[i];
        }
        return infos;
    }

}
