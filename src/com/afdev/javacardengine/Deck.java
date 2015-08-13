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
    
    // Get specific card and remove it from deck
    public Card getSpecificCardRemove(int deckPosition){
        return deck.remove(deckPosition);
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
    
    public void shuffle(){
        ArrayList <Card> copyDeck = new ArrayList<>(this.deck.size());
        int size = this.deck.size();
        for(int i = 0; i < size; i++){
            int index = rng.nextInt(this.deck.size());
            copyDeck.add(this.deck.remove(index));
        }
        for(Card c: copyDeck){
            this.deck.add(c);
        }       
    }

    // All of the cards in this deck represented as a container
    private ArrayList<Card> deck;
    
    // The random number generator that will be used for all cards
    protected static final Random rng = new Random(System.currentTimeMillis());
}
