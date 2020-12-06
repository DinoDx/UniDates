package com.unidates.Unidates.UniDates.Model;

import com.unidates.Unidates.UniDates.Enum.Ruolo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

public class Studente extends Utente {

    private boolean isBanned;
    private List<Studente> utentiBloccati;

    @OneToOne
    private Profilo profilo;

    public Studente(String email, String password){
        super(email, password,Ruolo.STUDENTE);
        isBanned = false;
        utentiBloccati = new ArrayList<>();
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public List<Studente> getUtentiBloccati() {
        return utentiBloccati;
    }

    public void addUtenteBloccato(Studente studente) {
        this.utentiBloccati.add(studente);
    }
}
