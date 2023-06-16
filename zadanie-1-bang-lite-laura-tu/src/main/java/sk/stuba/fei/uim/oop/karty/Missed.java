package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

public class Missed extends Card {
    private static final String CARD_NAME = "Missed!";

    public Missed(Desk desk) {
        super(CARD_NAME, desk);
    }


    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
    }

}
