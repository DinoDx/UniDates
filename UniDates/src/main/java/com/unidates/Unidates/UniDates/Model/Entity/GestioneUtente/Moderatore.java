package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Moderatore extends Studente {

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Ammonimento> ammonimentoInviati;

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Segnalazione> segnalazioneRicevute;

    public Moderatore(){
    }

    public Moderatore(String email, String password) {
        super(email, password);
        setRuolo(Ruolo.MODERATORE);
        setActive(true);
        ammonimentoInviati = new ArrayList<Ammonimento>();
        segnalazioneRicevute = new ArrayList<Segnalazione>();
    }

    public Collection<Ammonimento> getAmmonimentiInviati() {
        return ammonimentoInviati;
    }

    public void setAmmonimentiInviati(Collection<Ammonimento> ammonimentoInviati) {
        this.ammonimentoInviati = ammonimentoInviati;
    }

    public Collection<Segnalazione> getSegnalazioniRicevute() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioniRicevute(Collection<Segnalazione> segnalazioneRicevute) {
        this.segnalazioneRicevute = segnalazioneRicevute;
    }


    public void addSegnalazione(Segnalazione segnalazione){
        segnalazioneRicevute.add(segnalazione);
    }
}
