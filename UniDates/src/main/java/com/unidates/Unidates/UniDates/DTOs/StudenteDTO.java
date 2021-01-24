package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import java.util.ArrayList;
import java.util.List;

public class StudenteDTO extends UtenteDTO{
    private ProfiloDTO profilo;
    private List<String> listaBloccatiEmail;
    private List<AmmonimentoDTO> listaAmmonimenti;
    private List<SospensioneDTO> listaSospensione;
    private List<MatchDTO> listaMatch;
    private List<MatchDTO> listaMatchRicevuti;
    private boolean isBanned;
    private int ammonimentiAttivi;

    public StudenteDTO() {
        this.listaBloccatiEmail = new ArrayList<String>();
        this.listaAmmonimenti = new ArrayList<AmmonimentoDTO>();
        this.listaSospensione = new ArrayList<SospensioneDTO>();
        this.listaMatch = new ArrayList<MatchDTO>();
        this.listaMatchRicevuti = new ArrayList<MatchDTO>();
        this.isBanned = false;
        this.ammonimentiAttivi = 0;
    }

    public StudenteDTO(String email, String password,ProfiloDTO profilo) {
        super(email, password, Ruolo.STUDENTE);
        this.profilo = profilo;
        this.listaBloccatiEmail = new ArrayList<String>();
        this.listaAmmonimenti = new ArrayList<AmmonimentoDTO>();
        this.listaSospensione = new ArrayList<SospensioneDTO>();
        this.listaMatch = new ArrayList<MatchDTO>();
        this.listaMatchRicevuti = new ArrayList<MatchDTO>();
        this.isBanned = false;
        this.ammonimentiAttivi = 0;
    }

    public ProfiloDTO getProfilo() {
        return profilo;
    }

    public StudenteDTO setProfilo(ProfiloDTO profilo) {
        this.profilo = profilo;
        return this;
    }

    public List<java.lang.String> getListaBloccatiEmail() {
        return listaBloccatiEmail;
    }

    public StudenteDTO setListaBloccatiEmail(List<java.lang.String> listaBloccatiEmail) {
        this.listaBloccatiEmail = listaBloccatiEmail;
        return this;
    }

    public List<AmmonimentoDTO> getListaAmmonimenti() {
        return listaAmmonimenti;
    }

    public StudenteDTO setListaAmmonimenti(List<AmmonimentoDTO> listaAmmonimenti) {
        this.listaAmmonimenti = listaAmmonimenti;
        return this;
    }

    public List<SospensioneDTO> getListaSospensione() {
        return listaSospensione;
    }

    public StudenteDTO setListaSospensione(List<SospensioneDTO> listaSospensione) {
        this.listaSospensione = listaSospensione;
        return this;
    }

    public List<MatchDTO> getListaMatch() {
        return listaMatch;
    }

    public StudenteDTO setListaMatch(List<MatchDTO> listaMatch) {
        this.listaMatch = listaMatch;
        return this;
    }

    public List<MatchDTO> getListaMatchRicevuti() {
        return listaMatchRicevuti;
    }

    public StudenteDTO setListaMatchRicevuti(List<MatchDTO> listaMatchRicevuti) {
        this.listaMatchRicevuti = listaMatchRicevuti;
        return this;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public StudenteDTO setBanned(boolean banned) {
        isBanned = banned;
        return this;
    }

    public int getAmmonimentiAttivi() {
        return ammonimentiAttivi;
    }

    public StudenteDTO setAmmonimentiAttivi(int ammonimentiAttivi) {
        this.ammonimentiAttivi = ammonimentiAttivi;
        return this;
    }


    @Override
    public java.lang.String toString() {
        return "StudenteDTO{" +
                "profilo=" + profilo +
                ", listaBloccati=" + listaBloccatiEmail +
                ", listaAmmonimenti=" + listaAmmonimenti +
                ", listaSospensione=" + listaSospensione +
                ", listaMatch=" + listaMatch +
                ", listaMatchRicevuti=" + listaMatchRicevuti +
                ", isBanned=" + isBanned +
                ", ammonimentiAttivi=" + ammonimentiAttivi +
                '}';
    }
}
