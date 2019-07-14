package network.data;

import logic.manager.field.FieldManager;
import logic.manager.game.Multiplayer;
import logic.manager.game.States;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

/**
 * Composizione messaggio da inviare a client per renderizzazione:
 * - STATO GIOCO
 * - INVADER (posX_posY)
 * - INVADER BULLET (posX_posY)
 * - BUNKER (posX_posY_life)
 * - SHIP (posX_posY_life)
 * - SHIP BULLET (posX_posY)
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
        stringBuilders = new String[8];
        for(int i=0;i<8;i++){
            stringBuilders[i] = "";
        }
    }

    /**
     * Primo componente del messaggio da inviare ai client é lo stato di gioco
     *
     * @param gameState stato di gioco
     */
    public void setGameStateInfo(States gameState){
        stringBuilders[0] = gameState.toString() + "\n";
    }

    public void setInfo(Multiplayer multiplayer){
        setInvaderInfo(multiplayer);
        setInvaderBonusInfo(multiplayer);
        setInvaderBulletInfo(multiplayer);
        setBunkerInfo(multiplayer);
        setShipInfo(multiplayer);
        setShipBulletInfo(multiplayer);
        setTeamCurrentScore(multiplayer);
    }

    /**
     * Otteniamo tutte le info sulla posizione degli invaders (classici e bonus)
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setInvaderInfo(Multiplayer multiplayer){
        String invaderInfo = "";
        for(Invader invader : getFieldManager(multiplayer).getInvaders()) {
            invaderInfo += invader.getX() + "_" + invader.getY() + "\t";
        }
        invaderInfo += "\n";
        stringBuilders[1] = invaderInfo;
    }

    private void setInvaderBonusInfo(Multiplayer multiplayer){
        String invaderBonusInfo = "";
        if(multiplayer.getFieldManager().isBonusInvader()){
            invaderBonusInfo += multiplayer.getFieldManager().getBonusInvader().getX() + "_" +
                    multiplayer.getFieldManager().getBonusInvader().getY();
        }
        invaderBonusInfo += "\n";
        stringBuilders[2] = invaderBonusInfo;
    }

    /**
     * Otteniamo le informazioni sui bullet sparati dagli invaders
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setInvaderBulletInfo(Multiplayer multiplayer){
        String invaderBulletInfo = "";
        for(Bullet invaderBullet : getFieldManager(multiplayer).getInvaderBullets()){
            invaderBulletInfo += invaderBullet.getX() + "_" + invaderBullet.getY() + "\t";
        }
        invaderBulletInfo += "\n";
        stringBuilders[3] = invaderBulletInfo;
    }

    /**
     * Otteniamo le informazioni sui bunker e i rispettivi brick
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setBunkerInfo(Multiplayer multiplayer){
        String bunkerInfo = "";
        for(Bunker bunker : getFieldManager(multiplayer).getBunkers()) {
            for (Brick brick : bunker.getBricks()) {
                bunkerInfo += brick.getX() + "_" + brick.getY() + "_" + brick.getLife() + "\t";
            }
        }
        bunkerInfo += "\n";
        stringBuilders[4] = bunkerInfo;
    }

    /**
     * Otteniamo informazioni riguardo la posizione delle space ship, la loro vita e, eventualmente, il bullet
     * sparato
     *
     * @param multiplayer gestore di gioco da cui ottenere le info
     */
    private void setShipInfo(Multiplayer multiplayer){
        String shipInfo = "";
        for(Integer ID : multiplayer.getPlayers().keySet()){
            shipInfo += ID + "_" + getSpaceShip(multiplayer, ID).getX() + "_" + getSpaceShip(multiplayer, ID).getY() +
                    "_" + getSpaceShip(multiplayer, ID).getLife() + "\t";
        }
        shipInfo += "\n";
        stringBuilders[5] = shipInfo;
    }

    private void setShipBulletInfo(Multiplayer multiplayer){
        String shipBulletInfo = "";
        for(Integer ID : multiplayer.getPlayers().keySet()) {
            if (getSpaceShip(multiplayer, ID).isShipShot()) {
                shipBulletInfo += getSpaceShipBullet(multiplayer, ID).getX() + "_" +
                        getSpaceShipBullet(multiplayer, ID).getY() + "\t";
            }
        }
        shipBulletInfo += "\n";
        stringBuilders[6] = shipBulletInfo;
    }

    private void setTeamCurrentScore(Multiplayer multiplayer){
        stringBuilders[7] = Integer.toString(multiplayer.getTeam().getTeamCurrentScore());
    }

    private SpaceShip getSpaceShip(Multiplayer multiplayer, int ID){
        return multiplayer.getPlayers().get(ID).getSpaceShip();
    }

    private Bullet getSpaceShipBullet(Multiplayer multiplayer, int ID){
        return getSpaceShip(multiplayer, ID).getShipBullet();
    }

    /**
     * Costruzione del messaggio completo da inviare
     * @return messaggio con le info necessarie
     */
    public String getInfo(){
        String infos = "";
        for(int i=0;i<8;i++){
            infos += stringBuilders[i];
        }
        System.out.println(stringBuilders[0]);
        return infos;
    }

    private FieldManager getFieldManager(Multiplayer multiplayer){
        return multiplayer.getFieldManager();
    }

}
