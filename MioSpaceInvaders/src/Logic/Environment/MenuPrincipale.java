package Logic.Environment;

import Logic.Player.Player;

public class MenuPrincipale {

    private Classifica classifica;
    private Personalizzazione personalizzazione;
    private Field field;

    public MenuPrincipale(Player player){
        classifica = new Classifica();
        personalizzazione = new Personalizzazione();
        field = new Field(player);
    }

}
