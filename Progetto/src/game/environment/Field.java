package game.environment;

import game.player.Player;
import game.sprite.buildings.Bunker;
import game.sprite.elements.Bullet;
import game.sprite.elements.Invader;
import game.sprite.elements.Ship;

import java.util.ArrayList;
import java.util.Random;

public class Field {

    private Ship ship;
    private ArrayList<Invader> invaders;
    private ArrayList<Bunker> bunkers;
    private Coordinate size;
    private int score=0;
    private Player player;

    public Field(Player player){
        this.player=player;
        initLevel();
    }

    /**
     * Genera un bullet poco sopra la ship. Dopo ció controlla se il bullet va in collisione con un bunker(prima) o con
     * un invader(poi). Se succede, eliminiamo bullet ed elemento colpito
     */

    public void initLevel(){

        for(int i=90; i>50; i-=10){
            for(int j=10;j<90;j+=10) {
                Coordinate coordinate = new Coordinate(j + 5, i-5);
                Invader invader = new Invader(coordinate, 10);
                invaders.add(invader);
            }
        }

        int index = 4;
        for(int i=0; i<4; i++){
            Bunker bunker = new Bunker(index);
            bunkers.add(bunker);
            index = index + 22;
        }

        Coordinate start_position = new Coordinate(50,5);
        ship = new Ship("Navicella", start_position,player);

    }

    public void initGame(){
        initLevel();
        this.score = 0;
    }

    public void shot(){
        Coordinate bCoordinates = new Coordinate(ship.getCoordinate().getX(),ship.getCoordinate().getY()+2);
        Bullet b = new Bullet(bCoordinates);
        for(Bunker bun : bunkers){
            if(bun.deleteBrick(bCoordinates)){
                b = null;
                return;
            }
        }
        for(Invader i : invaders){
            if(bCoordinates.getX() >= i.getCoordinate().getX()-2 || bCoordinates.getX() <+ i.getCoordinate().getX()+2 ){
                b = null;
                score+=i.getValue();
                invaders.remove(i);
            }
        }
        if(invaders.isEmpty()){
            levelWin();
        }
    }

    public void invaderShot(){
        Random rand = new Random();
        int n = rand.nextInt(invaders.size());
        Coordinate bCoordinates = new Coordinate(invaders.get(n).getCoordinate().getX(),invaders.get(n).getCoordinate().getY()-2);
        Bullet b = new Bullet(bCoordinates);

        for(Bunker bun : bunkers){
            if(bun.deleteBrick(bCoordinates)){
                b = null;
                return;
            }
        }
        if(bCoordinates.getX() >= ship.getCoordinate().getX()-2 || bCoordinates.getX() <+ ship.getCoordinate().getX()+2 ){
            if(ship.getLife()==1){
                gameOver();
            }
            else{ship.decreseLife();}
        }
    }


    public void shipMoveRight(){
        ship.getCoordinate().setX(ship.getCoordinate().getX()+1);
    }

    public void shipMoveLeft(){
        ship.getCoordinate().setX(ship.getCoordinate().getX()-1);
    }

    public void moveDown(){
    }

    public void levelWin(){
        initLevel();
    }

    public void gameOver(){
     String message= player.toString();
     if(player.getHighscore()<score){
         player.setHighscore(score);
         message+="Nuovo Highscore: "+ score;
     }
     int credit=score/32;
     player.changeCredit(credit);
     System.out.println(message);
     initGame();
    }


}
