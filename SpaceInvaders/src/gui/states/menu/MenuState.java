package gui.states.menu;

import gui.states.BasicState;
import gui.states.IDStates;
import gui.states.Timer.CountdownState;
import gui.states.buttons.Button;
import logic.manager.menu.Menu;
import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import java.util.ArrayList;

/**
 * Stato che rappresenta il menu di gioco.
 */
public class MenuState extends BasicState implements ComponentListener {
    private ArrayList<Button> buttons;
    private Menu menu;

    public MenuState(Menu menu){
        this.menu = menu;
        buttons = new ArrayList<>();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
        createButton();
        for(Button button : buttons){
            button.addGameContainer(gameContainer);
            button.addListener(this);
        }
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        menu.saveToFile();
        getAudioplayer().menu();
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        for(Button button : buttons){
            button.render(graphics);
        }
        String title = "SPACE INVADERS";
        getTitleFont().drawString((gameContainer.getWidth() - getTitleFont().getWidth(title))/2f,7*gameContainer.getHeight()/100f, title, Color.white);
    }

    /**
     * Funzione che si attiva quando si clicca su un bottone
     * Entra nello stato di gioco del corrispettivo al bottono premuto.
     * @param source bottone premuto
     */
    @Override
    public void componentActivated(AbstractComponent source) {
        for(Button button : buttons){
            if(source == button.getMouseOverArea()){
                getStateBasedGame().enterState(button.getIdState(), new FadeOutTransition(), new FadeInTransition());
            }
        }
    }

    /**
     * Funzione che crea i bottoni dello stato.
     */
    private void createButton() throws SlickException{
        Image single = new Image(getReaderXmlFile().read("buttonSinglePlayer")).getScaledCopy(30*getGameContainer().getWidth()/100, 10*getGameContainer().getHeight()/100);
        Coordinate posSingle = new Coordinate((getGameContainer().getWidth() - single.getWidth())/2,26*getGameContainer().getHeight()/100);
        buttons.add(new Button(single, posSingle, IDStates.SINGLEPLAYER_STATE));

        Image multi = new Image(getReaderXmlFile().read("buttonMultiplayer")).getScaledCopy(30*getGameContainer().getWidth()/100,10*getGameContainer().getHeight()/100);
        Coordinate posMulti = new Coordinate((getGameContainer().getWidth() - multi.getWidth())/2,45*getGameContainer().getHeight()/100);
        buttons.add(new Button(multi, posMulti, IDStates.WAITING_STATE));

        Image custom = new Image(getReaderXmlFile().read("buttonSettings")).getScaledCopy(8*getGameContainer().getWidth()/100,10*getGameContainer().getHeight()/100);
        Coordinate posCustom = new Coordinate(35*getGameContainer().getWidth()/100,63*getGameContainer().getHeight()/100);
        buttons.add(new Button(custom, posCustom, IDStates.CUSTOMIZATION_STATE));

        Image ranking = new Image(getReaderXmlFile().read("buttonRanking")).getScaledCopy(12*getGameContainer().getWidth()/100,12*getGameContainer().getHeight()/100);
        Coordinate posRanking = new Coordinate(55*getGameContainer().getWidth()/100,63*getGameContainer().getHeight()/100);
        buttons.add(new Button(ranking, posRanking, IDStates.RANKING_STATE));

        Image help = new Image(getReaderXmlFile().read("buttonHelp")).getScaledCopy(12*getGameContainer().getWidth()/100,12*getGameContainer().getHeight()/100);
        Coordinate posHelp = new Coordinate(85*getGameContainer().getWidth()/100,4*getGameContainer().getHeight()/100);
        buttons.add(new Button(help, posHelp, IDStates.TUTORIAL_STATE));

        Image exit = new Image(getReaderXmlFile().read("buttonExit")).getScaledCopy(15*getGameContainer().getWidth()/100,10*getGameContainer().getHeight()/100);
        Coordinate posExit = new Coordinate((getGameContainer().getWidth() - exit.getWidth())/2,80*getGameContainer().getHeight()/100);
        buttons.add(new Button(exit, posExit, IDStates.START_STATE));
    }

    @Override
    public int getID() {
        return IDStates.MENU_STATE;
    }
}
