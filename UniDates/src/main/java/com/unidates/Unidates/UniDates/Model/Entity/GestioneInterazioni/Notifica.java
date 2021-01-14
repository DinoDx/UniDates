package com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni;

import com.unidates.Unidates.UniDates.Enum.Tipo_Notifica;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
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

    @OneToOne
    private Foto foto;

    private String text;

    private Tipo_Notifica tipo_notifica;

    public Notifica() {
    }

    public Notifica(Utente utente, String text , Tipo_Notifica tipo_notifica, Foto foto) {
        this.foto = foto;
        this.utente = utente;
        this.text = text;
        this.tipo_notifica = tipo_notifica;
    }

    public Notifica(String text) {
        this.text = text;
    }

    public Foto getFoto(){ return foto; }

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

    public Tipo_Notifica getTipo_notifica() {
        return tipo_notifica;
    }

    public void setTipo_notifica(Tipo_Notifica tipo_notifica) {
        this.tipo_notifica = tipo_notifica;
    }
}
