package com.example.TehnicaBG.Condition;

public enum Condition {
    NEW("Ново"),
    VERY_GOOD("Много добро"),
    USED("Използвано"),
    DEFECT("С дефект");

    private final String displayName;

    Condition(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
