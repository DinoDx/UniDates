package com.unidates.Unidates.UniDates.Enum;

public enum Colore_Occhi {
    NERI("NERI"),
    CASTANI("CASTANI"),
    AMBRA("AMBRA"),
    VERDI("VERDI"),
    GRIGI("GRIGI"),
    AZZURRI("AZZURRI"),
    ROSSI("ROSSI");

    private String occhi;
    private Colore_Occhi(String occhi){
        this.occhi = occhi;
    }

    @Override
    public String toString() {
        return occhi;
    }
}
