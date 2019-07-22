package network.client;

import gui.drawer.SpriteDrawer;
import logic.manager.game.States;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;

/**
 * Classe che gestisce la renderizzazione degli elementi in locale durante il multiplayer.
 * Utilizza il LocalMultiMesssageHandler per la conversione delle informazioni nel messaggio ottenuto e la creazione
 * degli sprite.
 */
public class LocalMultiRender {
    private LocalMultiMessageHandler localMultiMessageHandler;
    private SpriteDrawer spriteDrawer;
    private int score;
    private States gameState;

    public LocalMultiRender(){
        localMultiMessageHandler = new LocalMultiMessageHandler();
        spriteDrawer = new SpriteDrawer();
    }

    /**
     * Metodo che disegna tutti gli elementi di gioco.
     *
     * @param rcvdata dati
     * @param ID id client locale
     */
    public void draw(String[] rcvdata, int ID){
        setGameState(rcvdata[0]);
        invaderDrawer(rcvdata[1]);
        bonusInvaderDrawer(rcvdata[2]);
        invaderBulletDrawer(rcvdata[3]);
        bunkerDrawer(rcvdata[4]);
        shipDrawer(rcvdata[5], ID);
        shipBulletDrawer(rcvdata[6]);
        setScore(rcvdata[7]);
    }

    /**
     * Medoto che disegna gli inveder
     *
     * @param data info invaders
     */
    private void invaderDrawer(String data) {
        for (Invader invader : localMultiMessageHandler.invaderCreator(data)) {
            spriteDrawer.render(invader);
        }
    }

    /**
     * Medoto che disegna il bonus inveder
     *
     * @param data info bonus invaders
     */
    private void bonusInvaderDrawer(String data) {
        BonusInvader bonusInvader = localMultiMessageHandler.bonusInvaderCreator(data);
        if (bonusInvader != null) {
            spriteDrawer.render(bonusInvader);
        }
    }

    /**
     * Medoto che disegna gli inveder bullet
     *
     * @param data info invaders bullet
     */
    private void invaderBulletDrawer(String data) {
        for (InvaderBullet invaderBullet : localMultiMessageHandler.invaderBulletCreator(data)) {
            spriteDrawer.render(invaderBullet);
        }
    }

    /**
     * Medoto che disegna i bunker
     *
     * @param data info bunker
     */
    private void bunkerDrawer(String data) {
        for (Brick brick : localMultiMessageHandler.bunkerCreator(data)) {
            spriteDrawer.render(brick);
        }
    }

    /**
     * Medoto che disegna le ship
     *
     * @param data info ship
     */
    private void shipDrawer(String data, int ID) {
        for (SpaceShip spaceShip : localMultiMessageHandler.shipCreator(data, ID)) {
            spriteDrawer.render(spaceShip);
        }
    }

    /**
     * Medoto che disegna gli ship bullet
     *
     * @param data info ship bullet
     */
    private void shipBulletDrawer(String data) {
        for (SpaceShipBullet spaceShipBullet : localMultiMessageHandler.shipBulletCreator(data)) {
            spriteDrawer.render(spaceShipBullet);
        }
    }

    /**
     * Medoto che setta lo score
     *
     * @param data info score
     */
    private void setScore(String data) {
        score = localMultiMessageHandler.getScore(data);
    }

    /**
     * Medoto che setta stato gioco
     *
     * @param data info stato gioco
     */
    private void setGameState(String data) {
        gameState = localMultiMessageHandler.getGameState(data);
    }

    public States getGameState(){return gameState;}

    public int getScore(){return score;}

    public int getLife(){
        return localMultiMessageHandler.getLife();
    }
}
