package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import java.util.ArrayList;
import java.util.List;

public  class UtenteDTO {
    private Long id;
    private String email;
    private String password;
    private Ruolo ruolo;
    private List<NotificaDTO> listaNotifica;

    public UtenteDTO() {
        listaNotifica = new ArrayList<NotificaDTO>();
    }

    public UtenteDTO(String email, String password, Ruolo ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        listaNotifica = new ArrayList<NotificaDTO>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }

    public List<NotificaDTO> getListaNotifica() {
        return listaNotifica;
    }

    public void setListaNotifica(List<NotificaDTO> listaNotifica) {
        this.listaNotifica = listaNotifica;

    }

    @Override
    public String toString() {
        return "UtenteDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ruolo=" + ruolo +
                ", listaNotifica=" + listaNotifica +
                '}';
    }
}
