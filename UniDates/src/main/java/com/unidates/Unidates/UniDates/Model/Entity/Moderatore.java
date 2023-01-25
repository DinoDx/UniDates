package com.unidates.Unidates.UniDates.Model.Entity;

import com.unidates.Unidates.UniDates.Model.Enum.Ruolo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Moderatore extends Studente {

    @OneToMany(mappedBy = "moderatore", orphanRemoval = true)
    private List<Ammonimento> ammonimentoInviati;

    @OneToMany(mappedBy = "moderatore", orphanRemoval = true)
    private List<Segnalazione> segnalazioneRicevute;

    public Moderatore(){
        setRuolo(Ruolo.MODERATORE);
        setActive(true);
        ammonimentoInviati = new ArrayList<Ammonimento>();
        segnalazioneRicevute = new ArrayList<Segnalazione>();
    }

    public Moderatore(String email, String password) {
        super(email, password);
        setRuolo(Ruolo.MODERATORE);
        setActive(true);
        ammonimentoInviati = new ArrayList<Ammonimento>();
        segnalazioneRicevute = new ArrayList<Segnalazione>();
    }

    public List<Ammonimento> getAmmonimentoInviati() {
        return ammonimentoInviati;
    }

    public void setAmmonimentoInviati(List<Ammonimento> ammonimentoInviati) {
        this.ammonimentoInviati = ammonimentoInviati;
    }

    public List<Segnalazione> getSegnalazioneRicevute() {
        return segnalazioneRicevute;
    }

    public void setSegnalazioneRicevute(List<Segnalazione> segnalazioneRicevute) {
        this.segnalazioneRicevute = segnalazioneRicevute;
    }

    public void addSegnalazione(Segnalazione segnalazione){
        segnalazioneRicevute.add(segnalazione);
    }
}
