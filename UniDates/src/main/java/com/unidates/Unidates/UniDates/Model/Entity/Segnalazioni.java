package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;

import javax.persistence.*;

@Entity
@Table(name = "Segnalazioni")
public class Segnalazioni {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foto_id")
    private Foto foto;

    private String motivazione;

    private String dettagli;

    public Segnalazioni(){
    }

    public Segnalazioni(String motivazione, String dettagli){

        this.motivazione = motivazione;
        this.dettagli = dettagli;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(String motivazione) {
        this.motivazione = motivazione;
    }

    public String getDettagli() {
        return dettagli;
    }

    public void setDettagli(String dettagli) {
        this.dettagli = dettagli;
    }
}
