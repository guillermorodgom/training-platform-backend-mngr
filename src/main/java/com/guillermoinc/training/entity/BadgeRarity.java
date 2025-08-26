package com.guillermoinc.training.entity;

public enum BadgeRarity {
    COMMON("Común"),
    RARE("Raro"),
    EPIC("Épico"),
    LEGENDARY("Legendario");
    
    private final String displayName;
    
    BadgeRarity(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}