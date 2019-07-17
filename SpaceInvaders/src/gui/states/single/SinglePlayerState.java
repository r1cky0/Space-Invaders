package gui.states.single;

import gui.drawer.SpriteDrawer;
import gui.states.BasicState;
import gui.states.IDStates;
import logic.manager.game.single.SinglePlayer;
import logic.manager.game.Commands;
import logic.manager.game.States;
import logic.manager.menu.Menu;
import logic.sprite.dinamic.bullets.Bullet;
import logic.sprite.dinamic.invaders.Invader;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;
import org.newdawn.slick.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class SinglePlayerState extends BasicState {
    private SpriteDrawer spriteDrawer;
    private Menu menu;
    private SinglePlayer singlePlayer;
    private int life;

    public SinglePlayerState(Menu menu) {
        this.menu = menu;
        spriteDrawer = new SpriteDrawer();
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        super.init(gameContainer, stateBasedGame);
    }

    @Override
    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        getAudioplayer().game();
        singlePlayer = menu.getSinglePlayer();
        singlePlayer.startGame();
        spriteDrawer.addShipImage(menu.getCustomization().getCurrentShip());
        life = 3;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) {
        super.render(gameContainer, stateBasedGame, graphics);
        Color color;
        int highscore;
        if (singlePlayer.getPlayer().getHighScore() >= singlePlayer.getSpaceShip().getCurrentScore()) {
            color = Color.white;
            highscore = singlePlayer.getPlayer().getHighScore();
        } else {
            color = Color.green;
            highscore = singlePlayer.getSpaceShip().getCurrentScore();
        }
        String textScore = "Score: " + singlePlayer.getSpaceShip().getCurrentScore();
        String textLives = "Lives: " + life;
        String textHighscore = "Highscore: " + highscore;
        getDataFont().drawString(85*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f, textLives, Color.red);
        getDataFont().drawString((gameContainer.getWidth() - getDataFont().getWidth(textScore))/2f,2*gameContainer.getHeight()/100f, textScore, color);
        getDataFont().drawString(2*gameContainer.getWidth()/100f,2*gameContainer.getHeight()/100f, textHighscore, Color.green);

        spriteRender();
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_RIGHT)){
            singlePlayer.execCommand(Commands.MOVE_RIGHT, delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            singlePlayer.execCommand(Commands.MOVE_LEFT, delta);
        }
        if(input.isKeyPressed(Input.KEY_SPACE)){
            if(!singlePlayer.getSpaceShip().isShipShot()){
                getAudioplayer().shot();
            }
            singlePlayer.execCommand(Commands.SHOT, delta);
        }
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            singlePlayer.execCommand(Commands.EXIT, delta);
            stateBasedGame.enterState(IDStates.MENU_STATE, new FadeOutTransition(), new FadeInTransition());
        }
        singlePlayer.update(delta);
        if(life > singlePlayer.getSpaceShip().getLife()){
            getAudioplayer().explosion();
        }
        life = singlePlayer.getSpaceShip().getLife();

        //STATO GIOCO
        if (singlePlayer.getGameState() == States.GAMEOVER) {
            stateBasedGame.enterState(IDStates.GAMEOVERSINGLE_STATE, new FadeOutTransition(), new FadeInTransition());
        }else if(singlePlayer.getGameState() == States.NEWHIGHSCORE) {
            stateBasedGame.enterState(IDStates.NEWHIGHSCORE_STATE, new FadeOutTransition(), new FadeInTransition());
        }
    }

    private void spriteRender(){
        spriteDrawer.render(singlePlayer.getSpaceShip());
        if (singlePlayer.isBonusInvader()) {
            spriteDrawer.render(singlePlayer.getBonusInvader());
        }
        if (singlePlayer.getSpaceShip().isShipShot()) {
            spriteDrawer.render(singlePlayer.getSpaceShipBullet());
        }
        for (Invader invader : singlePlayer.getInvaders()) {
            spriteDrawer.render(invader);
        }
        for (Bullet invaderBullet : singlePlayer.getInvadersBullet()) {
            spriteDrawer.render(invaderBullet);
        }
        for (Bunker bunker : singlePlayer.getBunkers()) {
            for (Brick brick : bunker.getBricks()) {
                spriteDrawer.render(brick);
            }
        }
        life = singlePlayer.getSpaceShip().getLife();
    }

     @Override
    public int getID() {
        return IDStates.SINGLEPLAYER_STATE;
    }
}