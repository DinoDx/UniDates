package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;

import javax.persistence.*;

@Entity
@Table(name = "Ammonimenti")
public class Ammonimenti {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente_id")
    private Studente studente;

    @ManyToOne
    @JoinColumn(name = "moderatore_id")
    private Moderatore moderatore;

    private String usernameStudente;
    private String motivazione;
    private String dettagli;

    public Ammonimenti(){
    }

    public Ammonimenti(String motivazione, String dettagli){

        this.motivazione = motivazione;
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

    public Moderatore getModeratore() {
        return moderatore;
    }

    public void setModeratore(Moderatore moderatore) {
        this.moderatore = moderatore;
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

    public String getUsernameStudente() {
        return usernameStudente;
    }

    public void setUsernameStudente(String usernameStudente) {
        this.usernameStudente = usernameStudente;
    }
}
