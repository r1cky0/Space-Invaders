package gui.states;

import logic.environment.Menu;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.util.ResourceLoader;

import java.awt.Font;

public class AddAccountState extends BasicGameState implements ComponentListener {
    private Menu menu;

    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private TextField newnameField;
    private TextField newpasswordField;

    private String message;
    private String newnameString;
    private String newpasswordString;

    private Font UIFont1;
    private UnicodeFont uniFont;

    private org.newdawn.slick.Image background;

    private org.newdawn.slick.Image newAccount;
    private MouseOverArea newAccountButton;

    private Input input;

    public AddAccountState(Menu menu){
        this.menu = menu;
    }

    @Override
    public int getID() {
        return 6;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.gameContainer= gameContainer;
        this.stateBasedGame= stateBasedGame;
        background = new org.newdawn.slick.Image("res/images/space.png");
        try{
            UIFont1 = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.getResourceAsStream("res/font/invaders_font.ttf"));
            UIFont1 = UIFont1.deriveFont(Font.PLAIN, 40);
            uniFont = new UnicodeFont(UIFont1);
            uniFont.getEffects().add(new ColorEffect(java.awt.Color.white));
            uniFont.addAsciiGlyphs();
            uniFont.loadGlyphs();
        }catch(Exception e){
            e.printStackTrace();
        }

        input = new Input(gameContainer.getHeight());
        input.addKeyListener(this);

        message = "inserisci dati";
        newnameString = "nickname:";
        newpasswordString = "password:";

        newnameField = new TextField(gameContainer,uniFont,260,200,300,40);
        newpasswordField = new TextField(gameContainer,uniFont,260,250,300,40);

        newnameField.setBackgroundColor(org.newdawn.slick.Color.white);
        newnameField.setTextColor(org.newdawn.slick.Color.black);

        newpasswordField.setBackgroundColor(org.newdawn.slick.Color.white);
        newpasswordField.setTextColor(org.newdawn.slick.Color.black);

        newAccount = new org.newdawn.slick.Image("res/images/account.png").getScaledCopy(gameContainer.getWidth()/3, gameContainer.getHeight()/10);
        newAccountButton = new MouseOverArea(gameContainer, newAccount, gameContainer.getWidth()/3, 3*gameContainer.getHeight()/7, gameContainer.getWidth()/3, gameContainer.getHeight()/10, this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);

        newnameField.render(gameContainer, graphics);
        newpasswordField.render(gameContainer, graphics);

        uniFont.drawString(150, 50, message);
        uniFont.drawString(50,200, newnameString);
        uniFont.drawString(50,250, newpasswordString);
        newAccountButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    public void componentActivated(AbstractComponent source) {
        if ((source == newAccountButton) || (input.isKeyPressed(28))) {
            String nickname = newnameField.getText();
            String password = newpasswordField.getText();
            if (menu.addPlayer(nickname,password)){
                newnameField.setText("");
                newpasswordField.setText("");
                stateBasedGame.enterState(0, new FadeOutTransition(), new FadeInTransition());
            }
            else{
                newnameField.setText("Nome usato");
                newpasswordField.setText("");
            }
        }
    }
}
