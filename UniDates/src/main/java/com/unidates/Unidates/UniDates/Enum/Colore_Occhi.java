package com.unidates.Unidates.UniDates.Enum;

public enum Colore_Occhi {
    NERI("Neri"),
    CASTANI("Castani"),
    AMBRA("Ambra"),
    VERDI("Verdi"),
    GRIGI("Grigi"),
    AZZURRI("Azzurri"),
    ROSSI("Rossi");

    private String occhi;
    private Colore_Occhi(String occhi){
        this.occhi = occhi;
    }

    @Override
    public String toString() {
        return occhi;
    }
}
