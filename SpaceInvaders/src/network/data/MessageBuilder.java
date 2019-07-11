package network.data;

import logic.environment.manager.field.FieldManager;
import logic.environment.manager.game.Multiplayer;
import logic.environment.manager.game.States;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

public class MessageBuilder{

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
        setInvaderInfos(multiplayer);
        setInvaderBulletInfos(multiplayer);
        setBunkerInfos(multiplayer);
        setShipInfos(multiplayer);
    }

    public void setInvaderInfos(Multiplayer multiplayer){
        String invaderInfos = "";
        for(Invader invader : getFieldManager(multiplayer).getInvaders()) {
            invaderInfos += invader.getX() + "_" + invader.getY() + "\t";
        }
        invaderInfos += "\n";
        stringBuilders[1] = invaderInfos;
    }

    public void setInvaderBulletInfos(Multiplayer multiplayer){
        String invaderBulletInfos = "";
        for(InvaderBullet invaderBullet : getFieldManager(multiplayer).getInvaderBullets()){
            invaderBulletInfos += invaderBullet.getX() + "_" + invaderBullet.getY() + "\t";
        }
        invaderBulletInfos += "\n";
        stringBuilders[2] = invaderBulletInfos;
    }

    private void setBunkerInfos(Multiplayer multiplayer){
        String bunkerInfos = "";
        for(Bunker bunker : getFieldManager(multiplayer).getBunkers()) {
            for (Brick brick : bunker.getBricks()) {
                bunkerInfos += brick.getX() + "_" + brick.getY() + "_" + brick.getLife() + "\t";
            }
        }
        bunkerInfos += "\n";
        stringBuilders[3] = bunkerInfos;
    }

    private void setShipInfos(Multiplayer multiplayer){
        String shipInfos = "";
        for(Integer ID : multiplayer.getPlayers().keySet()){
            shipInfos += ID + "_" + getSpaceShip(multiplayer, ID).getX() + "_" + getSpaceShip(multiplayer, ID).getLife() + "_";

            if(getSpaceShip(multiplayer, ID).isShipShot()) {
                shipInfos += getSpaceShipBullet(multiplayer, ID).getX() + "_" + getSpaceShipBullet(multiplayer, ID).getY()+ "\t";
            }
            else {
                shipInfos += (" " + "_" + " " + "\t");
            }
        }
        shipInfos += "\n";
        shipInfos += multiplayer.getTeam().getTeamCurrentScore();
        stringBuilders[4] = shipInfos;
    }

    private SpaceShip getSpaceShip(Multiplayer multiplayer, int ID){
        return multiplayer.getPlayers().get(ID).getSpaceShip();
    }

    private SpaceShipBullet getSpaceShipBullet(Multiplayer multiplayer, int ID){
        return getSpaceShip(multiplayer, ID).getShipBullet();
    }

    public String getInfos(){
        String infos = "";
        for(int i=0;i<5;i++){
            infos += stringBuilders[i];
        }
        return infos;
    }

    private FieldManager getFieldManager(Multiplayer multiplayer){
        return multiplayer.getFieldManager();
    }

}
