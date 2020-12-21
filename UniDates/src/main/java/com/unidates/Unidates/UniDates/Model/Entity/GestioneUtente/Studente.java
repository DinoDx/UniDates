package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;


import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.Ammonimenti;
import com.unidates.Unidates.UniDates.Model.Entity.Match;
import com.unidates.Unidates.UniDates.Model.Entity.Segnalazioni;
import com.unidates.Unidates.UniDates.Model.Entity.Sospensioni;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Profilo;

import javax.persistence.*;
import java.util.List;

@Entity
public class Studente extends Utente{


    private boolean isBanned;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinColumn(name = "profilo_id", referencedColumnName = "id")
    private Profilo profilo;



    @ManyToMany
    private List<Studente> listaBloccati;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.REMOVE)
    private List<Segnalazioni> listSegnalazioni;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.REMOVE)
    private List<Sospensioni> listSospensioni;

    @OneToMany(mappedBy = "studente", cascade = CascadeType.REMOVE)
    private List<Ammonimenti> listAmmonimenti;

    @OneToMany(mappedBy = "studente1", cascade = CascadeType.REMOVE)
    private List<Match> listMatch;

    public Studente() {
    }

    public Studente(String email, String password, Profilo profilo) {
        super(email, password, Ruolo.STUDENTE);
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

    public List<Segnalazioni> getListSegnalazioni() {
        return listSegnalazioni;
    }

    public void setListSegnalazioni(List<Segnalazioni> listSegnalazioni) {
        this.listSegnalazioni = listSegnalazioni;
    }

    public List<Sospensioni> getListSospensioni() {
        return listSospensioni;
    }

    public void setListSospensioni(List<Sospensioni> listSospensioni) {
        this.listSospensioni = listSospensioni;
    }

    public List<Ammonimenti> getListAmmonimenti() {
        return listAmmonimenti;
    }

    public void setListAmmonimenti(List<Ammonimenti> listAmmonimenti) {
        this.listAmmonimenti = listAmmonimenti;
    }

    public List<Match> getListMatch() {
        return listMatch;
    }

    public void setListMatch(List<Match> listMatch) {
        this.listMatch = listMatch;
    }

    @Override
    public String toString() {
        return super.toString() + "Studente{" +
                "isBanned=" + isBanned +
                ", profilo=" + profilo +
                ", listaBloccati=" + listaBloccati +
                ", listSegnalazioni=" + listSegnalazioni +
                ", listSospensioni=" + listSospensioni +
                ", listAmmonimenti=" + listAmmonimenti +
                '}';
    }
}
