package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;


public class Beer extends Card {
    private static final String CARD_NAME = "Pivo!";

    public Beer(String nazov, Desk desk) {
        super(nazov, desk);
    }

    public Beer(Desk desk) {
        super(CARD_NAME, desk);
    }


    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        int x = 0;
        for (Player p : players) {
            if (p == player) {
                break;
            }
            x++;
        }
        player.plusLife(players, x);
    }
}
