package network.data;

import logic.manager.field.FieldManager;
import network.server.game.manager.Multiplayer;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

/**
 * Composizione messaggio da inviare a client per renderizzazione:
 * - STATO GIOCO
 * - INVADER (posX_posY)
 * - BONUSINVADER (posX_posY)
 * - INVADER BULLET (posX_posY)
 * - BRICK (posX_posY_life)
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
    private Multiplayer multiplayer;
    private String[] stringBuilders;

    public MessageBuilder(){
        stringBuilders = new String[8];
        for(int i=0;i<8;i++){
            stringBuilders[i] = " \n";
        }
    }

    /**
     * Primo componente del messaggio da inviare ai client é lo stato di gioco
     *
     */
    private void setGameStateInfo(){
        stringBuilders[0] = multiplayer.getGameState().toString() + "\n";
    }

    public void setInfo(Multiplayer multiplayer){
        this.multiplayer = multiplayer;
        setGameStateInfo();
        setInvaderInfo();
        setInvaderBonusInfo();
        setInvaderBulletInfo();
        setBunkerInfo();
        setShipInfo();
        setShipBulletInfo();
        setTeamCurrentScore();
    }

    /**
     * Otteniamo tutte le info sulla posizione degli invaders (classici e bonus)
     *
     */
    private void setInvaderInfo(){
        String invaderInfo = "";
        for(Invader invader : getFieldManager(multiplayer).getInvaders()) {
            invaderInfo += invader.getX() + "_" + invader.getY() + "\t";
        }
        invaderInfo += "\n";
        stringBuilders[1] = invaderInfo;
    }

    private void setInvaderBonusInfo(){
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
     */
    private void setInvaderBulletInfo(){
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
     **/
    private void setBunkerInfo(){
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
     */
    private void setShipInfo(){
        String shipInfo = "";
        for(Integer ID : multiplayer.getPlayers().keySet()){
            shipInfo += getSpaceShip(ID).getX() + "_" + getSpaceShip(ID).getY() +
                    "_" + getSpaceShip(ID).getLife() + "_" + ID + "\t";
        }
        shipInfo += "\n";
        stringBuilders[5] = shipInfo;
    }

    private void setShipBulletInfo(){
        String shipBulletInfo = "";
        for(Integer ID : multiplayer.getPlayers().keySet()) {
            if (getSpaceShip(ID).isShipShot()) {
                shipBulletInfo += getSpaceShipBullet(ID).getX() + "_" +
                        getSpaceShipBullet(ID).getY() + "\t";
            }
        }
        shipBulletInfo += "\n";
        stringBuilders[6] = shipBulletInfo;
    }

    private void setTeamCurrentScore(){
        stringBuilders[7] = Integer.toString(multiplayer.getTeam().getTeamCurrentScore());
    }

    private SpaceShip getSpaceShip(int ID){
        return multiplayer.getPlayers().get(ID).getSpaceShip();
    }

    private Bullet getSpaceShipBullet(int ID){
        return getSpaceShip(ID).getShipBullet();
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
        return infos;
    }

    private FieldManager getFieldManager(Multiplayer multiplayer){
        return multiplayer.getFieldManager();
    }

}
