package com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Notifica")
public class Notifica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    private String text;


    public Notifica() {
    }

    public Notifica( Utente utente, String text) {
        this.utente = utente;
        this.text = text;
    }

    public Notifica(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
}
