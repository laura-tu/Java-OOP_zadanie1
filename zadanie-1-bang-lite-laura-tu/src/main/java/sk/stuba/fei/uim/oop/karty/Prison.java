package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;

public class Prison extends Card {
    private static final String CARD_NAME = "Prison!";

    public Prison(Desk desk) {
        super(CARD_NAME, desk);
    }

    @Override
    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        int whichPlayer = this.chooseOponent(player, players);

        ArrayList<Card> board = new ArrayList<>();
        board.add(this);
        players[whichPlayer].addCardsBoard(board);

        System.out.println("--- " + players[whichPlayer].getNameOfPlayer() + " is in the PRISON ---");
    }

}
