package logic.environment.manager.field;

public class Difficulty {

    private int difficulty;

    public Difficulty(){
        //millisecondi di sleep thread alieni
        this.difficulty = 900;
    }

    /**
     * Metodo richiamato ogni volta che si completa un livello. L' esito influenza i thread di aggiornamento
     */
    public void incrementDifficulty(){
        if(difficulty >= 500){
            difficulty -= 100;
        }
    }

    public int getDifficulty() {
        return difficulty;
    }
}
