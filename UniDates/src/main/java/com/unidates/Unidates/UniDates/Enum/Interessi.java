package com.unidates.Unidates.UniDates.Enum;

public enum Interessi {
    UOMINI("Uomini"),
    DONNE("Donne"),
    ENTRAMBI("Entrambi"),
    ALTRO("Altro");

    private String interessi;
    private Interessi (String interessi){
        this.interessi = interessi;
    }

    @Override
    public String toString() {
        return interessi;
    }
}
