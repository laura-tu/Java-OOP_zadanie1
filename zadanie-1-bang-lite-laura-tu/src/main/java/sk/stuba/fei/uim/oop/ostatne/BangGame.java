package sk.stuba.fei.uim.oop.ostatne;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.karty.Card;
import sk.stuba.fei.uim.oop.karty.Dynamite;
import sk.stuba.fei.uim.oop.karty.Missed;
import sk.stuba.fei.uim.oop.karty.Prison;
import sk.stuba.fei.uim.oop.stol.Desk;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.Random;

public class BangGame {
    private Random rand;
    private Player[] players;
    private int playerOnTurn;
    private Desk deck;


    public BangGame() {
        System.out.println("* BANG Game *");
        int numberOfPlayers = 0;
        boolean fewPlayers = true;
        rand = new Random();

        while (fewPlayers) {
            numberOfPlayers = KeyboardInput.readInt("** Enter number of players (2 to 4)");
            if ((numberOfPlayers < 2) || (numberOfPlayers > 4)) {
                fewPlayers = true;
            } else fewPlayers = false;
        }

        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players[i] = new Player(ZKlavesnice.readString("** Enter name of " + (i + 1) + ". player"));
        }

        this.deck = new Desk(this.players);
        deck.fourCards(this.players);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");
        while (this.numberOfAliveOnes() > 1) {

            Player activePlayer = this.players[this.playerOnTurn];
            if (!activePlayer.isAlive()) {
                ArrayList<Card> cardsToDeck = activePlayer.removeAllCardsFromHand();
                for (Card card : cardsToDeck) {
                    this.deck.addUsedCard(card);
                }
                System.out.println("--- Player " + activePlayer.getNameOfPlayer() + " is OUT OF GAME! ---");

                this.addUp();
            }

            drawCards();
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        getWinner();
    }

    private void drawCards() {
        Player activePlayer = this.players[this.playerOnTurn];

        System.out.println("\n--- Player " + activePlayer.getNameOfPlayer() + "'s turn: ---");
        System.out.print(activePlayer.getNameOfPlayer() + " lives: ");
        activePlayer.getPlayersLives();


        ArrayList<Card> playersHand = activePlayer.getCardsFromHand(activePlayer);
        ArrayList<Card> playersBoard = activePlayer.getCardsFromBoard(activePlayer);
        int numberCard = 0;

        for (Card target : playersBoard) {
            if (target instanceof Dynamite) {
                activePlayer.removeCardFromPlayerBoard(target);
                playersBoard = activePlayer.getAllBoardCards();
                target.playCard(activePlayer, players);

                playersBoard = activePlayer.getCardsFromBoard(activePlayer);
            }

        }

        boolean check = isInPrison(activePlayer);
        ArrayList<Card> board = new ArrayList<>();

        if (!check) {
            playersHand = activePlayer.getCardsFromHand(activePlayer);
            playersBoard = activePlayer.getCardsFromBoard(activePlayer);
            board = activePlayer.getAllBoardCards();
            this.turnPlayer(activePlayer);

            throwAway(activePlayer, playersHand, playersBoard, numberCard);
        } else {
            if (rand.nextInt(4) == 2) {
                System.out.println("---" + activePlayer.getNameOfPlayer() + " escaped the prison ---");


                board = activePlayer.getAllBoardCards();
                for (Card k : board) {
                    if (k instanceof Prison) {
                        activePlayer.removeCardFromPlayerBoard(k);
                        board = activePlayer.getAllBoardCards();
                        break;
                    }
                }

                playersBoard = activePlayer.getCardsFromBoard(activePlayer);
                this.turnPlayer(activePlayer);
                throwAway(activePlayer, playersHand, playersBoard, numberCard);

            } else {
                System.out.println("--- Skipping player " + activePlayer.getNameOfPlayer() + " because of prison.Not able to escape. ---");

                board = activePlayer.getAllBoardCards();
                for (Card k : board) {
                    if (k instanceof Prison) {
                        activePlayer.removeCardFromPlayerBoard(k);
                        board = activePlayer.getAllBoardCards();
                        break;
                    }
                }
            }
        }
        this.addUp();
    }

