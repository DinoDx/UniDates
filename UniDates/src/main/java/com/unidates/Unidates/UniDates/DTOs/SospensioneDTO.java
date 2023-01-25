package com.unidates.Unidates.UniDates.DTOs;

public class SospensioneDTO {
    private Long id;
    private String studente;
    private int durata;
    private String dettagli;

    public SospensioneDTO() {
    }

    public SospensioneDTO(int durata, String dettagli) {
        this.durata = durata;
        this.dettagli = dettagli;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudente() {
        return studente;
    }

    public void setStudente(String studente) {
        this.studente = studente;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }
}
