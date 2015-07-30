package com.afdev.javacardengine;

public interface GameSettingInterface {
    // GameSettingInterface contains all of the actions that can be taken
    // by a setting when initializing any specific game type
    
    // Adds # of players
    void addPlayers(int numberOfPlayers);
    
    // Add a players
    void addPlayer();
    
    // Give player a deck
    void givePlayerDeck(int playerPosition, Deck deck);
    
    // Give dealer a deck
    void giveDealerDeck(Deck deck);
    
    // Distribute cards evenly from dealer to players
    void distributeDeckToPlayers(int deckPosition, int plDeckPosition);
    
    // Fill players deck with # cards
    void fillPlayerDeck(int numberOfCards, int playerPosition,
            int plDeckPosition);
    
    // Fill dealers deck with # cards
    void fillDealerDeck(int numberOfCards, int deckPosition);
    
    // Get a card from the dealer
    Card getCardFromDealer(int deckPosition);
    
    // Tell the dealer to create a new deck
    void dealerCreateNewDeck();
    
    // Tell the player to create a new deck
    void playerCreateNewDeck(int playerPosition);
    
    // Tell all players to create a new deck
    void allPlayersCreateDeck();
    
    // Tell all players to create a new points value
    void allPlayersCreateValue();
}
