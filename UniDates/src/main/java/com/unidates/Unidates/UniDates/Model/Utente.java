package com.unidates.Unidates.UniDates.Model;


import com.unidates.Unidates.UniDates.Enum.Ruolo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Utente {
    @Id
    private String email;
    private String password;
    private Ruolo ruolo;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.PERSIST)
    private List<Notifica> notificheList;



    public Utente() {
    }

    public Utente(String email, String password, Ruolo ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
        notificheList = new ArrayList<Notifica>();
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

    public List<Notifica> getNotificheList() {
        return notificheList;
    }

    public void setNotificheList(List<Notifica> notificheList) {
        this.notificheList = notificheList;
    }
}
