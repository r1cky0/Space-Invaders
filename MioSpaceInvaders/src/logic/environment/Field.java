package logic.environment;

import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.Sprite;
import logic.sprite.dinamic.Bullet;
import logic.sprite.dinamic.Invader;
import logic.sprite.dinamic.SpaceShip;
import logic.sprite.unmovable.Brick;
import logic.sprite.unmovable.Bunker;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

public class Field {

    //DIMENSIONS
    private final double MIN_HEIGHT = 0.0;
    private final double MIN_WIDTH = 0.0;
    private double max_height;
    private double max_width;
    private double invader_size;
    private double bullet_size;
    private double brick_size;

    private Player player;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Bullet ship_bullet;
    private boolean ship_shot;
    private Bullet invader_bullet;
    private boolean invader_shot;
    private boolean game_over;

    public Field(Player player, double max_height, double max_width){
        this.player = player;
        this.max_height = max_height;
        this.max_width = max_width;

        invaders = new ArrayList<>();
        bunkers = new ArrayList<>();

        invader_size = max_width/20;
        bullet_size = max_width / 100;
        brick_size = max_width/80;

        ship_bullet = null;
        ship_shot = false;
        invader_bullet = null;
        invader_shot = false;
        game_over = false;

        startGame();
    }

    public void startGame(){
        //inizializzazione di tutti gli elementi all'inizio del gioco

        double base_x = (max_width - 8*(invader_size))/2;
        double base_y = max_height/20;
        double x;

        for(int i=0; i<4; i++){
            x = base_x;

            for(int j=0; j<8; j++){
                Coordinate coordinate = new Coordinate(x,base_y);
                Invader invader = new Invader(coordinate,invader_size, 10);
                invaders.add(invader);
                x+=invader_size;
            }
            base_y+=invader_size;
        }

        base_x = (max_width - 20*(brick_size))/5;
        base_y = (max_height-max_height/10);
        x = base_x;

        for(int i=1; i<5;i++){
            Bunker bunker = new Bunker(x,base_y, brick_size);
            bunkers.add(bunker);
            x = base_x*i + (5*brick_size);
        }
    }

    public void nextLevel(){
        //metodo da richiamare quando finiscono gli invaders e si passa al livello successivo
    }

    public boolean gameOver(){
        return game_over;
    }

    public Coordinate shipMovement(MovingDirections md){

        if(((player.getSpaceShip().getX() + player.getSpaceShip().getSize()/2) < max_width)
                && (md == MovingDirections.RIGHT)){
            return player.getSpaceShip().moveRight();
        }

        if(((player.getSpaceShip().getX() - player.getSpaceShip().getSize()/2) > MIN_WIDTH)
                && (md == MovingDirections.LEFT)){
            return player.getSpaceShip().moveLeft();
        }

        return player.getSpaceShip().getCoordinate();
    }

    public void shipShot() throws InterruptedException {

        if(!ship_shot) {
            ship_bullet = new Bullet(player.getSpaceShip().getCoordinate(), bullet_size);
            ship_shot = true;

                while ((!checkCollision(player.getSpaceShip(), ship_bullet) && (ship_bullet.getY() > MIN_HEIGHT))) {
                    Runnable runnable = () -> ship_bullet.moveUp();
                    Thread thread = new Thread(runnable);
                    thread.start();
                    Thread.sleep(50);
                    //***************
                    System.out.println(ship_bullet.getCoordinate());
                    //***************
                }
        }
    }

    private boolean checkCollision(Sprite sprite, Bullet bullet){

        for(Bunker bunker:bunkers){
            ListIterator<Brick> listIterator = bunker.getBricks().listIterator();

            while(listIterator.hasNext()){

                if(listIterator.next().collides(bullet)){
                    listIterator.remove();
                    //***************
                    System.out.println("Bunker colpito");
                    //***************
                    if(sprite instanceof SpaceShip) {
                        ship_bullet = null;
                        ship_shot = false;
                    }
                    else {
                        invader_bullet = null;
                        invader_shot = false;
                    }
                    return true;
                }
            }
        }

        if(sprite instanceof SpaceShip) {
            ListIterator<Invader> listIterator = invaders.listIterator();

            while (listIterator.hasNext()) {
                Invader invader = listIterator.next();

                if (invader.collides(bullet)) {
                    player.getSpaceShip().incrementCurrentScore(invader.getValue());
                    listIterator.remove();
                    //***************
                    System.out.println("Invader colpito");
                    //***************
                    ship_bullet = null;
                    ship_shot = false;
                    return true;
                }
            }
            return false;
        }
        else{

            if(player.getSpaceShip().collides(bullet)){

                if(player.getSpaceShip().decreaseLife() == 0){
                    game_over = true;
                }
                else{
                    invader_bullet = null;
                    invader_shot = false;
                    return true;
                }
            }
            return false;
        }
    }

    public void invaderDirection(){
        MovingDirections md = MovingDirections.RIGHT;

        for(Invader invader: invaders){

            if((invader.getX() + invader.getSize()/2) >= max_width){
                invaderMovement(MovingDirections.DOWN);
                md = MovingDirections.LEFT;
            }
            else if((invader.getX() - invader.getSize()/2) <= MIN_WIDTH){
                invaderMovement(MovingDirections.DOWN);
                md = MovingDirections.RIGHT;
            }
        }
        invaderMovement(md);
    }

    private void invaderMovement(MovingDirections md){

        for(Invader invader:invaders) {

            switch (md) {
                case RIGHT:
                    invader.moveRight();
                    break;
                case LEFT:
                    invader.moveLeft();
                    break;
                case DOWN:
                    invader.moveDown();
                    break;
            }
        }
    }

    public void invaderShot() throws InterruptedException {

        if(!invader_shot){
            Random rand = new Random();
            int random = rand.nextInt(invaders.size());
            //***************
            System.out.println(random);
            //***************
            invader_bullet = new Bullet(invaders.get(random).getCoordinate(), bullet_size);
            invader_shot = true;

            while ((!checkCollision(invaders.get(random), invader_bullet) && (invader_bullet.getY() < max_height))) {
                Runnable runnable = () -> invader_bullet.moveDown();
                Thread thread = new Thread(runnable);
                thread.start();
                Thread.sleep(50);
                //***************
                System.out.println(invader_bullet.getCoordinate());
                //***************
            }
        }
    }

    public boolean isShipShot() {
        return ship_shot;
    }

    public boolean isInvaderShot() {
        return invader_shot;
    }
}
