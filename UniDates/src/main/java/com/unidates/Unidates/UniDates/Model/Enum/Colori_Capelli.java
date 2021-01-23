package com.unidates.Unidates.UniDates.Model.Enum;

public enum Colori_Capelli {
    NERI("NERI"),
    BIONDI("BIONDI"),
    CASTANI("CASTANI"),
    AMBRA("AMBRA"),
    GRIGI("GRIGI"),
    ROSSI("ROSSI"),
    ALTRO("ALTRO");

    private String colore ;
    private Colori_Capelli(String colore){
        this.colore = colore;
    }

    @Override
    public String toString() {
        return colore;
    }
}
