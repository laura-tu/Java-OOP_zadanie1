package sk.stuba.fei.uim.oop.karty;


import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;


public class Bang extends Card {
    private static final String CARD_NAME = "Bang!";

    public Bang(Desk desk) {
        super(CARD_NAME, desk);
    }


    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        int whichPlayer = this.chooseOponent(player, players);

        boolean saved = false;
        ArrayList<Card> cardsBoard = players[whichPlayer].getAllBoardCards();

        for (Card q : cardsBoard) {
            if (q instanceof Barrel) {
                q.playCard(players[whichPlayer], players);
                int a = ((Barrel) q).myChance();
                if (a == 3) {
                    System.out.println("--- BARREL saved you, " + players[whichPlayer].getNameOfPlayer() + " ---");
                    saved = true;
                    break;
                }
            } else {
                saved = false;
            }
        }

        if (!saved) {
            ArrayList<Card> cards = players[whichPlayer].getAllHandCards();
            for (Card k : cards) {
                if (k instanceof Missed) {
                    k.playCard(players[whichPlayer], players);
                    this.desk.addUsedCard(k);
                    players[whichPlayer].removeCardFromPlayerHand(k);
                    saved = true;
                    break;
                } else {
                    saved = false;
                }
            }
        }
        if (!saved) {
            player.minusLife(players, whichPlayer);
            System.out.println("---Your BARREL didn't help you or you don't have MISSED---");
        }
    }
}
