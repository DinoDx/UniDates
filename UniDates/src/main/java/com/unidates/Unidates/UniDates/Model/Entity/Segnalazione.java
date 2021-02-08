package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.Motivazione;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Segnalazioni")
public class Segnalazione {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foto_id")
    private Foto foto;

    @ManyToOne
    @JoinColumn(name = "moderatore_id")
    private Moderatore moderatore;

    private Motivazione motivazione;

    private String dettagli;

    public Segnalazione(){
    }

    public Segnalazione(Motivazione motivazione, String dettagli){
        this.motivazione = motivazione;
        this.dettagli = dettagli;
    }

    public Moderatore getModeratore() {
        return moderatore;
    }

    public void setModeratore(Moderatore moderatore) {
        this.moderatore = moderatore;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segnalazione that = (Segnalazione) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
