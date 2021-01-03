package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;


import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneInterazioni.Match;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Studente extends Utente{


    private boolean isBanned;
    private boolean isActive;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "profilo_id", referencedColumnName = "id")
    private Profilo profilo;



    @ManyToMany
    private List<Studente> listaBloccati;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.REMOVE)
    private List<Sospensione> listSospensione;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.REMOVE)
    private List<Ammonimento> listAmmonimento;

    @OneToMany(mappedBy = "studente1", cascade = CascadeType.REMOVE)
    private List<Match> listMatch;

    @OneToMany(mappedBy = "studente2", cascade = CascadeType.REMOVE)
    private List<Match> listMatchRicevuti;

    public Studente() {
    }

    public Studente(String email, String password, Profilo profilo) {
        super(email, password, Ruolo.STUDENTE);
        this.isActive = false;
        this.isBanned = false;
        this.profilo = profilo;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public Profilo getProfilo() {
        return profilo;
    }

    public void setProfilo(Profilo profilo) {
        this.profilo = profilo;
    }

    public List<Studente> getListaBloccati() {
        return listaBloccati;
    }

    public void setListaBloccati(List<Studente> listaBloccati) {
        this.listaBloccati = listaBloccati;
    }

    public List<Sospensione> getListSospensioni() {
        return listSospensione;
    }

    public void setListSospensioni(List<Sospensione> listSospensione) {
        this.listSospensione = listSospensione;
    }

    public List<Ammonimento> getListAmmonimenti() {
        return listAmmonimento;
    }

    public void setListAmmonimenti(List<Ammonimento> listAmmonimento) {
        this.listAmmonimento = listAmmonimento;
    }

    public List<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(List<Match> listMatch) {
        this.listMatch = listMatch;
    }



    public List<Match> getListMatchRicevuti() {
        return listMatchRicevuti;
    }

    public void setListMatchRicevuti(List<Match> listMatchRicevuti) {
        this.listMatchRicevuti = listMatchRicevuti;
    }

    public void addBloccato(Studente studente){
        listaBloccati.add(studente);
    }

    public void addSospensione(Sospensione sospensione){
        listSospensione.add(sospensione);
    }

    public void addAmmonimento(Ammonimento ammonimento){
        listAmmonimento.add(ammonimento);
    }

    public void addMatch(Match match){
        listMatch.add(match);
    }

    public void addMatchRicevuto(Match match){
        listMatchRicevuti.add(match);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return super.toString() + "Studente{" +
                "isBanned=" + isBanned +
                ", profilo=" + profilo +
                ", listaBloccati=" + listaBloccati +
                ", listSospensioni=" + listSospensione +
                ", listAmmonimenti=" + listAmmonimento +
                '}';
    }
}
