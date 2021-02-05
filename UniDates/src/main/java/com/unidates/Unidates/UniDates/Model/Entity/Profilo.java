package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.*;

import javax.persistence.*;
import java.io.Serializable;

import java.util.*;

import java.time.LocalDate;


@Entity
@Table(name = "Profilo")
public class Profilo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "profilo", cascade = CascadeType.REMOVE)
    private Studente studente;

    @OneToMany(mappedBy = "profilo", cascade ={CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<Foto> listaFoto;


    private String nome, cognome, luogoNascita, residenza;
    private LocalDate dataDiNascita;
    private double altezza;
    private Sesso sesso;
    private Interessi interessi;
    private Colori_Capelli colori_capelli;
    private Colore_Occhi colore_occhi;

    private String numeroTelefono, nickInstagram;

    @ElementCollection
    private List<Hobby> hobbyList;


    public Profilo() {
        this.listaFoto = new ArrayList<Foto>();
    }


    public Profilo(String nome, String cognome, String luogoNascita, String residenza, LocalDate dataDiNascita, double altezza, Sesso sesso, Interessi interessi, Colori_Capelli colori_capelli, Colore_Occhi colore_occhi,Foto fotoProfilo, List<Hobby> hobbyList) {
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
        this.listaFoto = new ArrayList<Foto>();
        addFoto(fotoProfilo, true);
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

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public double getAltezza() {
        return altezza;
    }

    public void setAltezza(double altezza) {
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


    public void setId(Long id) { this.id = id; }

    public Studente getStudente() { return studente; }

    public void setStudente(Studente studente) { this.studente = studente; }


    public Foto getFotoProfilo() {
        for(Foto f : listaFoto)
            if(f.isFotoProfilo()) return f;
            return null;
    }

    public List<Foto> getListaFoto() {
        return listaFoto;
    }

    public void setListaFoto(List<Foto> listaFoto) {
        this.listaFoto = listaFoto;
    }

    public List<Hobby> getHobbyList() {
        return hobbyList;
    }

    public void setHobbyList(List<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public String getNickInstagram() {
        return nickInstagram;
    }

    public void setNickInstagram(String nickInstagram) {
        this.nickInstagram = nickInstagram;
    }

    public void addFoto(Foto foto, boolean isProfile) {
        foto.setFotoProfilo(isProfile);
        foto.setProfilo(this);
        listaFoto.add(foto);
    }
    public void removeFoto(Foto foto){
        listaFoto.remove(foto);
    }

}
