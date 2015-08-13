package com.afdev.javacardengine;

import java.util.*;

public class Card {
    // Default constructor
    public Card() {
        // Randomly set the card value for this card
        this.cardValue = rng.nextInt(2);
    }
    
    // Accessor for the card value
    public int getCardValue() {
        return this.cardValue;
    }
    
    // Settor for the card value
    public void setCardValue(int cardValue) {
        this.cardValue = cardValue;
    }
    
    // An integer value that determins if the base card is 0 or 1
    protected int cardValue;
 
    // The random number generator that will be used for all cards
    protected static final Random rng = new Random(System.currentTimeMillis());
}
