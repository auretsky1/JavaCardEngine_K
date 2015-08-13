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
    
    //SPECIFIC GAMEACTIONS for WAR RULESET  
    
    // Compare two playing cards to determine which one is of a higher value
    PlayingCard comparePlayingCards(PlayingCard cardOne, PlayingCard cardTwo);
    
    
    // Get a card from each player and compare them give cards to player
    // with higher card
    void warGameComparison(int playerOne, int playerTwo, 
            int plDeckPosition);
    
    // called when both player cards equal each other
    void war(int playerOne, int playerTwo, int plDeckPosition,
            PlayingCard plOne, PlayingCard plTwo);
    
        
    //Check if player playing deck is empty
    boolean checkOnePlayerDecksEmpty(int plDeckPosition);
    
    //transfer discard deck to playing deck
    void transferFromDiscard(int plDeckPosition, int player);
    
    // Check if a player has both decks empty and return true/false
    boolean checkPlayerPlayingDeck(int plDeckPosition, int player);
    
    // Called when war game ends
    void checkWarGameOver();
    
}
