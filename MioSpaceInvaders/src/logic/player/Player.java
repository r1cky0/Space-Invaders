package logic.player;

import logic.FileManager.AddHighScore;
import logic.sprite.dinamic.SpaceShip;

import java.io.IOException;

public class Player implements Comparable{

    private String name;
    private int credit, highScore;
    private SpaceShip spaceShip;

    public Player(String name, SpaceShip spaceShip) {
        this.name = name;
        this.spaceShip = spaceShip;
        this.credit = 0;
        this.highScore = 0;
    }

    public String getName() {
        return name;
    }

    public int getCredit() {
        return credit;
    }

    public int getHighScore() {
        return highScore;
    }

    /**
     * Se il player batte il proprio highscore, questo viene aggiornato nel database (PER ORA SALVATO SU FILE DI TESTO)
     * @param highscore Il nuovo record da salvare
     */
    public void setHighScore(int highscore) {
        this.highScore = highscore;
        AddHighScore.saveHighscore(name,highscore);
    }

    public SpaceShip getSpaceShip() {
        return spaceShip;
    }

    public void setSpaceShip(SpaceShip spaceShip) {
        this.spaceShip = spaceShip;
    }

    public void incrementCredit(int credit) {
        this.credit += credit;
    }

    /**
     * Funzione di comparazione per ordinare i player in modo crescente rispetto al proprio highscore
     * @param o Object che viene castato a player per fare confronto
     * @return ritorna un int indice dell' esito del confronto
     */
    @Override
    public int compareTo(Object o) {
        return Integer.compare(((Player) o).highScore, this.highScore);
    }
}
