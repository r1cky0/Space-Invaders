package gui.states.buttons;

import logic.sprite.Coordinate;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;

public class Button {
    private Image image;
    private Coordinate position;
    private GameContainer gameContainer;
    private MouseOverArea mouseOverArea;
    private int idState;

    public Button(Image image, Coordinate position, int idState){
        this.image = image;
        this.idState = idState;
        this.position = position;
    }

    public Button(GameContainer gameContainer, Image image, Coordinate position, int idState, ComponentListener listener){
        this.image = image;
        this.idState = idState;
        this.position = position;
        mouseOverArea = new MouseOverArea(gameContainer,image,(int)position.getX(),(int)position.getY(),image.getWidth(),image.getHeight(), listener);
    }

    public void render(Graphics graphics){
        mouseOverArea.render(gameContainer, graphics);
    }

    public void addGameContainer(GameContainer gameContainer){
        this.gameContainer = gameContainer;
        mouseOverArea = new MouseOverArea(gameContainer,image,(int)position.getX(),(int)position.getY(),image.getWidth(),image.getHeight());
    }

    public void addListener(ComponentListener listener){
        mouseOverArea.addListener(listener);
    }

    public int getIdState(){
        return idState;
    }

    public Coordinate getPosition(){
        return position;
    }

    public Image getImage() {
        return image;
    }

    public MouseOverArea getMouseOverArea() {
        return mouseOverArea;
    }
}
