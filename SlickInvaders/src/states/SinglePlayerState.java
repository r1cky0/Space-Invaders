package states;

import logic.Player;
import logic.elements.Bullet;
import logic.elements.MovingDirections;
import logic.elements.Ship;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class SinglePlayerState extends BasicGameState{
    private GameContainer container;
    private Ship ship;
    private int score;
    private Image background;
    private Bullet bullet;
    //private Player player;


    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        score = 0;
        this.container = container;
        //this.player = player;
        ship = new Ship(container);
        background = new Image("res/space.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        ship.render(container,graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        //bullet.update(gameContainer,i);
    }

    public void keyPressed(int key, char c){
        if(key == Input.KEY_LEFT){
            ship.move(MovingDirections.LEFT);
        }

        if(key == Input.KEY_RIGHT){
            ship.move(MovingDirections.RIGHT);
        }

        if(key == Input.KEY_SPACE){
            try {
                bullet = new Bullet(container, ship.getX() + ship.getSize()/2, ship.getY()- ship.getSize());
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public int getID() {
        return 1;
    }

}
