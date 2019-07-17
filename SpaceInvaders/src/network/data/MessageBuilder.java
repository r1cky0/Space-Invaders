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
 * - SHIP (posX_posY_life_ID)
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

    /**
     * Inizializzazione string builder
     */
    public MessageBuilder(){
        stringBuilders = new String[8];
        for(int i=0;i<8;i++){
            stringBuilders[i] = " \n";
        }
    }

    /**
     * Metodo richiamato per la creazione completa del messaggio.
     *
     * @param multiplayer oggetto da cui prendere le informazioni
     */
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
     * Metodo che inserisce stato di gioco.
     *
     */
    private void setGameStateInfo(){
        stringBuilders[0] = multiplayer.getGameState().toString() + "\n";
    }

    /**
     * Metodo che inserisce info invaders.
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

    /**
     * Metodo che inserisce info bonus invader.
     */
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
     * Metodo che inserisce info invader bullet.
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
     * Metodo che inserisce info bunkers e relativi bricks.
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
     * Metodo che inserisce info sulle ship.
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

    /**
     * Metodo che inserisce info ship bullets.
     */
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

    /**
     * Metodo che inserisce info team score.
     */
    private void setTeamCurrentScore(){
        stringBuilders[7] = Integer.toString(multiplayer.getTeam().getTeamCurrentScore());
    }

    /**
     * Costruzione del messaggio completo da inviare.
     *
     * @return stringa con le informazioni
     */
    public String getInfo(){
        String infos = "";
        for(int i=0;i<8;i++){
            infos += stringBuilders[i];
        }
        return infos;
    }

    private SpaceShip getSpaceShip(int ID){
        return multiplayer.getPlayers().get(ID).getSpaceShip();
    }

    private Bullet getSpaceShipBullet(int ID){
        return getSpaceShip(ID).getShipBullet();
    }

    private FieldManager getFieldManager(Multiplayer multiplayer){
        return multiplayer.getFieldManager();
    }

}
