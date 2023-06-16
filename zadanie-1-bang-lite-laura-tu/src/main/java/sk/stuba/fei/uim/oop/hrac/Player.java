package sk.stuba.fei.uim.oop.hrac;

import sk.stuba.fei.uim.oop.karty.Card;
import sk.stuba.fei.uim.oop.stol.Desk;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private final String NAME_PLAYER;
    private int numberOfLives;
    private ArrayList<Card> cardsHand;
    private ArrayList<Card> cardsOnBoard;
    private Desk deck;


    public Player(String playerName) {
        this.NAME_PLAYER = playerName;
        this.numberOfLives = 4;
        this.cardsHand = new ArrayList<Card>();
        this.cardsOnBoard = new ArrayList<Card>();
    }

    public String getNameOfPlayer() {
        return NAME_PLAYER;
    }

    public void getPlayersLives() {
        for (int i = 0; i < numberOfLives; i++) {
            System.out.print("+");
        }
        System.out.println();
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public ArrayList<Card> getAllHandCards() {
        return this.cardsHand;
    }

    public ArrayList<Card> getAllBoardCards() {
        return this.cardsOnBoard;
    }

    public int getSizeHandCards() {
        return this.cardsHand.size();
    }

    public int getSizeBoardCards() {
        return this.cardsOnBoard.size();
    }

    public void addCards(ArrayList<Card> brown_cards) {
        cardsHand.addAll(brown_cards);
    }

    public void addCardsBoard(ArrayList<Card> blue_cards) {

        boolean check = false;
        for (Card k : cardsOnBoard) {
            for (Card c : blue_cards) {
                if (Objects.equals(k, c)) {
                    check = true;
                }
            }
        }
        if (!check) {
            cardsOnBoard.addAll(blue_cards);
        }
    }

    public ArrayList<Card> getCardsFromHand(Player player) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (Card karta : this.cardsHand) {
            cards.add(karta);
        }
        return cards;
    }

    public ArrayList<Card> getCardsFromBoard(Player player) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (Card karta : this.cardsOnBoard) {
            cards.add(karta);
        }
        return cards;
    }

    public void removeCardFromPlayerHand(Card oldCard) {
        for (int i = 0; i < this.cardsHand.size(); i++) {

            if (Objects.equals(this.cardsHand.get(i), oldCard)) {
                this.cardsHand.remove(i);
                break;
            }
        }
    }

    public void removeCardFromPlayerBoard(Card oldCard) {
        for (int i = 0; i < this.cardsOnBoard.size(); i++) {
            if (Objects.equals(this.cardsOnBoard.get(i), oldCard)) {
                this.cardsOnBoard.remove(i);
                break;
            }
        }
    }

    public boolean isAlive() {
        return this.numberOfLives > 0;
    }

    public ArrayList<Card> removeAllCardsFromHand() {
        ArrayList<Card> removedCards = new ArrayList<>(this.cardsHand);
        this.cardsHand.clear();
        return removedCards;
    }

    public void minusLife(Player[] players, int who) {
        players[who].numberOfLives--;
        System.out.println(players[who].getNameOfPlayer() + "'s number of lives: " + players[who].getNumberOfLives());
    }

    public void minusThreeLives(Player[] players, int who) {
        if(players[who].getNumberOfLives()>=3){
            players[who].numberOfLives--;
            players[who].numberOfLives--;
            players[who].numberOfLives--;
            System.out.println(players[who].getNameOfPlayer() + "'s number of lives: " + players[who].getNumberOfLives());
        }
        else{
            ArrayList<Card> cardsToDeck = players[who].removeAllCardsFromHand();
            for (Card card : cardsToDeck) {
                deck.addUsedCard(card);
            }
            System.out.println("--- Player " + players[who].getNameOfPlayer() + " is OUT OF GAME! ---");

        }

    }

    public void plusLife(Player[] players, int who) {
        this.numberOfLives++;
        System.out.println(players[who].getNameOfPlayer() + "'s number of lives: " + players[who].getNumberOfLives());
    }

    ;
}