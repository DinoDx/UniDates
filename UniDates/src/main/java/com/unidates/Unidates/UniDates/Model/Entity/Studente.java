package com.unidates.Unidates.UniDates.Model.Entity;


import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Studente extends Utente{

    private boolean isBanned;


    @ManyToMany
    private List<Studente> listaBloccati;

    @OneToMany(mappedBy = "studente", orphanRemoval = true)
    private List<Sospensione> listaSospensioni;

    @OneToMany(mappedBy = "studente",orphanRemoval = true)
    private List<Ammonimento> listaAmmonimenti;

    @OneToMany(mappedBy = "studente1", orphanRemoval = true)
    private List<Match> listaMatch;

    @OneToMany(mappedBy = "studente2", orphanRemoval = true)
    private List<Match> listaMatchRicevuti;

    @OneToMany(mappedBy = "utente",orphanRemoval = true)
    private List<Notifica> listaNotifica;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "profilo_id", referencedColumnName = "id")
    private Profilo profilo;


    private int ammonimentiAttivi;

    public Studente() {
        super();
        this.listaBloccati = new ArrayList<Studente>();
        this.listaSospensioni = new ArrayList<Sospensione>();
        this.listaAmmonimenti = new ArrayList<Ammonimento>();
        this.listaMatch = new ArrayList<Match>();
        this.listaMatchRicevuti = new ArrayList<Match>();
        this.ammonimentiAttivi = 0;
        this.isBanned = false;
        this.setRuolo(Ruolo.STUDENTE);
    }

    public Studente(String email, String password) {
        super(email, password, Ruolo.STUDENTE);
        this.listaBloccati = new ArrayList<Studente>();
        this.listaSospensioni = new ArrayList<Sospensione>();
        this.listaAmmonimenti = new ArrayList<Ammonimento>();
        this.listaMatch = new ArrayList<Match>();
        this.listaMatchRicevuti = new ArrayList<Match>();
        this.ammonimentiAttivi = 0;
        this.isBanned = false;
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

    public List<Sospensione> getListaSospensioni() {
        return listaSospensioni;
    }

    public void setListaSospensioni(List<Sospensione> listSospensione) {
        this.listaSospensioni = listSospensione;
    }

    public List<Ammonimento> getListaAmmonimenti() {
        return listaAmmonimenti;
    }

    public void setListaAmmonimenti(List<Ammonimento> listAmmonimento) {
        this.listaAmmonimenti = listAmmonimento;
    }

    public List<Match> getListaMatch() {
        return listaMatch;
    }

    public void setListaMatch(List<Match> listMatch) {
        this.listaMatch = listMatch;
    }



    public List<Match> getListaMatchRicevuti() {
        return listaMatchRicevuti;
    }

    public void setListaMatchRicevuti(List<Match> listMatchRicevuti) {
        this.listaMatchRicevuti = listMatchRicevuti;
    }

    public void addBloccato(Studente studente){
        listaBloccati.add(studente);
    }

    public void addSospensione(Sospensione sospensione){
        listaSospensioni.add(sospensione);
    }

    public void addAmmonimento(Ammonimento ammonimento){
        listaAmmonimenti.add(ammonimento);
    }

    public void addMatch(Match match){
        listaMatch.add(match);
    }

    public void addMatchRicevuto(Match match){
        listaMatchRicevuti.add(match);
    }

    public void addAmmonimentoattivo(){
        this.ammonimentiAttivi++;
    }

    public void removeAmmonimentoattivo(){
        this.ammonimentiAttivi--;
    }

    public void resetAmmonimentiattivi(){
        this.ammonimentiAttivi = 0;
    }

    public int getAmmonimentiAttivi(){
        return this.ammonimentiAttivi;
    }

    public List<Notifica> getListaNotifica() {
        return listaNotifica;
    }

    public void setListaNotifica(List<Notifica> listNotifica) {
        this.listaNotifica = listNotifica;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(!(obj instanceof Studente))
            return false;

        Studente s = (Studente) obj;
        return this.getEmail().equals(s.getEmail());
    }

    @Override
    public String toString() {
        return super.toString() + "Studente{" +
                "isBanned=" + isBanned +
                ", profilo=" + profilo +
                ", listaBloccati=" + listaBloccati +
                ", listSospensioni=" + listaSospensioni +
                ", listAmmonimenti=" + listaAmmonimenti +
                '}';
    }
}
