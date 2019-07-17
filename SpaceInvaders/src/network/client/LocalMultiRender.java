package network.client;

import gui.drawer.SpriteDrawer;
import logic.manager.game.States;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;

public class LocalMultiRender {
    private LocalMultiMessageHandler localMultiMessageHandler;
    private SpriteDrawer spriteDrawer;
    private int score;
    private States gameState;
    private ShipManager shipManager;

    public LocalMultiRender(ShipManager shipManager){
        this.shipManager = shipManager;
        localMultiMessageHandler = new LocalMultiMessageHandler();
        spriteDrawer = new SpriteDrawer();
    }

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

    private void invaderDrawer(String data) {
        if(!data.equals(" ")) {
            for (Invader invader : localMultiMessageHandler.invaderCreator(data)) {
                spriteDrawer.render(invader);
            }
        }
    }

    private void bonusInvaderDrawer(String data) {
        if(!data.equals(" ")) {
            BonusInvader bonusInvader = localMultiMessageHandler.bonusInvaderCreator(data);
            if (bonusInvader != null) {
                spriteDrawer.render(bonusInvader);
            }
        }
    }

    private void invaderBulletDrawer(String data) {
        if(!data.equals(" ")) {
            for (InvaderBullet invaderBullet : localMultiMessageHandler.invaderBulletCreator(data)) {
                spriteDrawer.render(invaderBullet);
            }
        }
    }

    private void bunkerDrawer(String data) {
        if(!data.equals(" ")) {
            for (Brick brick : localMultiMessageHandler.bunkerCreator(data)) {
                spriteDrawer.render(brick);
            }
        }
    }

    private void shipDrawer(String data, int ID) {
        if(!data.equals(" ")) {
            for (SpaceShip spaceShip : localMultiMessageHandler.shipCreator(data, ID, shipManager)) {
                spriteDrawer.render(spaceShip);
            }
        }
    }

    private void shipBulletDrawer(String data) {
        if(!data.equals(" ")) {
            for (SpaceShipBullet spaceShipBullet : localMultiMessageHandler.shipBulletCreator(data)) {
                spriteDrawer.render(spaceShipBullet);
            }
        }
    }

    private void setScore(String data){
        if(!data.equals(" ")) {
            score = localMultiMessageHandler.getScore(data);
        }
    }

    private void setGameState(String data){
        if(!data.equals(" ")) {
            gameState = localMultiMessageHandler.getGameState(data);
        }
    }

    public States getGameState(){return gameState;}

    public int getScore(){return score;}

    public int getLife(){
        return shipManager.getSpaceShip().getLife();
    }
}
