package network.data;

import logic.manager.field.FieldManager;
import logic.manager.game.Multiplayer;
import logic.manager.game.States;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

/**
 * Composizione messaggio da inviare a client per renderizzazione:
 * - STATO GIOCO
 * - INVADER (posX_posY)
 * - INVADER BULLET (posX_posY)
 * - BUNKER (posX_posY_life)
 * - SHIP E SHIP BULLET (posXShip_life_posXBullet_posYBullet)
 * - TEAM SCORE
 *
 * Ogni informazione dello stesso gruppo è separata da carattere '\t'
 * I gruppi sono separati dal carattere '\n'
 * Fine messaggio indicata dal carattere '\r'
 *
 */

public class MessageBuilder{

    private String[] stringBuilders;

    public MessageBuilder(){
        stringBuilders = new String[5];
        for(int i=0;i<5;i++){
            stringBuilders[i] = "";
        }
    }

    /**
     * Primo componente del messaggio da inviare ai client é lo stato di gioco
     *
     * @param gameState stato di gioco
     */
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

    /**
     * Otteniamo tutte le info sulla posizione degli invaders (classici e bonus)
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setInvaderInfos(Multiplayer multiplayer){
        String invaderInfos = "";
        for(Invader invader : getFieldManager(multiplayer).getInvaders()) {
            invaderInfos += invader.getX() + "_" + invader.getY() + "\t";
        }
        invaderInfos += "\n";
        if(multiplayer.getFieldManager().isBonusInvader()){
            invaderInfos += multiplayer.getFieldManager().getBonusInvader().getX() + "_" +
                    multiplayer.getFieldManager().getBonusInvader().getY();
        }
        invaderInfos += "\n";
        stringBuilders[1] = invaderInfos;
    }

    /**
     * Otteniamo le informazioni sui bullet sparati dagli invaders
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setInvaderBulletInfos(Multiplayer multiplayer){
        String invaderBulletInfos = "";
        for(InvaderBullet invaderBullet : getFieldManager(multiplayer).getInvaderBullets()){
            invaderBulletInfos += invaderBullet.getX() + "_" + invaderBullet.getY() + "\t";
        }
        invaderBulletInfos += "\n";
        stringBuilders[2] = invaderBulletInfos;
    }

    /**
     * Otteniamo le informazioni sui bunker e i rispettivi brick
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
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

    /**
     * Otteniamo informazioni riguardo la posizione delle space ship, la loro vita e, eventualmente, il bullet
     * sparato
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
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

    /**
     * Costruzione del messaggio completo da inviare
     * @return messaggio con le info necessarie
     */
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
