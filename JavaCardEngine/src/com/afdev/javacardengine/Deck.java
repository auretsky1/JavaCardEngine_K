package com.afdev.javacardengine;

import java.util.*;

public class Deck {
    // Default constructor
    public Deck() {
        this.deck = new ArrayList<>();
    }
    
    // Get the top card of the deck
    public Card getTopCard() {
        return deck.get(0);
    }
    
    // Get the top card and remove it from deck
    public Card getTopCardRemove() {
        return deck.remove(0);
    }
    
    // Add a card to this deck
    public void addCard(Card card) {
        deck.add(card);
    }
    
    // Get the size of the deck
    public int getDeckSize() {
        return deck.size();
    }
    
    // Is this deck empty
    public boolean isDeckEmpty() {
        return this.deck.isEmpty();
    }

    // All of the cards in this deck represented as a container
    private ArrayList<Card> deck;
}
