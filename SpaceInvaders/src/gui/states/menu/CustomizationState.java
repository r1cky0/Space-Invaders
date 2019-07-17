package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import gui.states.buttons.Button;
import logic.manager.menu.Customization;
import logic.manager.menu.Menu;
import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


import java.util.ArrayList;

public class CustomizationState extends BasicState implements ComponentListener {
    private Customization customization;
    private Shape cornice;
    private ArrayList<Button> buttons;
    private ArrayList<Image> ships;

    public CustomizationState(Menu menu) {
        buttons = new ArrayList<>();
        ships = new ArrayList<>();
        customization = menu.getCustomization();
        try {
            for (String ship : customization.getSpaceShips()) {
                ships.add(new Image(getReaderXmlFile().read(ship)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer,stateBasedGame);
        int offset = 17*gameContainer.getWidth()/100;
        int i = 0;
        for (Image img: ships) {
            img = img.getScaledCopy(10*gameContainer.getWidth()/100,10*gameContainer.getWidth()/100);
            Coordinate posImage = new Coordinate(5*gameContainer.getScreenWidth()/100 + offset*i,45*gameContainer.getHeight()/100);
            buttons.add(new Button(gameContainer, img, posImage, i, this));
            i++;
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){
        int currentShip = customization.indexOfCurrentShip();
        float corniceSide = 14*gameContainer.getWidth()/100f;
        float posX = buttons.get(currentShip).getPosition().getX() - (corniceSide - buttons.get(currentShip).getImage().getWidth())/2f;
        float posY = buttons.get(currentShip).getPosition().getY() - (corniceSide - buttons.get(currentShip).getImage().getHeight())/2f;
        cornice = new Rectangle(posX, posY, corniceSide, corniceSide);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        getHomeButton().render(graphics);
        String title = "SET YOUR SHIP!";
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title, Color.white);
        for (Button button: buttons) {
            button.render(graphics);
        }
        graphics.draw(cornice);
    }
    
    @Override
    public void componentActivated(AbstractComponent source) {
        super.componentActivated(source);
        for(Button button : buttons){
            if(source == button.getMouseOverArea()){
                customization.setCurrentShip(button.getIdState());
                cornice.setX(button.getPosition().getX() - (cornice.getWidth() - button.getImage().getWidth())/2f);
            }
        }
    }

    @Override
    public int getID() {
        return IDStates.CUSTOMIZATION_STATE;
    }
}
