package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfiloDTO {
    private String nome, cognome, luogoNascita, residenza;
    private LocalDate dataDiNascita;
    private double altezza;
    private Sesso sesso;
    private Interessi interessi;
    private Colori_Capelli colori_capelli;
    private Colore_Occhi colore_occhi;

    private List<Hobby> hobbyList;
    private Long id;
    private String emailStudente;
    private FotoDTO fotoProfilo;
    private List<FotoDTO> listaFoto;

    private String numeroTelefono, nickInstagram;

    public ProfiloDTO(){    this.listaFoto = new ArrayList<FotoDTO>();
    }

    public ProfiloDTO(String nome, String cognome, String luogoNascita, String residenza, LocalDate dataDiNascita, double altezza, Sesso sesso, Interessi interessi, Colori_Capelli colori_capelli, Colore_Occhi colore_occhi, List<Hobby> hobbyList, FotoDTO fotoProfilo) {
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
        this.id = id;
        this.fotoProfilo = fotoProfilo;

        this.listaFoto = new ArrayList<FotoDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailStudente() {
        return emailStudente;
    }

    public void setEmailStudente(String emailStudente) {
        this.emailStudente = emailStudente;
    }

    public FotoDTO getFotoProfilo() {
        return fotoProfilo;
    }

    public void setFotoProfilo(FotoDTO fotoProfilo) {
        this.fotoProfilo = fotoProfilo;
    }

    public List<FotoDTO> getListaFoto() {
        return listaFoto;
    }

    public void setListaFoto(List<FotoDTO> listaFoto) {
        this.listaFoto = listaFoto;
    }

    public ProfiloDTO setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProfiloDTO setCognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public ProfiloDTO setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
        return this;
    }

    public ProfiloDTO setResidenza(String residenza) {
        this.residenza = residenza;
        return this;
    }

    public ProfiloDTO setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
        return this;
    }

    public ProfiloDTO setAltezza(double altezza) {
        this.altezza = altezza;
        return this;
    }

    public ProfiloDTO setSesso(Sesso sesso) {
        this.sesso = sesso;
        return this;
    }

    public ProfiloDTO setInteressi(Interessi interessi) {
        this.interessi = interessi;
        return this;
    }

    public ProfiloDTO setColori_capelli(Colori_Capelli colori_capelli) {
        this.colori_capelli = colori_capelli;
        return this;
    }

    public ProfiloDTO setColore_occhi(Colore_Occhi colore_occhi) {
        this.colore_occhi = colore_occhi;
        return this;
    }

    public void setHobbyList(List<Hobby> hobbyList) {
        this.hobbyList = hobbyList;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public double getAltezza() {
        return altezza;
    }

    public Sesso getSesso() {
        return sesso;
    }

    public Interessi getInteressi() {
        return interessi;
    }

    public Colori_Capelli getColori_capelli() {
        return colori_capelli;
    }

    public Colore_Occhi getColore_occhi() {
        return colore_occhi;
    }

    public List<Hobby> getHobbyList() {
        return hobbyList;
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

    @Override
    public String toString() {
        return "ProfiloDTO{" +
                "nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", luogoNascita='" + luogoNascita + '\'' +
                ", residenza='" + residenza + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", altezza=" + altezza +
                ", sesso=" + sesso +
                ", interessi=" + interessi +
                ", colori_capelli=" + colori_capelli +
                ", colore_occhi=" + colore_occhi +
                ", hobbyList=" + hobbyList +
                ", id=" + id +
                ", emailStudente='" + emailStudente + '\'' +
                ", fotoProfilo=" + fotoProfilo +
                ", listaFoto=" + listaFoto +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                ", nickInstagram='" + nickInstagram + '\'' +
                '}';
    }
}
