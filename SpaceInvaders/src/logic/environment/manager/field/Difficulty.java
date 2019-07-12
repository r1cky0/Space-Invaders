package logic.environment.manager.field;

class Difficulty {

    private int difficulty;

    Difficulty(){
        //millisecondi di sleep thread alieni
        this.difficulty = 900;
    }

    /**
     * Metodo richiamato ogni volta che si completa un livello.
     * L'aumento della difficoltà si traduce in uno sleep più lento del thread invader e quindi
     * un movimento e sparo più veloce degli stessi.
     */
    void incrementDifficulty(){
        if(difficulty >= 500){
            difficulty -= 100;
        }
    }

    int getDifficulty() {
        return difficulty;
    }
}
