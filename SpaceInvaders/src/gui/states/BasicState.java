package gui.states;

import gui.music.AudioPlayer;
import gui.states.buttons.Button;
import logic.manager.file.ReadXmlFile;
import logic.sprite.Coordinate;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;
import java.awt.Font;


public abstract class BasicState extends BasicGameState implements ComponentListener {
    private GameContainer gameContainer;
    private StateBasedGame stateBasedGame;
    private AudioPlayer audioplayer = new AudioPlayer();
    private ReadXmlFile readerXmlFile = new ReadXmlFile();

    private Image background;
    private Button homeButton;
    private UnicodeFont titleFont;
    private UnicodeFont dataFont;

    public BasicState(){
        try {
            background = new Image(readerXmlFile.read("defaultBackground"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer = gameContainer;
        this.stateBasedGame = stateBasedGame;
        titleFont = build(7*gameContainer.getWidth()/100f);
        dataFont = build(3*gameContainer.getWidth()/100f);
        int side = 6*gameContainer.getWidth()/100;
        Image homeImage = new Image(getReaderXmlFile().read("buttonHome")).getScaledCopy(side, side);
        Coordinate posHome = new Coordinate(5*gameContainer.getWidth()/100,7*gameContainer.getHeight()/100);
        homeButton = new Button(gameContainer, homeImage, posHome, IDStates.MENU_STATE, this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        background.draw(0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();
        if(input.isKeyPressed(Input.KEY_ESCAPE)){
            componentActivated(homeButton.getMouseOverArea());
        }
    }

    /**
     * Metodo che esegue le operazioni di creazione font comuni a tutti gli stati
     *
     * @param size Dimensioni da apportare al font (ricordare di inserirle dinamiche alla finestra)
     */
    private UnicodeFont build(float size) {
        try {
            Font fontTitle = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            fontTitle = fontTitle.deriveFont(Font.BOLD, size);
            UnicodeFont uniFontTitle = new UnicodeFont(fontTitle);
            uniFontTitle.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFontTitle.addAsciiGlyphs();
            uniFontTitle.loadGlyphs();
            return uniFontTitle;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public void componentActivated(AbstractComponent source) {
        if (source == homeButton.getMouseOverArea()) {
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    public Button getHomeButton(){
        return homeButton;
    }

    public GameContainer getGameContainer() {
        return gameContainer;
    }

    public StateBasedGame getStateBasedGame() {
        return stateBasedGame;
    }

    public AudioPlayer getAudioplayer() {
        return audioplayer;
    }

    public ReadXmlFile getReaderXmlFile() {
        return readerXmlFile;
    }

    public UnicodeFont getTitleFont() {
        return titleFont;
    }

    public UnicodeFont getDataFont() {
        return dataFont;
    }
}
