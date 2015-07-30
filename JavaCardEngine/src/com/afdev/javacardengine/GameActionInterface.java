package com.afdev.javacardengine;

public interface GameActionInterface {
    // GameActionInterface contains all of the actions that can be taken
    // by a gameAction when playing a specific turn of any game type
    
    // Deal the top card of a dealer's deck to a specified player
    void dealCardToPlayer(int dealerDeckPosition, int player, int plDeckPosition);
    
    // Compare two cards to determine which one is of a higher numerical value
    Card compareCards(Card cardOne, Card cardTwo);
    
    // Deal a card from a single player to the gameManager for use in any way
    Card getPlayerCard(int player, int plDeckPosition);
    
    // Give player a card into any of their decks
    void giveCardToPlayer(Card card, int player, int plDeckPosition);
    
    // Give player's any number of points (one player at a time)
    void givePlayerPoints(int player, int plValuePosition, int points);
    
    // Get a card from each player and compare them give a point to higher card
    void defaultGameComparison(int playerOne, int playerTwo, 
            int plDeckPosition, int valuePosition);
    
    // Check if all players decks are empty and return true/false
    boolean checkAllDecksEmpty(int plDeckPosition);
    
    // Called when default game ends
    void checkDefaultGameOver();
}
