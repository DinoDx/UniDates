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
    private List<NotificaDTO> listaNotifiche;
    private boolean isBanned;
    private int ammonimentiAttivi;

    public StudenteDTO() {
        this.listaBloccatiEmail = new ArrayList<String>();
        this.listaAmmonimenti = new ArrayList<AmmonimentoDTO>();
        this.listaSospensione = new ArrayList<SospensioneDTO>();
        this.listaMatch = new ArrayList<MatchDTO>();
        this.listaMatchRicevuti = new ArrayList<MatchDTO>();
        this.listaNotifiche = new ArrayList<NotificaDTO>();
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
        this.listaNotifiche = new ArrayList<NotificaDTO>();
        this.isBanned = false;
        this.ammonimentiAttivi = 0;
    }

    public ProfiloDTO getProfilo() {
        return profilo;
    }

    public void setProfilo(ProfiloDTO profilo) {
        this.profilo = profilo;
    }

    public List<String> getListaBloccatiEmail() {
        return listaBloccatiEmail;
    }

    public void setListaBloccatiEmail(List<String> listaBloccatiEmail) {
        this.listaBloccatiEmail = listaBloccatiEmail;
    }

    public List<AmmonimentoDTO> getListaAmmonimenti() {
        return listaAmmonimenti;
    }

    public void setListaAmmonimenti(List<AmmonimentoDTO> listaAmmonimenti) {
        this.listaAmmonimenti = listaAmmonimenti;
    }

    public List<SospensioneDTO> getListaSospensione() {
        return listaSospensione;
    }

    public void setListaSospensione(List<SospensioneDTO> listaSospensione) {
        this.listaSospensione = listaSospensione;
    }

    public List<MatchDTO> getListaMatch() {
        return listaMatch;
    }

    public void setListaMatch(List<MatchDTO> listaMatch) {
        this.listaMatch = listaMatch;
    }

    public List<MatchDTO> getListaMatchRicevuti() {
        return listaMatchRicevuti;
    }

    public void setListaMatchRicevuti(List<MatchDTO> listaMatchRicevuti) {
        this.listaMatchRicevuti = listaMatchRicevuti;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public int getAmmonimentiAttivi() {
        return ammonimentiAttivi;
    }

    public void setAmmonimentiAttivi(int ammonimentiAttivi) {
        this.ammonimentiAttivi = ammonimentiAttivi;
    }

    public List<NotificaDTO> getListaNotifica() {
        return listaNotifiche;
    }

    public void setListaNotifica(List<NotificaDTO> listaNotifiche) {
        this.listaNotifiche = listaNotifiche;
    }

    @Override
    public String toString() {
        return "StudenteDTO{" +
                "profilo=" + profilo +
                ", listaBloccatiEmail=" + listaBloccatiEmail +
                ", listaAmmonimenti=" + listaAmmonimenti +
                ", listaSospensione=" + listaSospensione +
                ", listaMatch=" + listaMatch +
                ", listaMatchRicevuti=" + listaMatchRicevuti +
                ", listaNotifiche=" + listaNotifiche +
                ", isBanned=" + isBanned +
                ", ammonimentiAttivi=" + ammonimentiAttivi +
                '}';
    }
}
