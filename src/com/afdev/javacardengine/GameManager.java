package com.afdev.javacardengine;

import java.util.*;
import java.lang.reflect.*;

// GameManager class handles all other objects used by this library
public class GameManager implements GameSettingInterface, GameActionInterface {
    // Default constructor
    public GameManager() {
        // Initialize the dealer for this game
        this.dealer = new Dealer();
        
        // Initialize the player container for this game
        this.players = new ArrayList<>();
        
        // Initialize the turn location to 0
        this.turnActionLocation = 0;
        
        // Initialize the endGame flag
        this.isGameOver = false;
    }
    
    // This is called by the creator of the object to initialize a game
    public ArrayList<Setting> initializeGame(GameType gameType) {
        // Set the gameType equal to the value passed in by the creator
        this.gameType = gameType;
        
        // Create a ruleset for the game we are going to create
        switch (this.gameType) {
            // Creates an instance of the default game
            case DEFAULT:
                this.ruleset = new Ruleset();
                break;
                
            case WAR:
                this.ruleset = new WarRuleset();
                break;
                
            case GEMWAR:
                break;
                
            default:
                break;
        }
        
        // Set the method map for the settings
        this.methodMapSetting = this.ruleset.getMethodMapSettings();
        
        // Set the method map for the game actions
        this.methodMapGameActions = this.ruleset.getMethodMapActions();
        
        // Return all of the settings to the caller for processing
        return this.ruleset.getSettings();
    }
    
    // This is called to configure the settings decided by the user
    public GameAction initializeSettings() {
        // Loop through each setting and process it
        for (Setting setting : this.ruleset.getSettings()) {
            this.processSetting(setting);
        }
        
        // Now that the game is set up initialize the actions for each turn
        this.turnActions = this.ruleset.getTurnActions();
        
        // Return the first gameAction for preprocessing
        return this.turnActions.get(0);
    }
    
    // Run an action of the turn and return necessary values to the caller
    public GameAction runTurnAction() {
        // Is the game over? If so return
        if (this.isGameOver) {
            return this.currentAction;
        }
        
        // Get the next game action for this turn
        GameAction currentTurnAction = this.turnActions.get(turnActionLocation);
        
        // Clear out the return values for this gameAction
        currentTurnAction.clearActionReturnValues();
        
        // Set the currentAction (used for setting return values)
        this.currentAction = currentTurnAction;
        
        // Process the next action for this turn
        this.processGameAction(currentTurnAction);
        
        // Increment the turn action location or loop back to 0
        this.turnActionLocation++;
        
        // Check to make sure we haven't gone over the turn list size
        if (this.turnActionLocation >= this.turnActions.size()) {
            turnActionLocation = 0;
        }

       // Return any necessary values to the caller as a gameAction
       return currentTurnAction;
    }
    
    // Accessor for gameOver state
    public boolean isGameOver() {
        return this.isGameOver;
    }
    
    // Process a specific game setting
    private void processSetting(Setting setting) {
       // Get all of the methods to be called by this setting as strings
       ArrayList<String> methods = setting.getSettingMethods();
       
       // Set each method into an array to be run using the mapMethodSetting
       for (String methodName : methods) {
           try {
               // Get the specific method to run from this class 
               Method method = GameManager.class.getMethod(methodName, 
                        this.methodMapSetting.get(methodName));
               
               // Set an array to hold the arguments for this 
               Object[] arguments = 
                       new Object[method.getParameterTypes().length];
               
               // Loop through the values and add them to the arguments list
               for (int i = 0; i < arguments.length; i++) {
                   arguments[i] = setting.getNextSettingValue();
               }
               
               // Invoke the method with the arguments array
               method.invoke(this, arguments);
           }
           catch (NoSuchMethodException|IllegalAccessException|
                   InvocationTargetException e) {
                System.out.println(e.getCause());
           }
       }
    }
    
    // Process a specific game action during a turn
    private void processGameAction(GameAction gameAction) {
       // Get all of the methods to be called by this setting as strings
       ArrayList<String> methods = gameAction.getGameActionMethods();
       
       // Set each method into an array to be run using the mapMethodSetting
       for (String methodName : methods) {
           try {
               // Get the specific method to run from this class 
               Method method = GameManager.class.getMethod(methodName, 
                        this.methodMapGameActions.get(methodName));
               
               // Set an array to hold the arguments for this 
               Object[] arguments = 
                       new Object[method.getParameterTypes().length];
               
               // Loop through the values and add them to the arguments list
               for (int i = 0; i < arguments.length; i++) {
                   arguments[i] = gameAction.getNextActionValue();
               }
               
               // Invoke the method with the arguments array
               method.invoke(this, arguments);
           }
           catch (NoSuchMethodException|IllegalAccessException|
                   InvocationTargetException e) {
                System.out.println(e.getCause());
           }
       }
    }    
    
