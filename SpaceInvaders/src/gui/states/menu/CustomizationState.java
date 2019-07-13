package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import logic.environment.manager.menu.Customization;
import logic.environment.manager.menu.Menu;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


import java.util.ArrayList;

public class CustomizationState extends BasicState implements ComponentListener {
    private StateBasedGame stateBasedGame;
    private Menu menu;
    private Customization customization;

    private Image background;
    private MouseOverArea homeButton;
    private Shape cornice;

    private String title;
    private UnicodeFont uniFontTitle;

    private ArrayList<Image> ships;
    private ArrayList<MouseOverArea> shipButtons;

    public CustomizationState(Menu menu) {
        shipButtons = new ArrayList<>();
        ships = new ArrayList<>();
        customization = menu.getCustomization();
        try {
            for (String ship : customization.getSpaceShips()) {
                ships.add(new Image(readerXmlFile.read(ship)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        background = new Image(readerXmlFile.read("defaultBackground"));
        Image homeImage = new Image(readerXmlFile.read("buttonHome")).getScaledCopy(6 * gameContainer.getWidth() / 100,
                6 * gameContainer.getWidth() / 100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,
                7*gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,
                this);
        title = "SET YOUR SHIP!";



        int offset = 17*gameContainer.getWidth()/100;
        int i = 0;
        for (Image img: ships) {
            img = img.getScaledCopy(10*gameContainer.getWidth()/100,10*gameContainer.getWidth()/100);
            shipButtons.add(new MouseOverArea(gameContainer, img,5*gameContainer.getScreenWidth()/100 + offset*i,
                    45*gameContainer.getHeight()/100,6*gameContainer.getWidth()/100,
                    6*gameContainer.getHeight()/100,this));
            i++;
        }

        uniFontTitle = build(8*gameContainer.getWidth()/100f);
        cornice = new Rectangle(shipButtons.get(customization.indexOfCurrentShip()).getX()
                - 42*gameContainer.getWidth()/1000f,40*gameContainer.getHeight()/100f,
                14*gameContainer.getWidth()/100f,12*gameContainer.getWidth()/100f);

    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame){

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);
        homeButton.render(gameContainer, graphics);
        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title, org.newdawn.slick.Color.white);
        
        for (MouseOverArea button: shipButtons) {
            button.render(gameContainer, graphics);
        }
        graphics.draw(cornice);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) {
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        if (source == shipButtons.get(0)) {
            customization.setCurrentShip(0);
            cornice.setX(shipButtons.get(0).getX() - 30*cornice.getWidth()/100);
        }
        if (source == shipButtons.get(1)) {
            customization.setCurrentShip(1);
            cornice.setX(shipButtons.get(1).getX() - 30*cornice.getWidth()/100);
        }
        if (source == shipButtons.get(2)) {
            customization.setCurrentShip(2);
            cornice.setX(shipButtons.get(2).getX() - 30*cornice.getWidth()/100);
        }
        if (source == shipButtons.get(3)) {
            customization.setCurrentShip(3);
            cornice.setX(shipButtons.get(3).getX() - 30*cornice.getWidth()/100);
        }
        if (source == shipButtons.get(4)) {
            customization.setCurrentShip(4);
            cornice.setX(shipButtons.get(4).getX() - 30*cornice.getWidth()/100);
        }
    }

    @Override
    public int getID() {
        return IDStates.CUSTOMIZATION_STATE;
    }
}
