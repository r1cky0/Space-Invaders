package gui.states.multi;

import logic.manager.game.States;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.dinamic.bullets.SpaceShipBullet;
import logic.sprite.dinamic.invaders.BonusInvader;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;

import java.util.ArrayList;

public class LocalMultiRender {

    private LocalMultiMessageHandler localMultiMessageHandler;
    private int score;

    public LocalMultiRender(){
        localMultiMessageHandler = new LocalMultiMessageHandler();
        score = 0;
    }

    public void draw(String[] rcvdata){
        gameState = States.valueOf(rcvdata[0]);
        invaderDrawer(rcvdata[1]);
        bonusInvaderDrawer(rcvdata[2]);
        invaderBulletDrawer(rcvdata[3]);
        bunkerDrawer(rcvdata[4]);
        shipDrawer(rcvdata[5]);
        shipBulletDrawer(rcvdata[6]);
        score = Integer.parseInt(rcvdata[7]);
    }

    private void invaderDrawer(String data) {
        ArrayList<Invader> invaders = new ArrayList<>();
        for (String strings : data.split("\\t")) {
            invaders.add(new Invader());
        }
    }

    private void bonusInvaderDrawer(String data){
        if(!data.equals("")){
            spriteDrawer.render(new BonusInvader(converter(data)));
        }
    }

    private void invaderBulletDrawer(String data) {
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                spriteDrawer.render(new InvaderBullet(converter(strings)));
            }
        }
    }

    private void bunkerDrawer(String data) {
        for (String strings : data.split("\\t")) {
            Brick brick = new Brick(converter(strings));
            brick.setLife(Integer.parseInt(strings.split("_")[2]));
            spriteDrawer.render(brick);
        }
    }

    private void shipDrawer(String data) {
        for (String strings : data.split("\\t")) {
            if (!strings.equals("")) {
                if (client.getID() == Integer.parseInt(strings.split("_")[0])) {
                    spriteDrawer.render(shipManager.getSpaceShip());
                    shipManager.getSpaceShip().setLife(Integer.parseInt(strings.split("_")[3]));
                } else {
                    spriteDrawer.render(new SpaceShip(converter(strings)));
                }
            }
        }
    }

    private void shipBulletDrawer(String data){
        for (String strings : data.split("\\t")) {
            if(!strings.equals("")) {
                spriteDrawer.render(new SpaceShipBullet(converter(strings)));
            }
        }
    }

    public int getScore(){
        return score;
    }

    public States getState(){return localMultiMessageHandler.getGameState();}
}
