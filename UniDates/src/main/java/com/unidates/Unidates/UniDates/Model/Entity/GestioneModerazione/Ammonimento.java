package com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Service.GestioneModerazione.ModerazioneService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Ammonimenti")
public class Ammonimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studente_id")
    private Studente studente;

    @ManyToOne
    @JoinColumn(name = "moderatore_id")
    private Moderatore moderatore;

    @OneToOne
    private Foto foto;

    @NotNull
    private String motivazione;

    @NotNull
    @Size(min = 2, max = 1000)
    private String dettagli;

    public Ammonimento(){
    }

    public Ammonimento(String motivazione, String dettagli){
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

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
