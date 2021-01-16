package com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;
import javassist.LoaderClassPath;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "Notifica")
public class Notifica implements Serializable, Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;


    private String emailToMatchWith;


    @OneToOne
    private Foto foto;

    private String testoNotifica;

    private Tipo_Notifica tipo_notifica;

    private LocalDate creationTime;

    public Notifica() {
    }

    public Notifica(Utente utente, String testoNotifica, Tipo_Notifica tipo_notifica, Foto foto) {
        this.foto = foto;
        this.utente = utente;
        this.testoNotifica = testoNotifica;
        this.tipo_notifica = tipo_notifica;
        this.creationTime = LocalDate.now();
  }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public String getEmailToMatchWith() {
        return emailToMatchWith;
    }

    public void setEmailToMatchWith(String toMatchWith) {
        this.emailToMatchWith = toMatchWith;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    public String getTestoNotifica() {
        return testoNotifica;
    }

    public void setTestoNotifica(String testoNotifica) {
        this.testoNotifica = testoNotifica;
    }

    public Tipo_Notifica getTipo_notifica() {
        return tipo_notifica;
    }

    public void setTipo_notifica(Tipo_Notifica tipo_notifica) {
        this.tipo_notifica = tipo_notifica;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDate creationTime) {
        this.creationTime = creationTime;
    }
}
