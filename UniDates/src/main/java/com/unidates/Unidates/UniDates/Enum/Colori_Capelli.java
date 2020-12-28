package com.unidates.Unidates.UniDates.Enum;

public enum Colori_Capelli {
    NERI("Neri"),
    BIONDI("Biondi"),
    CASTANI("Castani"),
    AMBRA("Ambra"),
    GRIGI("Grigi"),
    ROSSI("Rossi"),
    ALTRO("Altro");

    private String colore ;
    private Colori_Capelli(String colore){
        this.colore = colore;
    }

    @Override
    public String toString() {
        return colore;
    }
}
