package com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente;

import com.unidates.Unidates.UniDates.Enum.Ruolo;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Moderatore extends Utente {

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Ammonimento> ammonimentoInviati;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id_studente")
    private Studente studente;

    @OneToMany(mappedBy = "moderatore", cascade = CascadeType.REMOVE)
    private Collection<Segnalazione> segnalazioneRicevute;

    public Moderatore(){
    }

    public Moderatore(String email, String password) {
        super(email, password, Ruolo.MODERATORE);
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

    public Studente getStudente() {
        return studente;
    }

    public void setStudente(Studente studente) {
        this.studente = studente;
    }

    public void addSegnalazione(Segnalazione segnalazione){
        segnalazioneRicevute.add(segnalazione);
    }
}
