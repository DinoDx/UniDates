package com.unidates.Unidates.UniDates.Model.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Sospensioni")
public class Sospensione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente_id")
    private Studente studente;

    private int durata;

    private String dettagli;

    public Sospensione(){

    }

    public Sospensione(int durata, String dettagli){
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sospensione that = (Sospensione) o;
        return Objects.equals(id, that.id);
    }

}
