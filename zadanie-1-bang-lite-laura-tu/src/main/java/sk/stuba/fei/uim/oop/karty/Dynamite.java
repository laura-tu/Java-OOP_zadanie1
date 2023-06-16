package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;

/**
 * Túto kartu pri zahratí vykladáte pred seba.
 * Efekt karty Dynamit sa kontroluje vždy na začiatku kola, pokiaľ dynamit
 * vybuchne pred vami, strácate 3 životy a karta sa odhadzuje do balíčka.
 * Pokiaľ vám dynamit nevybuchne, karta sa posúva hráčovi, ktorý hral pred vami (Dynamit sa posúva opačným smerom,
 * akým prebieha hra).
 * Šanca na vybuchnutie dynamitu je 1 ku 8;
 * <p>
 * Pokiaľ by nastala situácia, že hráč má na začiatku kola pred sebou aj kartu Väzenie aj Dynamit, kontroluje sa najskôr
 * efekt karty Dynamit a až potom Väzenie
 **/
public class Dynamite extends Card {
    private static final String CARD_NAME = "Dynamit!";
    private Desk deck;

    public Dynamite(Desk desk) {
        super(CARD_NAME, desk);
    }

    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);

        ArrayList<Card> board = new ArrayList<>();
        board.add(this);
        player.addCardsBoard(board);

        this.deck = new Desk(players);
        int chance = rand.nextInt(8);

        if (chance == 7) {

            for (int index = 0; index < players.length; index++) {
                Player x = players[index];
                if (x == player) {

                    if ((!player.isAlive())|| (player.getNumberOfLives()<3)) {
                        ArrayList<Card> cardsToDeck =player.removeAllCardsFromHand();
                        for (Card card : cardsToDeck) {
                            deck.addUsedCard(card);
                        }
                        System.out.println("--- Player " + player.getNameOfPlayer() + " is OUT OF GAME! ---");
                        break;
                    }
                    player.minusThreeLives(players, index);
                    System.out.println("--- " + player.getNameOfPlayer() + " lost 3 lives ---");
                }
            }

            board = player.getAllBoardCards();
            for (Card k : board) {
                if (k instanceof Dynamite) {
                    player.removeCardFromPlayerBoard(k);
                }
            }

        } else {
            for (int index = 0; index < players.length; index++) {
                Player x = players[index];
                if ((x == player) && (index == 0)) {
                    if(players[players.length-1].isAlive()){
                        players[players.length-1].addCardsBoard(board);
                        System.out.println("--- Dynamite goes to " + players[players.length-1].getNameOfPlayer() + " ---");
                        break;
                    }
                    else{
                        players[players.length-2].addCardsBoard(board);
                        System.out.println("--- Dynamite goes to " + players[players.length-2].getNameOfPlayer() + " ---");
                        break;
                    }

                }
                if ((x == player) && (index != 0)) {
                    if(players[index - 1].isAlive()){
                        players[index - 1].addCardsBoard(board);
                        System.out.println("--- Dynamite goes to " + players[index-1].getNameOfPlayer() + " ---");
                        break;
                    }
                    else{
                        players[index - 2].addCardsBoard(board);
                        System.out.println("--- Dynamite goes to " + players[index-2].getNameOfPlayer() + " ---");
                        break;
                    }

                }

            }


        }
    }
}
