package com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo;

import com.unidates.Unidates.UniDates.Enum.*;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Utente;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "Profilo")
public class Profilo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "profilo", cascade = CascadeType.REMOVE)
    private Studente studente;

    @OneToMany(mappedBy = "profilo", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Collection<Foto> listaFoto;

    private String nome, cognome, luogoNascita, residenza;
    private Date dataDiNascita;
    private int altezza;
    private Sesso sesso;
    private Interessi interessi;
    private Colori_Capelli colori_capelli;
    private Colore_Occhi colore_occhi;

    @ElementCollection
    private Collection<Hobby> hobbyList;


    public Profilo() {

    }

    public Profilo(String nome, String cognome, String luogoNascita, String residenza, Date dataDiNascita, int altezza, Sesso sesso, Interessi interessi, Colori_Capelli colori_capelli, Colore_Occhi colore_occhi, Collection<Hobby> hobbyList) {
        this.nome = nome;
        this.cognome = cognome;
        this.luogoNascita = luogoNascita;
        this.residenza = residenza;
        this.dataDiNascita = dataDiNascita;
        this.altezza = altezza;
        this.sesso = sesso;
        this.interessi = interessi;
        this.colori_capelli = colori_capelli;
        this.colore_occhi = colore_occhi;
        this.hobbyList = hobbyList;
    }

    public Long getId() { return id; }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public void setResidenza(String residenza) {
        this.residenza = residenza;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public int getAltezza() {
        return altezza;
    }

    public void setAltezza(int altezza) {
        this.altezza = altezza;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public void setSesso(Sesso sesso) {
        this.sesso = sesso;
    }

    public Interessi getInteressi() {
        return interessi;
    }

    public void setInteressi(Interessi interessi) {
        this.interessi = interessi;
    }

    public Colori_Capelli getColori_capelli() {
        return colori_capelli;
    }

    public void setColori_capelli(Colori_Capelli colori_capelli) {
        this.colori_capelli = colori_capelli;
    }

    public Colore_Occhi getColore_occhi() {
        return colore_occhi;
    }

    public void setColore_occhi(Colore_Occhi colore_occhi) {
        this.colore_occhi = colore_occhi;
    }

    public Collection<Hobby> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(Collection<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public void setId(Long id) { this.id = id; }

    public Studente getStudente() { return studente; }

    public void setStudente(Studente studente) { this.studente = studente; }

    public Collection<Foto> getListaFoto() { return listaFoto; }

    public void setListaFoto(Collection<Foto> listaFoto) { this.listaFoto = listaFoto; }
}
