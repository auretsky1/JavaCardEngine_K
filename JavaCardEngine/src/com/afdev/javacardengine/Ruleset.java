package com.afdev.javacardengine;

import com.afdev.javacardengine.Setting.SettingType;
import java.util.*;
import java.lang.reflect.*;

public class Ruleset {
    // Default constructor
    public Ruleset() {
        /* These are the settings for the default game, see
         "Default Game Rules.txt" for more information. */
        
        // Initialize the methodMap, which is used to pass a collection of
        // method calls to the GameManager for each individual setting
        this.initializeAllMethodMaps();
  
        // Create all the settings for this game type
        this.createAllGameSettings();
        
        // Create the actions to be taken each turn
        this.createTurnGameActions();
    }
    
    // Accessor for the settings list
    public ArrayList<Setting> getSettings() {
        return this.settings;
    } 
    
    // Accessor for the turn actions list
    public ArrayList<GameAction> getTurnActions() {
        return this.turnActions;
    }
    
    // Method which initializes the method map for the game settings interface
    private void initializeMethodMapSettings() {
        // Create an unitialized collection of generic class objects to be
        // used for specifying the parameters of each method
        Class<?>[] parameters;
        
        // Then get all of the possible methods from the interface as
        // an array of methods
        Method[] listOfMethods = GameSettingInterface.class.getMethods();
        
        // Loop through every method in the interface and assign parameter types
        for (Method method : listOfMethods) {
            // Get the name of the method that will be added to the map
            String methodName = method.getName();
            
            // Get the parameters of the method that will add to the map
            parameters = method.getParameterTypes();
            
            // Add the key/value pair to this map
            this.methodMapSettings.put(methodName, parameters);
        }
    }
    
    // Method which initializes the method map for the game actions interface
    private void initializeMethodMapActions() {
        // Create an unitialized collection of generic class objects to be
        // used for specifying the parameters of each method
        Class<?>[] parameters;
        
        // Then get all of the possible methods from the interface as
        // an array of methods
        Method[] listOfMethods = GameActionInterface.class.getMethods();
        
        // Loop through every method in the interface and assign parameter types
        for (Method method : listOfMethods) {
            // Get the name of the method that will be added to the map
            String methodName = method.getName();
            
            // Get the parameters of the method that will add to the map
            parameters = method.getParameterTypes();
            
            // Add the key/value pair to this map
            this.methodMapGameActions.put(methodName, parameters);
        }
    }
    
    // Method which calls both init methods for interface maps
    private void initializeAllMethodMaps() {
        this.initializeMethodMapSettings();
        this.initializeMethodMapActions();
    }
    
    // Allow outside caller to get the MethodMapSettings values
    public Map<String, Class<?>[]> getMethodMapSettings() {
        return this.methodMapSettings;
    }
    
    // Allow outside caller to get the MethodMapSettings values
    public Map<String, Class<?>[]> getMethodMapActions() {
        return this.methodMapGameActions;
    }
    
    // Create all the default settings for this game type (default game) 
    private void createAllGameSettings() {
        // Create individual settings and add them to the settings collection
        // Begin by creating two players for the game
        Setting setting = this.createSetting("NULL", SettingType.STATIC, 2);
        this.addMethodsToSettings(setting, "addPlayers");
        this.settings.add(setting);
        
        // Tell each player to create a value slot for the points scoring
        setting = this.createSetting("NULL", SettingType.STATIC);
        this.addMethodsToSettings(setting, "allPlayersCreateValue");
        this.settings.add(setting);
        
        // Create a deck of 100 cards
        setting = this.createSetting("NULL", SettingType.STATIC, 100, 0);
        this.addMethodsToSettings(setting, "dealerCreateNewDeck", 
                "fillDealerDeck");
        this.settings.add(setting);
        
        // Distribute the deck to the players
        setting = this.createSetting("NULL", SettingType.STATIC, 0, 0);
        this.addMethodsToSettings(setting, "allPlayersCreateDeck", 
                "allPlayersCreateDeck", "distributeDeckToPlayers");
        this.settings.add(setting);
    }
    
    // Create all the game actions that will occur during a single turn
    private void createTurnGameActions() {
        // Create individual gameActions and add them to the turn collection
        // Begin by creating two players for the game
        GameAction gameAction = new GameAction(0, 1, 0, 0);
        this.addMethodsToGameAction(gameAction, "defaultGameComparison");
        this.turnActions.add(gameAction);   
        
        // Check if the game is over
        gameAction = new GameAction();
        this.addMethodsToGameAction(gameAction, "checkDefaultGameOver");
        this.turnActions.add(gameAction);
    }
    
    // Internal method for initializing a setting
    private Setting createSetting(String label, Setting.SettingType setType,
            Object ... values) {
        // Construct the setting with the specificed label/setType
        Setting setting = new Setting(label, setType);
        
        // Add all the passed in values
        for (Object value : values) {
            setting.addSettingValue(value);
        }
        
        // Return the new setting
        return setting;
    }
    
    // Internal method for adding interface method calls to settings
    private void addMethodsToSettings(Setting setting, String ... methods) {
        // Loop through all provided strings and add them as methods to setting
        for (String method : methods) {
            setting.addMethod(method);
        }
    }
    
    // Internal method for adding interface method calls to settings
    private void addMethodsToGameAction(GameAction gameAction, String ... methods) {
        // Loop through all provided strings and add them as methods to setting
        for (String method : methods) {
            gameAction.addMethod(method);
        }
    }    
    
    // A list of settings to be displayed by the transducer
    private final ArrayList<Setting> settings = new ArrayList<>();
    
    // A list of gameActions to be performed during each turn
    private final ArrayList<GameAction> turnActions = new ArrayList<>();
    
    // A map that maps methods in the GameSettingInterface to their strings
    private final Map<String, Class<?>[]> methodMapSettings = new HashMap<>();
    
    // A map that maps methods in the GameActionInterface to their strings
    private final Map<String, Class<?>[]> methodMapGameActions = new HashMap<>();
}