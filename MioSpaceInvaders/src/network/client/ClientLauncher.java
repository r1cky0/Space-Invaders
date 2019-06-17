package network.client;

import logic.player.Player;
import logic.sprite.Coordinate;
import logic.sprite.dinamic.SpaceShip;
import net.java.games.input.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientLauncher {

    public static void main(String[] args){

        Player player = new Player("Prova", new SpaceShip(new Coordinate(10,10),10));

        //INDIRIZZO IP SERVER, PORTA SERVER
        Client client = new Client(player, "localhost", 9999);

    }
}
