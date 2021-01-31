package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

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
    private Motivazione motivazione;

    @NotNull
    @Size(min = 2, max = 1000)
    private String dettagli;

    public Ammonimento(){
    }

    public Ammonimento(Motivazione motivazione, String dettagli){
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

    public Motivazione getMotivazione() {
        return motivazione;
    }

    public void setMotivazione(Motivazione motivazione) {
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


    @Override
    public boolean equals(Object o) {  //Funziona soltanto nel caso in cui gli ammonimenti facciano riferimento alla stessa foto
        if (this == o) return true;
        if (!(o instanceof Ammonimento)) return false;
        Ammonimento that = (Ammonimento) o;
        return that.getFoto().getId().equals(this.getFoto().getId());
    }

}
