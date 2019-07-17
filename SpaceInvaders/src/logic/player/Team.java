package logic.player;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe che rappresenta la squadra durante il multiplayer.
 * Contiene mappa dei giocatori presenti nella partita e il punteggio complessivo di squadra.
 */
public class Team {
    private ConcurrentHashMap<Integer, Player> players;
    private int teamCurrentScore;
    private int scoreRemovedPlayer;

    public Team(){
        players = new ConcurrentHashMap<>();
        teamCurrentScore = 0;
        scoreRemovedPlayer = 0;
    }

    /**
     * Aggiunta di nuovo giocatore alla mappa
     *
     * @param ID id giocatore
     * @param player giocatore
     */
    public void addPlayer(int ID, Player player){
        players.put(ID, player);
    }

    /**
     * Metodo che rimuove giocatore dalla mappa.
     * Salva il suo score in una variabile per non perdere il punteggio ottenuto da quel giocatore fino alla sua
     * rimozione.
     *
     * @param ID id giocatore
     */
    public void removePlayer(int ID){
        if(players.size()>1) {
            scoreRemovedPlayer += players.get(ID).getSpaceShip().getCurrentScore();
        }
        players.remove(ID);
    }

    /**
     * Metodo che svuota la mappa.
     */
    public void clear(){
        players.clear();
        teamCurrentScore = 0;
        scoreRemovedPlayer = 0;
    }

    /**
     * Metodo che incrementa la vita di tutti i giocatori quando si passa il livello.
     */
    public void incrementLife(){
        for(Player player : players.values()){
            player.getSpaceShip().incrementLife();
        }
    }

    /**
     * Metodo che calcola lo score tenendo conto anche dei punteggi dei giocatori già eliminati.
     */
    public void calculateTeamCurrentScore(){
        teamCurrentScore = 0;
        for(Player player : players.values()){
            teamCurrentScore += player.getSpaceShip().getCurrentScore() + scoreRemovedPlayer;
        }
    }

    public int getTeamCurrentScore(){
        return teamCurrentScore;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

}
