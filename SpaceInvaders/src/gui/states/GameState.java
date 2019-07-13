package gui.states;

import gui.elements.*;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class GameState extends BasicState {

    protected GraphicInvader graphicInvader;
    protected GraphicBonusInvader graphicBonusInvader;
    protected GraphicBullet graphicBullet;
    protected GraphicBrick graphicBrick;
    protected GraphicShip graphicShip;

    protected GameState(){
        try {
            graphicInvader = new GraphicInvader(new Image(readerXmlFile.read("defaultInvader")));
            graphicBonusInvader = new GraphicBonusInvader(new Image(readerXmlFile.read("bonusInvader")));
            graphicBullet = new GraphicBullet(new Image(readerXmlFile.read("defaultBullet")));
            graphicBrick = new GraphicBrick();
            for(int i=0; i<4; i++){
                graphicBrick.addImage(new Image(readerXmlFile.read("brick"+i)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}
