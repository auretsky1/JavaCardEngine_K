package com.afdev.javacardengine;

import java.util.*;

public class Dealer {
    // Default constructor
    public Dealer() {
        // Initialize the deck container
        this.decks = new ArrayList<>();
    }

    // Create a new deck for this dealer to own
    public void createNewDeck() {
        decks.add(new Deck());
    }
    
    // Add deck to dealers collection
    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }
    
    // Draw a card into the specified deck
    public boolean addCard(int deckPosition, Card card) {
        // Check if deck at deckPosition exists, if not return false
        if (this.decks.size() - 1 < deckPosition) {
            return false;
        }
        else {
            decks.get(deckPosition).addCard(card);
            return true;
        }
    }
    
    // Give a card from the deck to the caller
    public Card getCard(int deckPosition) {
        // Check if deck at deckPosition exists, if not return false
        if (this.decks.size() - 1 < deckPosition) {
            return null;
        }
        else if (this.decks.get(deckPosition).getDeckSize() < 0) {
            return null;
        }
        else {
            return this.decks.get(deckPosition).getTopCardRemove();
        }        
    }

    // Get the size of any deck the dealer owns
    public int getDeckSize(int deckPosition) {
        return this.decks.get(deckPosition).getDeckSize();
    }
    
    // Collection of decks that the dealer can deal from
    private ArrayList<Deck> decks;
}
