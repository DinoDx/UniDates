package com.unidates.Unidates.UniDates.Model.Enum;

public enum Motivazione {
    NUDITA("NUDITA"),
    VIOLENZA("VIOLENZA"),
    CONTENUTO_OFFENSIVO("CONTENUTO_OFFENSIVO"),
    SPAM("SPAM"),
    CONETNUTO_NON_PERTINENTE("CONETNUTO_NON_PERTINENTE");

    private String motivazione;
    private Motivazione(String motivazione){
        this.motivazione = motivazione;
    }


    @Override
    public String toString() {
        return motivazione;
    }
}
