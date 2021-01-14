package com.unidates.Unidates.UniDates.Enum;

public enum Tipo_Notifica {

    MATCH ("MATCH"),
    AMMONIMENTO ("AMMONIMENTO");

    private String tipo_noitica;

    private Tipo_Notifica(String tipo_noitica){
        this.tipo_noitica = tipo_noitica;
    }

    @Override
    public String toString() {
        return tipo_noitica;
    }
}
