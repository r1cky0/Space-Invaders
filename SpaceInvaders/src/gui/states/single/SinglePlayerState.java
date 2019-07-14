package gui.states.single;

import gui.elements.GraphicShip;
import gui.states.GameState;
import gui.states.IDStates;
import logic.manager.game.SinglePlayer;
import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.manager.menu.Menu;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.dinamic.bullets.InvaderBullet;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SinglePlayerState extends GameState {
    private StateBasedGame stateBasedGame;
    private GameContainer gameContainer;

    private Menu menu;
    private SinglePlayer singlePlayer;
    private UnicodeFont uniFontData;

    //IMAGES
    private Image background;
    private boolean collision;

    public SinglePlayerState(Menu menu) {
        this.menu = menu;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.stateBasedGame = stateBasedGame;
        this.gameContainer = gameContainer;
        background = new Image(readerXmlFile.read("defaultBackground"));
        uniFontData = build(3*gameContainer.getWidth()/100f);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        singlePlayer = menu.getSinglePlayer();
        singlePlayer.startGame();
        graphicShip = new GraphicShip(new Image(readerXmlFile.read(menu.getCustomization().getCurrentShip())));
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        graphics.drawImage(background, 0, 0);

        Color color;
        int highscore;
        if (singlePlayer.getPlayer().getHighScore() >= singlePlayer.getSpaceShip().getCurrentScore()) {
            color = Color.white;
            highscore = singlePlayer.getPlayer().getHighScore();
        } else {
            color = Color.green;
            highscore = singlePlayer.getSpaceShip().getCurrentScore();
        }

        uniFontData.drawString(85 * gameContainer.getWidth() / 100f, 2 * gameContainer.getHeight() / 100f,
                "Lives: " + singlePlayer.getSpaceShip().getLife(), Color.red);
        uniFontData.drawString((gameContainer.getWidth() - uniFontData.getWidth("Score: ")) / 2f,
                2 * gameContainer.getHeight() / 100f, "Score: " + singlePlayer.getSpaceShip().getCurrentScore(),
                color);
        uniFontData.drawString(2 * gameContainer.getWidth() / 100f, 2 * gameContainer.getHeight() / 100f,
                "Highscore: " + highscore, Color.green);

        graphicShip.render(singlePlayer.getSpaceShip());

        if (singlePlayer.isBonusInvader()) {
            graphicBonusInvader.render(singlePlayer.getBonusInvader());
        }
        if (singlePlayer.getSpaceShip().isShipShot()) {
            graphicBullet.render(singlePlayer.getSpaceShipBullet());
        }
        for (Invader invader : singlePlayer.getInvaders()) {
            graphicInvader.render(invader);
        }
        for (InvaderBullet invaderBullet : singlePlayer.getInvadersBullet()) {
            graphicBullet.render(invaderBullet);
        }
        for (Bunker bunker : singlePlayer.getBunkers()) {
            for (Brick brick : bunker.getBricks()) {
                graphicBrick.render(brick);
            }
        }
        if (collision) {
            audioplayer.explosion();
        }
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT)){
            singlePlayer.execCommand(Commands.MOVE_RIGHT, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            singlePlayer.execCommand(Commands.MOVE_LEFT, delta);
        }
        if(input.isKeyPressed(Input.KEY_SPACE)){
            if(!singlePlayer.getSpaceShip().isShipShot()){
                audioplayer.shot();
            }
            singlePlayer.execCommand(Commands.SHOT, delta);
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            singlePlayer.execCommand(Commands.EXIT, delta);
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
            audioplayer.menu();
        }
        singlePlayer.update(delta);

        //STATO GIOCO
        if (singlePlayer.getGameState() == States.GAMEOVER) {
            stateBasedGame.enterState(IDStates.GAMEOVERSINGLE_STATE, new FadeOutTransition(), new FadeInTransition());
        }else if(singlePlayer.getGameState() == States.NEWHIGHSCORE) {
            stateBasedGame.enterState(IDStates.NEWHIGHSCORE_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

     @Override
    public int getID() {
        return IDStates.SINGLEPLAYER_STATE;
    }
}