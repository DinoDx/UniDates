package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Enum.*;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Entity

public class Profilo {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private Utente utente;

    private String nome, cognome, luogoNascita, residenza;
    private Date dataDiNascita;
    private int altezza;
    private Sesso sesso;
    private Interessi interessi;
    private Colori_Capelli colori_capelli;
    private Colore_Occhi colore_occhi;

    private Hobby hobbyList;


    public Profilo(){

    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

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

    public Hobby getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(Hobby hobbyList) {
        this.hobbyList = hobbyList;
    }
}
