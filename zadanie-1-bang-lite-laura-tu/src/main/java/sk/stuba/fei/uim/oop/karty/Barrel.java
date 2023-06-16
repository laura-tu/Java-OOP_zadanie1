package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;

public class Barrel extends Card {

    private static final String CARD_NAME = "Barrel!";

    public Barrel(Desk desk) {
        super(CARD_NAME, desk);
    }


    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        ArrayList<Card> board = new ArrayList<>();

        board.add(this);
        player.addCardsBoard(board);
    }

    public int myChance() {

        return rand.nextInt(4);

    }
}
