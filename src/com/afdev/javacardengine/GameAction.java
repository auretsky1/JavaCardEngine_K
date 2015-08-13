package com.afdev.javacardengine;

import java.util.*;

public class GameAction {
    // Default constructor
    public GameAction(Object ... values) {
        // Apply varags # of objects to gameActionValues
        for (Object value : values) {
            this.gameActionValues.add(value);
        }
        
        // Copy over the values to the values to be used by GM
        this.gameActionValuesCopy = (ArrayList<Object>) 
                this.gameActionValues.clone();
    }
    
    // Gettor for gameActionValues
    public ArrayList<Object> getActionValues() {
        return this.gameActionValues;
    }
    
    // Add a value to gameActionValues
    public void addActionValue(Object value) {
        this.gameActionValues.add(value);
    }
    
    // Add a return to gameActionValues
    public void addActionReturnValue(Object ... values) {
          for (Object value : values) {
              this.gameActionReturnValues.add(value);
          }
    }
    
    // Allow outside callers to clear the return values
    public void clearActionReturnValues() {
        this.gameActionReturnValues.clear();
    }
    
    // Set a specific gameActionValue
    public void setSpecificActionValue(int location, Object value) {
        this.gameActionValues.set(location, value);
    }
   
    // Get a specific gameActionValue
    public Object getSpecificActionValue(int location) {
        return this.gameActionValues.get(location);
    }
    
    // Get the next gameAction value and remove it from the list
    public Object getNextActionValue() {
        // Get the next value
        Object value = this.gameActionValuesCopy.remove(0);
        
        // If there are no more values copy over
        if (this.gameActionValuesCopy.isEmpty()) {
            this.gameActionValuesCopy = (ArrayList<Object>) 
                    this.gameActionValues.clone();
        }
        
        // Return the value that we got
        return value;
    }
    
    // Get all the return values for this game action
    public ArrayList<Object> getReturnValues() {
        return this.gameActionReturnValues;
    }
    
    // Add a method to this settingMethods collection
    public void addMethod(String method) {
        this.gameActionMethods.add(method);
    }
    
    // Get a specific method from this settings settingMethods
    public ArrayList<String> getGameActionMethods() {
        return this.gameActionMethods;
    }
    
    // Variable that will hold the values for this gameAction
    private final ArrayList<Object> gameActionValues = new ArrayList<>();
    
    // Copy of the gameActionValues that will be read from as turns go by
    private ArrayList<Object> gameActionValuesCopy;
    
    // Return values for this game action (to be given to the main/transudcer)
    private final ArrayList<Object> gameActionReturnValues = new ArrayList<>();
    
    // Collection of methods from GameSettingInterface that will be run
    private final ArrayList<String> gameActionMethods = new ArrayList<>();
}
