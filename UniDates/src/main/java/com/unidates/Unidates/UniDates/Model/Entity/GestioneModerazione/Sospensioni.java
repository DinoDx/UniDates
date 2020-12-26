package com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;

import javax.persistence.*;

@Entity
@Table(name = "Sospensioni")
public class Sospensioni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente_id")
    private Studente studente;

    private int durata;

    private String dettagli;

    public Sospensioni(){

    }

    public Sospensioni(int durata, String dettagli){
        this.durata = durata;
        this.dettagli = dettagli;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
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
