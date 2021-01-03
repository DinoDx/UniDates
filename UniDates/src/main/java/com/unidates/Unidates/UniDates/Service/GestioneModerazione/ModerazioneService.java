package com.unidates.Unidates.UniDates.Service.GestioneModerazione;

import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Ammonimento;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Segnalazione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneModerazione.Sospensione;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneProfilo.Foto;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Moderatore;
import com.unidates.Unidates.UniDates.Model.Entity.GestioneUtente.Studente;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.AmmonimentiRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SegnalazioniRepository;
import com.unidates.Unidates.UniDates.Model.Repository.GestioneModerazione.SospensioniRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ModerazioneService {

    @Autowired
    SegnalazioniRepository segnalazioniRepository;

    @Autowired
    AmmonimentiRepository ammonimentiRepository;

    @Autowired
    SospensioniRepository sospensioniRepository;



    public void inviaSegnalazione(Moderatore moderatore, Foto foto, String motivazione, String dettagli){
        Segnalazione segnalazione = new Segnalazione(motivazione, dettagli);
        segnalazione.setFoto(foto);
        segnalazione.setModeratore(moderatore);
        segnalazioniRepository.save(segnalazione);
    }

    public void inviaAmmonimento(Moderatore moderatore, Studente studente, String motivazione, String dettagli){
        Ammonimento ammonimento = new Ammonimento(motivazione, dettagli);
        ammonimento.setStudente(studente);
        ammonimento.setModeratore(moderatore);
        ammonimentiRepository.save(ammonimento);
    }

    public void inviaSospensione(Studente studente, int durata, String dettagli){
        Sospensione sospensione = new Sospensione(durata, dettagli);
        sospensione.setStudente(studente);
        sospendiUtente(studente);
        sospensioniRepository.save(sospensione);

    }

    public Collection<Segnalazione> visualizzaSegnalazioniRicevute(Moderatore moderatore) {
        return moderatore.getSegnalazioniRicevute();
    }

    public Collection<Ammonimento> visualizzaAmmonimentiInviati(Moderatore moderatore) {
        return moderatore.getAmmonimentiInviati();
    }


    public Collection<Ammonimento> visualizzaAmmonimentiRicevuti(Studente studente) {
        return studente.getListAmmonimenti();
    }

    public Collection<Sospensione> visualizzaSospensioni(Studente studente) {
        return studente.getListSospensioni();
    }

    private void sospendiUtente(Studente studente){
        studente.setBanned(true);
    }

}
