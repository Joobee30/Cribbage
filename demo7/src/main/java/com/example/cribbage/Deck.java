package com.example.cribbage;

import java.util.ArrayList;
import java.util.*;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        this.deck = new ArrayList<>(52);
        for (int suit = 0; suit < 4; suit++) {
            for (int value = 1; value < 14; value++) {
                this.deck.add(new Card(suit, value));
            }
        }

    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public Card dealCard() {
        Card card = deck.get(0);

        deck.remove(0);
        return card;
    }

    public Card[] deal6Card() {
        shuffle();
        Card[] cards = new Card[6];
        for (int x = 0; x < 6; x++) {
            cards[x] = deck.get(x);
        }
        return cards;

    }

    public Card getCard(int i) {
        return deck.get(i);
    }

    // Used for calculations
    public void removeCard(Card card) {
        for (int i = 0; i < deck.size(); i++) {
            if (card.getSuit() == deck.get(i).getSuit() && card.getValue() == deck.get(i).getValue())
                deck.remove(i);
        }
    }

    public void removeCard(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            deck.remove(cards.get(i));
        }
    }

    public int size() {
        return deck.size();
    }
}
