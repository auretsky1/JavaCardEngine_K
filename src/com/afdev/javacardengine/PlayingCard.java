
package com.afdev.javacardengine;
import java.util.*;
import com.afdev.javacardengine.Card.*;


public class PlayingCard extends Card {
    
    //Default constructor for making playing cards
    public PlayingCard(){
        setSuit();
        setRank();
    }
    
    //Constructor used for specifying a card to make by inputing the enum
    public PlayingCard(CardSuit suit, CardRank rank){
        this.cardSuit = suit;
        this.cardRank = rank;
        
    }
    
    //Constructor used for specifying a card to make by inputing the integer 
    //values
    public PlayingCard(int suit, int rank){
       int suitValue = suit;
       int rankValue = rank;       
       for(CardSuit c: CardSuit.values()){
           if(c.ordinal() == suitValue){
               this.cardSuit = c;
           }
       }
       for(CardRank c: CardRank.values()){
           if(c.ordinal() == rankValue){
               this.cardRank = c;
           }
       }
               
    }
    
    // Sets the suit of the card
    private void setSuit(){
        int suitValue = rng.nextInt(4);
        for(CardSuit c :CardSuit.values()){
            if(c.ordinal() == suitValue){
                this.cardSuit = c;
            }
        }
    }
    
   // Accessor for the cards suit 
    public CardSuit getSuit(){
        return this.cardSuit;
    }
    
    // sets the rank of the card
    private void setRank(){
        int rankValue = rng.nextInt(13);
        for(CardRank c :CardRank.values()){
            if(c.ordinal() == rankValue){
                this.cardRank = c;
            }
        }
    }
    
    // Accessor for the cards rank
    public CardRank getRank(){
        return this.cardRank;
    }
    
    // Enum that will hold the type of suits
    public enum CardSuit {
        Diamond, Heart, Club, Spade
    }  
    
    // Enum that will hold the type of suits
    public enum CardRank {
        Two, Three, Four, Five, Six, Seven, Eight, 
        Nine, Ten, Jack, Queen, King, Ace
    }
    
    // An enum that determines the suit of the card
    private CardSuit cardSuit;   
    
    //An enum that determines the rank of the card
    private CardRank cardRank;

}
