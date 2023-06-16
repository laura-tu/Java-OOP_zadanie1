package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;

public class Indians extends Card {
    private static final String CARD_NAME = "Indiani!";

    public Indians(Desk desk) {
        super(CARD_NAME, desk);
    }


    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        boolean x;
        for (int i = 0; i < players.length; i++) {
            Player p = players[i];
            x = false;
            if (p != player) {
                for (int n = 0; n < p.getSizeHandCards(); n++) {
                    ArrayList<Card> cards = p.getAllHandCards();

                    Card k = cards.get(n);
                    if (k instanceof Bang) {

                        System.out.println("--- " + p.getNameOfPlayer() + " chose: " + k.name + " . ---");
                        this.desk.addUsedCard(k);
                        players[i].removeCardFromPlayerHand(k);
                        x = true;
                        break;
                    } else {
                        x = false;
                    }
                }
                if (x == false) {
                    p.minusLife(players, i);
                }
            }
        }
    }
}