    // Enum that will hold the type of game to create
    // This can be extended by creating new sub-classes of Ruleset
    public enum GameType {
        DEFAULT, WAR, GEMWAR
    }
    
    // Holds the current game type that is being handled by the GameManager
    private GameType gameType;
    
    // Hold the ruleset for the current game we are playing
    private Ruleset ruleset;
    
    // The dealer that will handle shuffling and other related tasks
    private final Dealer dealer;
    
    // Container that will hold all the players for this game
    private final ArrayList<Player> players;
    
    // Map of setting method names (as strings) to their parameter types
    private Map<String, Class<?>[]> methodMapSetting; 
    
    // Map of game action method names (as strings) to their parameter types
    private Map<String, Class<?>[]> methodMapGameActions;
    
    // Numerical value that holds the current action to do in the turn list
    private int turnActionLocation;
    
    // Container that contains all game actions to be taken during a turn
    private ArrayList<GameAction> turnActions;
    
    // Pointer to the current gameAction being processed
    private GameAction currentAction;
    
    // Flag which determins if the game is finished
    private boolean isGameOver;
        
    /* BELOW HERE IS IMPLEMENTATION OF GAMESETTINGINTERFACE */
    // Adds # of players
    @Override
    public void addPlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; i++) {
            this.addPlayer();
        }
    }
    
    // Add a players
    @Override
    public void addPlayer() {
        this.players.add(new Player());
        System.out.println("Added player");
    }
    
    // Give player a deck
    @Override
    public void givePlayerDeck(int playerPosition, Deck deck) {
        this.players.get(playerPosition).addDeck(deck);
    }
    
    // Give players a deck
    @Override
    public void giveDealerDeck(Deck deck) {
        this.dealer.addDeck(deck);
    }
    
    // Distribute cards evenly from dealer to players
    @Override
    public void distributeDeckToPlayers(int deckPosition, int plDeckPosition) {        
        // The position of the current player to be dealt a card
        int playerPosition  = 0;
        
        // Get the size of the dealers deck we are distributing
        int dealerDeckSize = this.dealer.getDeckSize(deckPosition);

        // Go through entire deck and deal out each card to the player
        for (int i = 0; i < dealerDeckSize; i++) {
            // Deal a single card to the current player
            this.players.get(playerPosition).addCard(plDeckPosition, 
                    getCardFromDealer(deckPosition));
            
            // Increment the position to the next player
            playerPosition++;
            
            // Check if we've rolled over and reset to 0
            if (playerPosition >= this.players.size()) {
                playerPosition = 0;
            }
        }
    }
    
    // Fill players deck with # cards
    @Override
    public void fillPlayerDeck(int numberOfCards, int playerPosition,
            int plDeckPosition) {
        for (int i = 0; i < numberOfCards; i++) {
            this.players.get(playerPosition).addCard(plDeckPosition, 
                    new Card());
        }
    }
    
    // Fill dealers deck with # cards
    @Override
    public void fillDealerDeck(int numberOfCards, int deckPosition) {
        for (int i = 0; i < numberOfCards; i++) {
            this.dealer.addCard(deckPosition, new Card());
        }
        System.out.println("Deck filled");
    }
    
    // Fill dealer deck with playing cards
    @Override
    public void fillDealerDeckPlayingCards(int deckPosition){

    for(int i = 0; i < 4; i ++){
            for(int j = 0; j < 13; j ++){
               this.dealer.addCard(deckPosition, new PlayingCard(i,j));                     
            }
        }
    System.out.println("Deck Filled");
    }
    // Get a card from the dealer
    @Override
    public Card getCardFromDealer(int deckPosition) {
        return this.dealer.getCard(deckPosition);
    }
    
    // Tell the dealer to create a new deck
    @Override
    public void dealerCreateNewDeck() {
        this.dealer.createNewDeck();
        System.out.println("Dealer deck created");
    }
    
    // Tel the dealer to shuffle all his decks
    @Override
    public void dealerShuffleDeck(){
        this.dealer.shuffleDecks();
    }
    
    // Tell the player to create a new deck
    @Override
    public void playerCreateNewDeck(int playerPosition) {
        this.players.get(playerPosition).createNewDeck();
        System.out.println("Player " + playerPosition + " created new deck.");
    }
    
    // Tell all players to create a new deck
    @Override
    public void allPlayersCreateDeck() {
        // Loop through all players and call createNewDeck
        for (int i = 0; i < this.players.size(); i++) {
            this.playerCreateNewDeck(i);
        }
    }

    // Tell all players to create a new points value
    @Override
    public void allPlayersCreateValue() {
        // Loop through each player and create a new points value
        for (Player player : this.players) {
            player.createNewValue(0);
        }
    }
    /* END OF IMPLEMENTATION OF GAMESETTINGINTERFACE */
    
    
    /* BELOW HERE IS IMPLEMENTATION OF GAMEACTIONINTERFACE */
    // Deal the top card of a dealer's deck to a specified player
    @Override
    public void dealCardToPlayer(int dealerDeckPosition, int player, 
            int plDeckPosition) {
        // Get the card from the dealer
        Card card = this.dealer.getCard(dealerDeckPosition);
        
        // Give the card to the player
        this.players.get(player).addCard(plDeckPosition, card);
    }
    
    // Compare two cards to determine which one is of a higher numerical value
    @Override
    public Card compareCards(Card cardOne, Card cardTwo) {
        if (cardOne.getCardValue() > cardTwo.getCardValue()) {
            return cardOne;
        }
        else if (cardTwo.getCardValue() > cardOne.getCardValue()) {
            return cardTwo;
        }
        else {
            return null;
        }
    }
    
    // Deal a card from a single player to the gameManager for use in any way
    @Override
    public Card getPlayerCard(int player, int plDeckPosition) {
        // Get the top card of the specified player deck
        return this.players.get(player).getTopCardRemove(plDeckPosition);
    }
    
    // Give player a card into any of their decks
    @Override
    public void giveCardToPlayer(Card card, int player, int plDeckPosition) {
        this.players.get(player).addCard(plDeckPosition, card);
    }
    
    // Give player's any number of points (one player at a time)
    @Override
    public void givePlayerPoints(int player, int plValuePosition, int points) {
        // Incrememnt player values by the points passed in
        this.players.get(player).
                changePlayerValueNumeric(plValuePosition, points);
    }
    
    // Get a card from each player and compare them give a point to higher card
    @Override
    public void defaultGameComparison(int playerOne, int playerTwo, 
            int plDeckPosition, int valuePosition) {
        // Get player one's card
        Card plOneCard = this.getPlayerCard(playerOne, plDeckPosition);
        Card plTwoCard = this.getPlayerCard(playerTwo, plDeckPosition);
        Card higherCard = this.compareCards(plOneCard, plTwoCard);
        
        // See who owned that card and distribute points
        if (higherCard == plOneCard) {
            this.givePlayerPoints(playerOne, valuePosition, 1);
        }
        else if (higherCard == plTwoCard) {
            this.givePlayerPoints(playerTwo, valuePosition, 1);
        }
        
        // Send the cards to the players discard
        this.giveCardToPlayer(plOneCard, playerOne, plDeckPosition+1);
        this.giveCardToPlayer(plTwoCard, playerTwo, plDeckPosition+1);
        
        // Add return values to current game action
        this.currentAction.addActionReturnValue(plOneCard, 
                plTwoCard);
    }
      
    // Check if all players decks are empty and return true/false
    @Override
    public boolean checkAllDecksEmpty(int plDeckPosition) {
        // Are all the decks empty
        boolean flag = true;
        
        // Loop through all decks at deck position for each player
        for (Player player : this.players) {
            if (!player.isDeckEmpty(plDeckPosition)) {
                flag = false;
            }
        }
        
        // Return the value
        return flag;
    }
    
    // Called when default game ends
    @Override
    public void checkDefaultGameOver() {
        if (this.checkAllDecksEmpty(0)) {
            // Set gameOver flag to true
            this.isGameOver = true;
            
            // Set the return values for this game end state
            this.currentAction.addActionReturnValue(this.players.get(0),
                    this.players.get(1));
        }
    }
     // Called when war game ends
    
    //SPECIFIC GAMEACTIONs for WAR RULE SET
    
    // Compare two playing cards to determine which one is of a higher value
    @Override
    public PlayingCard comparePlayingCards(PlayingCard cardOne, PlayingCard cardTwo){
        if(cardOne.getRank().ordinal() > cardTwo.getRank().ordinal()){
            return cardOne;
        }
        else if(cardTwo.getRank().ordinal() > cardOne.getRank().ordinal()){
            return cardTwo;
        }
        else {
            return null;
        }
    }
    
    // Get a card from each player and compare them give a card to player
    // with higher card
    @Override
    public void warGameComparison(int playerOne, int playerTwo, 
            int plDeckPosition) {
        
        // Checks to see if main playing deck is empty
        // if it is, it fills main playing deck with cards from discard pile
        if(this.checkPlayerPlayingDeck(plDeckPosition, playerOne)){
            this.transferFromDiscard(plDeckPosition, playerOne);
        }
        if(this.checkPlayerPlayingDeck(plDeckPosition, playerTwo)){
            this.transferFromDiscard(plDeckPosition, playerTwo);
        }
        
        //Get player one and player twos card and compares to see which one is larger
        PlayingCard plOneCard = (PlayingCard) this.getPlayerCard(playerOne,
                plDeckPosition);
        PlayingCard plTwoCard = (PlayingCard) this.getPlayerCard(playerTwo, 
                plDeckPosition);
        PlayingCard higherCard = (PlayingCard) this.comparePlayingCards(plOneCard,
                plTwoCard);
        
        // See who owned that card and distribute cards accordingly or initialize
        // the war flag
        if (higherCard == plOneCard) {
         this.giveCardToPlayer(plTwoCard, playerOne, plDeckPosition+1);
         this.giveCardToPlayer(plOneCard, playerOne, plDeckPosition+1);
         // Add return values to current game action
         this.currentAction.addActionReturnValue(plOneCard, 
               plTwoCard);
        }
        else if (higherCard == plTwoCard) {
         this.giveCardToPlayer(plTwoCard, playerTwo, plDeckPosition+1);
         this.giveCardToPlayer(plOneCard, playerTwo, plDeckPosition+1); 
         // Add return values to current game action
         this.currentAction.addActionReturnValue(plOneCard, 
               plTwoCard);
        }
        else{
            this.war(playerOne, playerTwo, plDeckPosition, plOneCard, plTwoCard);
        }
                

             
    }
    
    // called when both player cards equal each other
    @Override
    public void war(int playerOne, int playerTwo, int plDeckPosition, 
            PlayingCard plOne, PlayingCard plTwo){
               
           //check to see if any player has run out of cards
           if(this.checkOnePlayerDecksEmpty(plDeckPosition)){
               
               // if a player has run out of cards have the player who has cards
               // win war and give them the cards
               if(!this.checkPlayerPlayingDeck(plDeckPosition, playerTwo)){
                   this.giveCardToPlayer(plTwo, playerTwo, plDeckPosition+1);
                   this.giveCardToPlayer(plOne, playerTwo, plDeckPosition+1);
               }
               else{
                   this.giveCardToPlayer(plTwo, playerOne, plDeckPosition+1);
                   this.giveCardToPlayer(plOne, playerOne, plDeckPosition+1);
               }
               this.currentAction.addActionReturnValue(plOne, plTwo);
           }

           else{
           
           // set a war flag to true, and add it to the action return values
           boolean hasWarOccured = true;    
           this.currentAction.addActionReturnValue(plOne, plTwo, hasWarOccured);
           
           // initialize variables that will hold the playing cards that will
           // be inserted into an array   
           PlayingCard plOneCard;
           PlayingCard plTwoCard;
           
           // create an array of PlayingCards that will hold the cards
           // that will be won by the winner of the war and array of cards to return
           // to acction return values
           ArrayList<PlayingCard> returnOneCards = new ArrayList<>();
           ArrayList<PlayingCard> returnTwoCards = new ArrayList<>();
           ArrayList<PlayingCard> plOneCards = new ArrayList<>();
           ArrayList<PlayingCard> plTwoCards = new ArrayList<>();
           
           // add the cards that triggered the war to the array of cards that
           // will be given to the winner of the war
           plOneCards.add(plOne);           
           plTwoCards.add(plTwo);          
          
           // transfer cards from discard to deck if discard pile has cards
           if(!this.checkPlayerPlayingDeck(plDeckPosition+1, 
                           playerOne)){
               this.transferFromDiscard(plDeckPosition, playerOne);
           }
           if(!this.checkPlayerPlayingDeck(plDeckPosition+1, 
                           playerTwo)){
               this.transferFromDiscard(plDeckPosition, playerTwo);
           } 
           
           // initialize length of war
           int lengthOfWar = 4;
           // add cards used in war into the arraylist of cards to be returned
           // to winner, and to the action return values
           for(int i = 0; i < lengthOfWar; i++){
               plOneCard = (PlayingCard)this.getPlayerCard(playerOne,
                       plDeckPosition);
               plTwoCard = (PlayingCard)this.getPlayerCard(playerTwo,
                       plDeckPosition);
               returnOneCards.add(plOneCard);
               returnTwoCards.add(plTwoCard);
               plOneCards.add(plOneCard);
               plTwoCards.add(plTwoCard);
               if(this.checkOnePlayerDecksEmpty(plDeckPosition)){
                   this.currentAction.addActionReturnValue(returnOneCards, 
                           returnTwoCards);
                   break;
               }
               if(i == lengthOfWar - 1){
                   if(this.comparePlayingCards(returnOneCards.get(
                           returnOneCards.size() - 1),
                           returnTwoCards.get(returnTwoCards.size() - 1)) 
                           == null){
                       this.currentAction.addActionReturnValue(returnOneCards,
                               returnTwoCards, hasWarOccured);
                       returnOneCards.clear();
                       returnTwoCards.clear();
                       lengthOfWar += 3;
                   }
                   else{
                       this.currentAction.addActionReturnValue(returnOneCards,
                               returnTwoCards);
                   }
               }
           } 

           // Compare the two player cards
           PlayingCard higherCard =  this.comparePlayingCards(
                           plOneCards.get(plOneCards.size() -1),
                           plTwoCards.get(plTwoCards.size() -1));
           
         // See who owned that card and distribute cards accordingly or initialize
        if (higherCard == plOneCards.get(plOneCards.size() -1)) {
            for(PlayingCard p : plTwoCards){
                this.giveCardToPlayer(p, playerOne, plDeckPosition+1);                
            }
            for(PlayingCard p : plOneCards){
                this.giveCardToPlayer(p, playerOne, plDeckPosition+1);                
            }
        }
        else if (higherCard == plTwoCards.get(plTwoCards.size() -1)) {
            for(PlayingCard p : plOneCards){
                this.giveCardToPlayer(p, playerTwo, plDeckPosition+1);                
            }
            for(PlayingCard p : plTwoCards){
                this.giveCardToPlayer(p, playerTwo, plDeckPosition+1);                
            }
        }
        else{
            if(!this.checkPlayerPlayingDeck(plDeckPosition, playerTwo)){
                for(PlayingCard p : plOneCards){
                    this.giveCardToPlayer(p, playerTwo, plDeckPosition+1);                
                }
                for(PlayingCard p : plTwoCards){
                    this.giveCardToPlayer(p, playerTwo, plDeckPosition+1);                
                }
            }
            else{
                for(PlayingCard p : plTwoCards){
                    this.giveCardToPlayer(p, playerOne, plDeckPosition+1);                
                 }
                for(PlayingCard p : plOneCards){
                    this.giveCardToPlayer(p, playerOne, plDeckPosition+1);                
                }
            }
        }
        }
        }
   
    //Check if specific player deck is empty
    @Override
    public boolean checkPlayerPlayingDeck(int plDeckPosition, int player){
        // is current deck empty
        boolean flag = true;

        if(!this.players.get(player).isDeckEmpty(plDeckPosition)){
                flag = false;
            }
        return flag;
    }
        
    //transfer discard deck to playing deck
    @Override
    public void transferFromDiscard(int plDeckPosition, int player){
        PlayingCard card;
        for(int i = 0; i < 
                this.players.get(player).getDeck(plDeckPosition+1).getDeckSize();
                i++){
            card = (PlayingCard) this.getPlayerCard(player, plDeckPosition+1);
            this.giveCardToPlayer(card, player, plDeckPosition);
        }                               
    }
    
     // Check if a player has both decks empty and return true/false
    @Override
    public boolean checkOnePlayerDecksEmpty(int plDeckPosition){
        // Is there one player with two empty decks
        boolean flag = false;
        
        //Loop through all decks at deck position for each player
        for(Player player : this.players){
            if(player.isDeckEmpty(plDeckPosition) && 
                    player.isDeckEmpty(plDeckPosition+1)){
                flag = true;
            }
        }
        return flag;
    }
    
    // Called when war game is over
    @Override
    public void checkWarGameOver(){
        if(this.checkOnePlayerDecksEmpty(0)){
            // Set gameOver flag to true
            this.isGameOver = true;            
           
        }
        
    }
        
    /* END OF IMPLEMENTATION OF GAMEACTIONINTERFACE */
      
}

    

