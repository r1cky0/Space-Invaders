package gui.states;

import logic.environment.manager.file_xml.ReadXmlFile;
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

public class SettingsState extends BasicInvaderState implements ComponentListener {

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private String title;

    private UnicodeFont uniFontTitle;
    private UnicodeFont uniFontData;

    private ArrayList<Image> ships;
    private ArrayList<MouseOverArea> shipButtons;

    private Shape cornice;

    private Image background;
    private Image homeImage;
    private MouseOverArea homeButton;

    private Menu menu;

    public SettingsState(Menu menu) {
        this.menu = menu;
    }


    @Override
    public int getID() {
        return 7;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {

        shipButtons = new ArrayList<>();
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        background = new Image(ReadXmlFile.readXmlFile(0, "background"));
        homeImage = new Image("res/images/Home.png").getScaledCopy(6*gameContainer.getWidth()/100,
                6*gameContainer.getWidth()/100);
        homeButton = new MouseOverArea(gameContainer, homeImage,5*gameContainer.getWidth()/100,7*gameContainer.getHeight()/100,
                6*gameContainer.getWidth()/100,6*gameContainer.getHeight()/100,this);
        title = "SET YOUR SHIP!";

        ships = new ArrayList<>();
        for (String s: menu.getCustomization().getSpaceShips()) {
            ships.add(new Image(s).getScaledCopy(10*gameContainer.getWidth()/100, 10*gameContainer.getWidth()/100));
        }

        int offset = 17*gameContainer.getWidth()/100;
        int i = 0;

        for (Image img: ships) {
            shipButtons.add(new MouseOverArea(gameContainer, img, 5*gameContainer.getScreenWidth()/100 + offset*i, 45*gameContainer.getHeight()/100,
                    6*gameContainer.getWidth()/100, 6*gameContainer.getHeight()/100, this ));
            i++;
        }

        uniFontTitle = Build(8*gameContainer.getWidth()/100f);

        cornice = new Rectangle(shipButtons.get(menu.getCustomization().getSpaceShips().indexOf(menu.getCustomization().getCurrentShip())).getX()
                - 42*gameContainer.getWidth()/1000f, 40*gameContainer.getHeight()/100f,
                14*gameContainer.getWidth()/100f, 12*gameContainer.getWidth()/100f);

    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background, 0, 0);

        homeButton.render(gameContainer,graphics);

        uniFontTitle.drawString((gameContainer.getWidth() - uniFontTitle.getWidth(title))/2f,
                7*gameContainer.getHeight()/100f, title, org.newdawn.slick.Color.white);
        
        for (MouseOverArea but: shipButtons) {
            but.render(gameContainer, graphics);
        }

        graphics.draw(cornice);

    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton) {
            stateBasedGame.enterState(1, new FadeOutTransition(), new FadeInTransition());
        }

        if (source == shipButtons.get(0)) {
            menu.getCustomization().setCurrentShip(menu.getCustomization().getSpaceShips().get(0));
            cornice.setX(shipButtons.get(0).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());  // salva la current ship nel file
        }
        if (source == shipButtons.get(1)) {
            menu.getCustomization().setCurrentShip(menu.getCustomization().getSpaceShips().get(1));
            cornice.setX(shipButtons.get(1).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());  // salva la current ship nel file
        }
        if (source == shipButtons.get(2)) {
            menu.getCustomization().setCurrentShip(menu.getCustomization().getSpaceShips().get(2));
            cornice.setX(shipButtons.get(2).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());  // salva la current ship nel file
        }
        if (source == shipButtons.get(3)) {
            menu.getCustomization().setCurrentShip(menu.getCustomization().getSpaceShips().get(3));
            cornice.setX(shipButtons.get(3).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());  // salva la current ship nel file
        }
        if (source == shipButtons.get(4)) {
            menu.getCustomization().setCurrentShip(menu.getCustomization().getSpaceShips().get(4));
            cornice.setX(shipButtons.get(4).getX() - 30*cornice.getWidth()/100);
            menu.saveCustomization(menu.getPlayer().getName(), menu.getCustomization().getCurrentShip());  // salva la current ship nel file
        }
    }
}
