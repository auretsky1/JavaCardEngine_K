package com.afdev.javacardengine;

import java.util.*;

public class Setting {
    // Default constructor
    public Setting(String settingLabel, SettingType setType) {
        this.settingLabel = settingLabel;
        this.settingType = setType;
    }
    
    // Constructor that sets all setting values in advance
    public Setting(String settingLabel, SettingType setType, 
            ArrayList<Object> settingValue) {
        this.settingLabel = settingLabel;
        this.settingType = setType;
        this.settingValues = settingValue;
    }
    
    // Enum that holds all the possible types of setting inputs
    public enum SettingType {
        NUMERIC, YESNO, LISTOFOPT, STATIC
    }
    
    // Get the settingLabel
    public String getSettingLabel() {
        return this.settingLabel;
    }
    
    // Get the settingType
    public SettingType getSettingType() {
        return this.settingType;
    }
    
    // Get the settingValue
    public ArrayList<Object> getSettingValue() {
        return this.settingValues;
    }
    
    // Get the next setting value and remove it from the list
    public Object getNextSettingValue() {
        return this.settingValues.remove(0);
    }
    
    // Set the settingValue
    public void addSettingValue(Object settingValue) {
        this.settingValues.add(settingValue);
    }
    
    // Add a method to this settingMethods collection
    public void addMethod(String method) {
        this.settingMethods.add(method);
    }
    
    // Get a specific method from this settings settingMethods
    public ArrayList<String> getSettingMethods() {
        return this.settingMethods;
    }
    
    // Variable that will hold the type of this setting
    private final SettingType settingType;
    
    // Variable that will hold the label for this setting
    private final String settingLabel;
    
    // Variable that will hold the values for this setting
    private ArrayList<Object> settingValues = new ArrayList<>();
    
    // Collection of methods from GameSettingInterface that will be run
    private final ArrayList<String> settingMethods = new ArrayList<>();
}
