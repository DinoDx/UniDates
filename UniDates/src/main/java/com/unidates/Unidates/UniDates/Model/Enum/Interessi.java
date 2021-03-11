package com.unidates.Unidates.UniDates.Model.Enum;

public enum Interessi {
    UOMINI("UOMINI"),
    DONNE("DONNE"),
    ENTRAMBI("ENTRAMBI");

    private String interessi;
    private Interessi (String interessi){
        this.interessi = interessi;
    }

    @Override
    public String toString() {
        return interessi;
    }
}
