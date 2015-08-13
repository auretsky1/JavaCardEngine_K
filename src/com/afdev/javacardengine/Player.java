package com.afdev.javacardengine;

import java.util.*;

public class Player {
    // Collection of decks that is owned by this player
    private final ArrayList<Deck> decks = new ArrayList<>();
    
    // Collection of decks that represents the players hands
    private final ArrayList<Deck> hands = new ArrayList<>();
    
    // Collection of misc. values that effect the player
    private final ArrayList<Object> values = new ArrayList<>();
    
    // Add deck to players collection
    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }
    
    // Create a new deck for this player to own
    public void createNewDeck() {
        decks.add(new Deck());
    }
    
    // Accessor for player deck
    public Deck getDeck(int deckPosition){
        return decks.get(deckPosition);
    }
    // Check if a deck at specified position is empty
    public boolean isDeckEmpty(int deckPosition) {
        return this.decks.get(deckPosition).isDeckEmpty();
    }
    
    // Add a card to a specific deck
    public void addCard(int deckPosition, Card card) {
        this.decks.get(deckPosition).addCard(card);
    }
    
    // Get the top card of the specified deck
    public Card getTopCard(int deck) {
        return this.decks.get(deck).getTopCard();
    }
    
    // Get the top card of the specified deck and remove it
    public Card getTopCardRemove(int deck) {
        return this.decks.get(deck).getTopCardRemove();
    }
    
    // Get specific card of the specified deck and removie it
    public Card getSpecificCardRemove(int deck, int deckPosition){
        return this.decks.get(deck).getSpecificCardRemove(deckPosition);
    }
    
    // Create a value for the player to own
    public void createNewValue(Object value) {
        this.values.add(value);
    }
    
    // Get a value at specified position
    public Object getPlayerValue(int valuePosition) {
        return this.values.get(valuePosition);
    }
    
    // Increment a player value by an amount (assumes integer data)
    public void changePlayerValueNumeric(int valuePosition, Object value) {
        // Numerical representation of object in value location valuePosition
        Integer currentValue = (Integer) this.values.get(valuePosition);
        
        // Incrememnt by the provided value
        currentValue += (Integer) value;
        
        // Set the value to the new value
        this.values.set(valuePosition, currentValue);
    }
}
