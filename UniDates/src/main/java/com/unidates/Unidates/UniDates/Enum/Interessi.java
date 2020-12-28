package com.unidates.Unidates.UniDates.Enum;

public enum Interessi {
    UOMINI("UOMINI"),
    DONNE("DONNE"),
    ENTRAMBI("ENTRAMBI"),
    ALTRO("ALTRO");

    private String interessi;
    private Interessi (String interessi){
        this.interessi = interessi;
    }

    @Override
    public String toString() {
        return interessi;
    }
}
