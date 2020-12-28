package com.unidates.Unidates.UniDates.Enum;

public enum Sesso {
    UOMO("UOMO"),
    DONNA("DONNA"),
    ALTRO("ALTRO");

    private String sesso;
    private Sesso(String sesso){
        this.sesso = sesso;
    }

    @Override
    public String toString() {
        return sesso;
    }
}


