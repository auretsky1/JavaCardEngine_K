
package com.afdev.javacardengine;
import com.afdev.javacardengine.Setting.SettingType;
import com.afdev.javacardengine.Ruleset.*;
import java.util.*;
import java.lang.reflect.*;


public class WarRuleset extends Ruleset {
    
    public WarRuleset(){
        super();
    }
    
    // Create all the default settings for this game type (default game) 
    @Override
    protected void createAllGameSettings() {
        // Create individual settings and add them to the settings collection
        // Begin by creating two players for the game
        Setting setting = this.createSetting("NULL", SettingType.STATIC, 2);
        this.addMethodsToSettings(setting, "addPlayers");
        this.settings.add(setting);       
               
       // Create deck of playing cards
        setting = this.createSetting("NULL", SettingType.STATIC, 0);
        this.addMethodsToSettings(setting, "dealerCreateNewDeck", 
                "fillDealerDeckPlayingCards", "dealerShuffleDeck");
        this.settings.add(setting);
        
        // Distribute the deck to the players
        setting = this.createSetting("NULL", SettingType.STATIC, 0, 0);
        this.addMethodsToSettings(setting, "allPlayersCreateDeck", 
                "allPlayersCreateDeck", "distributeDeckToPlayers");
        this.settings.add(setting);
        
        
    }
    
    // Create all the game actions that will occur during a single turn
    @Override
    protected void createTurnGameActions() {
        // Create individual gameActions and add them to the turn collection
        // Begin by creating two players for the game
        GameAction gameAction = new GameAction(0, 1, 0);
        this.addMethodsToGameAction(gameAction, "warGameComparison");
            this.turnActions.add(gameAction); 
            
        
        // Check if the game is over
        gameAction = new GameAction();
        this.addMethodsToGameAction(gameAction, "checkWarGameOver");
        this.turnActions.add(gameAction);
        
    }
    
}
