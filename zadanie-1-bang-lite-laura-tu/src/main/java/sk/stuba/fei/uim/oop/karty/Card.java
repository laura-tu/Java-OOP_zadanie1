package sk.stuba.fei.uim.oop.karty;

import sk.stuba.fei.uim.oop.hrac.Player;
import sk.stuba.fei.uim.oop.stol.Desk;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.Random;

public abstract class Card {
    protected String name;
    protected Desk desk;
    Random rand = new Random();

    public Card(String name, Desk desk) {
        this.name = name;
        this.desk = desk;
    }

    public void playCard(Player player, Player[] players) {
        System.out.println("--- " + player.getNameOfPlayer() + " chose: " + this.name + " . ---");
    }

    public String getName() {
        return name;
    }

    public int whichDeck(Player player) {
        int whichDeck = 0;
        if ((player.getSizeHandCards() > 0) && (player.getSizeBoardCards() > 0)) {
            whichDeck = ZKlavesnice.readInt(" ---From where do you wanna choose one of " + player.getNameOfPlayer() + "'s cards? Hand-1  Desk-2");
        }
        if ((player.getSizeHandCards() > 0) && (player.getSizeBoardCards() == 0)) {
            whichDeck = 1;
        }
        if ((player.getSizeBoardCards() > 0) && (player.getSizeHandCards() == 0)) {
            whichDeck = 2;
        }
        return whichDeck;
    }

    protected int chooseOponent(Player player, Player[] players) {
        int numberPlayer = 0;
        int thatOne;
        while (true) {
            for (int index = 0; index < players.length; index++) {
                Player x = players[index];
                if ((x != player) && (x.isAlive())) {
                    System.out.println(index + 1 + " " + x.getNameOfPlayer());
                } else {
                    thatOne = index;
                }

            }
            numberPlayer = ZKlavesnice.readInt("**I choose player number:") - 1;
            if ((players[numberPlayer] == player) || (!players[numberPlayer].isAlive())) {
                numberPlayer = ZKlavesnice.readInt("** Again.I choose player number:") - 1;
            }

            if (numberPlayer < 0 || numberPlayer > players.length) {
                System.out.println("!!!! Hey!This player doesn't exist!Try again! !!!");
            } else {
                break;
            }
        }
        return numberPlayer;
    }




}
