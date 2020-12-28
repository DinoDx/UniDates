package com.unidates.Unidates.UniDates.Enum;

public enum Sesso {
    UOMO("Uomo"),
    DONNA("Donna"),
    ALTRO("Altro");

    private String sesso;
    private Sesso(String sesso){
        this.sesso = sesso;
    }

    @Override
    public String toString() {
        return sesso;
    }
}


