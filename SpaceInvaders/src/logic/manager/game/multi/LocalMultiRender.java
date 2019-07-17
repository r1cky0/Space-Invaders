package logic.manager.game.multi;

import gui.drawer.SpriteDrawer;
import logic.manager.game.States;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;
import network.client.Client;

public class LocalMultiRender {
    private LocalMultiMessageHandler localMultiMessageHandler;
    private SpriteDrawer spriteDrawer;
    private int score;
    private States gameState;
    private Client client;
    private ShipManager shipManager;

    LocalMultiRender(Client client, ShipManager shipManager){
        localMultiMessageHandler = new LocalMultiMessageHandler();
        spriteDrawer = new SpriteDrawer();
        this.client = client;
        this.shipManager = shipManager;
    }

    public void draw(String[] rcvdata){
        setGameState(rcvdata[0]);
        invaderDrawer(rcvdata[1]);
        bonusInvaderDrawer(rcvdata[2]);
        invaderBulletDrawer(rcvdata[3]);
        bunkerDrawer(rcvdata[4]);
        shipDrawer(rcvdata[5]);
        shipBulletDrawer(rcvdata[6]);
        setScore(rcvdata[7]);
    }

    private void invaderDrawer(String data) {
        for (Invader invader : localMultiMessageHandler.invaderCreator(data)) {
            spriteDrawer.render(invader);
        }
    }

    private void bonusInvaderDrawer(String data) {
        BonusInvader bonusInvader = localMultiMessageHandler.bonusInvaderCreator(data);
        if (bonusInvader != null) {
            spriteDrawer.render(bonusInvader);
        }
    }

    private void invaderBulletDrawer(String data) {
        for (InvaderBullet invaderBullet : localMultiMessageHandler.invaderBulletCreator(data)) {
            spriteDrawer.render(invaderBullet);
        }
    }

    private void bunkerDrawer(String data) {
        for (Brick brick : localMultiMessageHandler.bunkerCreator(data)) {
            spriteDrawer.render(brick);
        }
    }

    private void shipDrawer(String data) {
        for (SpaceShip spaceShip : localMultiMessageHandler.shipCreator(data, client.getID(), shipManager)) {
            spriteDrawer.render(spaceShip);
        }
    }

    private void shipBulletDrawer(String data) {
        for (SpaceShipBullet spaceShipBullet : localMultiMessageHandler.shipBulletCreator(data)) {
            spriteDrawer.render(spaceShipBullet);
        }
    }

    private void setScore(String data){
        score = localMultiMessageHandler.getScore(data);
    }

    private void setGameState(String data){
        gameState = localMultiMessageHandler.getGameState(data);
    }

    public States getGameState(){return gameState;}

    int getScore(){return score;}
}
