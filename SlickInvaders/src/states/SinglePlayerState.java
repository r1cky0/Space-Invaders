package states;

import logic.Player;
import logic.elements.Bullet;
import logic.elements.Invader;
import logic.elements.MovingDirections;
import logic.elements.Ship;
import org.lwjgl.Sys;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;

public class SinglePlayerState extends BasicGameState{
    private GameContainer container;
    private Ship ship;
    private int score;
    private Image background;
    private Bullet bullet;
    private ArrayList<Invader> invaders;
    public boolean bulletShot = false;
    //private Player player;


    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        score = 0;
        this.container = container;
        //this.player = player;
        ship = new Ship(container);
        bullet = null;
        invaders = new ArrayList<>();
        background = new Image("res/space.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        ship.render(container,graphics);
        if(bullet != null){bullet.render(container,graphics);}
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        if(input.isKeyDown(Input.KEY_LEFT)){
            ship.move(MovingDirections.LEFT);

        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            ship.move(MovingDirections.RIGHT);
        }

        if (bullet != null) {
            bullet.update(container,i);
        }

        if(bullet !=null) {
            if(bullet.endReached()){
                bullet = null;
                bulletShot = false;
            }
            for(Invader inv: invaders){
                if(bullet.collides(inv.getShape())){
                    invaders.remove(inv);
                    bullet = null;
                    bulletShot = false;
                    score += inv.getValue();
                }
            }
        }

    }


    public void keyPressed(int key, char c){
        if(key==Input.KEY_SPACE && !bulletShot){
            try {
                bullet = new Bullet(container, ship.getX(), ship.getY()-ship.getSize()/2);
                this.bulletShot = true;
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
    }

    public int getID() {
        return 1;
    }


}
