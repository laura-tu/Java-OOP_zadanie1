package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

public class CatBalou extends Card {
    private static final String CARD_NAME = "Cat Balou!";

    public CatBalou(Desk desk) {
        super(CARD_NAME, desk);
    }

    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);

        int whichPlayer = this.chooseOponent(player, players);
        int iDeck = whichDeck(players[whichPlayer]);
        int chosenCard;
        Card card;

        if (iDeck == 1) {
            chosenCard = rand.nextInt(players[whichPlayer].getSizeHandCards());

            card = players[whichPlayer].getAllHandCards().get(chosenCard);
            players[whichPlayer].removeCardFromPlayerHand(card);
            this.desk.addUsedCard(card);
        }
        if (iDeck == 2) {
            chosenCard = rand.nextInt(players[whichPlayer].getSizeBoardCards());

            card = players[whichPlayer].getAllBoardCards().get(chosenCard);
            players[whichPlayer].removeCardFromPlayerBoard(card);
            this.desk.addUsedCard(card);
        }
    }
}
