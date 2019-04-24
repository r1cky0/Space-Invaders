package states;

import logic.Player;
import logic.elements.Bullet;
import logic.elements.Invader;
import logic.elements.MovingDirections;
import logic.elements.Ship;
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
    private ArrayList<Bullet> bullets;
    private ArrayList<Invader> invaders;
    public boolean bulletShot = false;
    //private Player player;


    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        score = 0;
        this.container = container;
        //this.player = player;
        ship = new Ship(container);
        bullets = new ArrayList<>();
        invaders = new ArrayList<>();
        background = new Image("res/space.png");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(background,0,0);
        ship.render(container,graphics);
        if(!bullets.isEmpty() && !bulletShot){
            bullets.get(0).render(container,graphics);
            bulletShot = true;
        }
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
        if(input.isKeyDown(Input.KEY_SPACE)){
            try {
                bullets.add(new Bullet(container, ship.getX() + ship.getSize()/2, ship.getY()- ship.getSize()));
                for(Invader inv: invaders){
                    if(bullets.get(0).collides(inv.getShape())){
                        bullets.remove(0);
                    }
                }
                if(bullets.get(0).getEnd()){
                    bullets.remove(0);
                }
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        //bullet.update(gameContainer,i);
    }

    public void setBulletShot(boolean bulletShot) {
        this.bulletShot = bulletShot;
    }

    public void bulletCollision(){
        bullets.remove(0);
    }

    public int getID() {
        return 1;
    }

}
