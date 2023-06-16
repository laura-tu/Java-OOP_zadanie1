package sk.stuba.fei.uim.oop.stol;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.karty.*;
import sk.stuba.fei.uim.oop.karty.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Desk {
    private ArrayList<Card> deckOfCards;
    private ArrayList<Card> deckOfUsedCards;



    public Desk(Player[] players) {
        this.deckOfCards = new ArrayList<>();
        this.deckOfUsedCards = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            this.deckOfCards.add(new Bang(this));
        }
        for (int i = 0; i < 15; i++) {
            this.deckOfCards.add(new Missed(this));
        }
        for (int i = 0; i < 8; i++) {
            this.deckOfCards.add(new Beer(this));
        }
        for (int i = 0; i < 6; i++) {
            this.deckOfCards.add(new CatBalou(this));
        }
        for (int i = 0; i < 4; i++) {
            this.deckOfCards.add(new Stagecoach(this));
        }
        this.deckOfCards.add(new Indians(this));
        this.deckOfCards.add(new Indians(this));

        this.deckOfCards.add(new Indians(this));
        this.deckOfCards.add(new Indians(this));

        for (int i = 0; i < 3; i++) {
            this.deckOfCards.add(new Prison(this));
        }
        this.deckOfCards.add(new Dynamite(this));
        this.deckOfCards.add(new Barrel(this));
        this.deckOfCards.add(new Barrel(this));

        Collections.shuffle(this.deckOfCards);
    }

    public Desk(ArrayList<Card> deckOfCards) {
        this.deckOfCards = deckOfCards;
    }

    public void fourCards(Player[] players) {

        for (Player player : players) {
            ArrayList<Card> hand = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                hand.add(deckOfCards.remove(0));
            }
            player.addCards(hand);
        }
    }

    public void addUsedCard(Card usedCard) {
        this.deckOfUsedCards.add(usedCard);
        if (this.deckOfCards.size() < 4) {
            joinDecks(this.deckOfUsedCards);
        }
    }


    public ArrayList<Card> joinDecks(ArrayList<Card> usedCards) {
        this.deckOfCards.addAll(usedCards);
        Collections.shuffle(this.deckOfCards);
        return this.deckOfCards;
    }

    public void plusTwoCards(Player player) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            cards.add(this.deckOfCards.remove(0));
        }
        player.addCards(cards);

    }


}
