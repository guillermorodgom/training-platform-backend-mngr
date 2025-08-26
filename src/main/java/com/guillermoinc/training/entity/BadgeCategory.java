package com.guillermoinc.training.entity;

public enum BadgeCategory {
    COMPLETION("Completación"),
    EXCELLENCE("Excelencia"), 
    SPEED("Velocidad"),
    CONSISTENCY("Consistencia"),
    MILESTONE("Hito");
    
    private final String displayName;
    
    BadgeCategory(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}