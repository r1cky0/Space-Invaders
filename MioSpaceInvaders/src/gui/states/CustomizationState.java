package gui.states;

import logic.environment.manager.file.ReadXmlFile;
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

public class CustomizationState extends BasicInvaderState implements ComponentListener {
    private StateBasedGame stateBasedGame;

    private Image background;
    private Image homeImage;
    private MouseOverArea homeButton;
    private Shape cornice;

    private String title;
    private UnicodeFont uniFontTitle;

    private ArrayList<Image> ships;
    private ArrayList<MouseOverArea> shipButtons;

    private Menu menu;

    public CustomizationState(Menu menu) {
        this.menu = menu;
        shipButtons = new ArrayList<>();
        ships = new ArrayList<>();
        try {
            for (String ship : menu.getCustomization().getSpaceShips()) {
                ships.add(new Image(ReadXmlFile.read(ship)));
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;

        background = new Image(ReadXmlFile.read("defaultBackground"));
        homeImage = new Image(ReadXmlFile.read("buttonHome")).getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
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
                    6*gameContainer.getHeight()/100,this ));
            i++;
        }

        uniFontTitle = build(8*gameContainer.getWidth()/100f);

        cornice = new Rectangle(shipButtons.get(menu.getCustomization().indexOfCurrentShip()).getX()
                - 42*gameContainer.getWidth()/1000f,40*gameContainer.getHeight()/100f,
                14*gameContainer.getWidth()/100f,12*gameContainer.getWidth()/100f);
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
            menu.getCustomization().setCurrentShip(0);
            cornice.setX(shipButtons.get(0).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());
        }
        if (source == shipButtons.get(1)) {
            menu.getCustomization().setCurrentShip(1);
            cornice.setX(shipButtons.get(1).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());
        }
        if (source == shipButtons.get(2)) {
            menu.getCustomization().setCurrentShip(2);
            cornice.setX(shipButtons.get(2).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());
        }
        if (source == shipButtons.get(3)) {
            menu.getCustomization().setCurrentShip(3);
            cornice.setX(shipButtons.get(3).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());
        }
        if (source == shipButtons.get(4)) {
            menu.getCustomization().setCurrentShip(4);
            cornice.setX(shipButtons.get(4).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());
        }
    }

    @Override
    public int getID() {
        return IDStates.CUSTOMIZATION_STATE;
    }
}
