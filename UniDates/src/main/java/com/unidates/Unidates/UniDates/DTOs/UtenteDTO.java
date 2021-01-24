package com.unidates.Unidates.UniDates.DTOs;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import java.util.ArrayList;
import java.util.List;

public class UtenteDTO {
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

    public UtenteDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UtenteDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UtenteDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public UtenteDTO setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
        return this;
    }

    public List<NotificaDTO> getListaNotifica() {
        return listaNotifica;
    }

    public UtenteDTO setListaNotifica(List<NotificaDTO> listaNotifica) {
        this.listaNotifica = listaNotifica;
        return this;
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