    private void turnPlayer(Player aktivnyPlayer) {
        System.out.println(">>>>>>>>>>>> START >>>>>>>>>>>>");
        this.deck.plusTwoCards(aktivnyPlayer);

        ArrayList<Card> handCards = aktivnyPlayer.getCardsFromHand(aktivnyPlayer);
        ArrayList<Card> boardCards = aktivnyPlayer.getCardsFromBoard(aktivnyPlayer);

        int numberCard = 0;
        int next = 1;

        while ((handCards.size() > 0) && (next == 1)) {
            handCards = aktivnyPlayer.getCardsFromHand(aktivnyPlayer);
            boardCards = aktivnyPlayer.getCardsFromBoard(aktivnyPlayer);

            showCards(aktivnyPlayer, handCards, boardCards);
            numberCard = chooseCard(handCards);
            if (numberCard == -1) {
                System.out.println("! You skipped your turn !");
                break;
            } else {
                handCards.get(numberCard).playCard(aktivnyPlayer, this.players);

                if (this.numberOfAliveOnes() == 1) {
                    getWinner();
                }

                this.deck.addUsedCard(handCards.get(numberCard));
                aktivnyPlayer.removeCardFromPlayerHand(handCards.get(numberCard));

                handCards = aktivnyPlayer.getCardsFromHand(aktivnyPlayer);
                boardCards = aktivnyPlayer.getCardsFromBoard(aktivnyPlayer);
                if (boardCards.size() > 0) {
                    this.deck.addUsedCard(boardCards.get(0));
                    aktivnyPlayer.removeCardFromPlayerBoard(boardCards.get(0));

                    boardCards = aktivnyPlayer.getCardsFromBoard(aktivnyPlayer);
                }


                next = ZKlavesnice.readInt("\n** Do you want to CONTINUE? YES-1  NO-2 **");
                if (next < 1 || next > 2) {
                    System.out.println("!!!  You entered wrong number of card. Try Again!  !!! ");
                    next = ZKlavesnice.readInt("***Do you want to CONTINUE? YES-1  NO-2");
                } else {
                    continue;
                }
            }

        }
    }

    private int chooseCard(ArrayList<Card> cards) {
        int numberCard = 0;
        while (true) {
            numberCard = ZKlavesnice.readInt("*** Which card do you wanna choose?") - 1;

            if ((numberCard < -1 || numberCard > cards.size() - 1) ||(cards.get(numberCard) instanceof Missed)) {
                System.out.println("!!!  You entered wrong card. Try Again!  !!! ");
            } else {
                break;
            }
        }

        return numberCard;
    }

    private void showCards(Player player, ArrayList<Card> hand, ArrayList<Card> board) {
        System.out.println(player.getNameOfPlayer() + " cards on board:");
        for (int i = 0; i < board.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + board.get(i).getName() + "\n");
        }
        System.out.println(player.getNameOfPlayer() + " cards on hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println("Card " + (i + 1) + ": " + hand.get(i).getName());
        }
    }

    private int numberOfAliveOnes() {
        int count = 0;
        for (Player player : this.players) {
            if (player.isAlive()) {
                count++;
            }
        }
        return count;
    }

    private void addUp() {
        this.playerOnTurn++;
        this.playerOnTurn %= this.players.length;
    }

    private void getWinner() {
        for (Player h : this.players) {
            if (h.isAlive()) {
                System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                System.out.println("      THE WINNER IS: " + h.getNameOfPlayer());
            }
        }
    }

    private boolean isInPrison(Player player) {
        boolean prisoner = false;

        for (Card k : player.getAllBoardCards()) {
            if (k instanceof Prison) {
                prisoner = true;
                break;
            } else {
                prisoner = false;
            }
        }
        return prisoner;
    }

    private void throwAway(Player player, ArrayList<Card> playersHand, ArrayList<Card> playersBoard, int num) {

        if (player.getNumberOfLives() < (player.getSizeHandCards())) {
            System.out.println("** YOU HAVE TO THROW AWAY (at least one) CARD FROM HAND: ");

            showCards(player, playersHand, playersBoard);
            num = chooseCard(playersHand);

            this.deck.addUsedCard(playersHand.get(num));
            player.removeCardFromPlayerHand(playersHand.get(num));
        }
    }


}
