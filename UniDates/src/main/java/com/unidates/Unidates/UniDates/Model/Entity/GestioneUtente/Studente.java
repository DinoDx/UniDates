package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;


import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Profilo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Studente extends Utente {


    private boolean isBanned;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "profilo_id", referencedColumnName = "id")
    private Profilo profilo;

    public Studente() {
    }

    public Studente(String email, String password, boolean isBanned, Profilo profilo) {
        super(email, password, Ruolo.STUDENTE);
        this.isBanned = isBanned;
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
}
