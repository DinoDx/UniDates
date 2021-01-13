package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import org.dom4j.rule.Mode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;

@Entity
public class CommunityManager extends Moderatore {

    @OneToOne
    @JoinColumn(name = "studente_id", referencedColumnName = "id")
    private Studente studente;

    public CommunityManager(){
    }

    public CommunityManager(String email, String password) {
        super(email, password);
        setRuolo(Ruolo.COMMUNITY_MANAGER);
    }

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }
}
